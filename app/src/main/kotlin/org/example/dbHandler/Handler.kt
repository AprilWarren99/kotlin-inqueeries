package org.example.dbHandler

import com.google.gson.Gson
import com.google.gson.JsonParser
import org.example.model.AccessibilityInformationTable
import org.example.model.CategoriesTable
import org.example.model.ContactTable
import org.example.model.OrganizationTable
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.andWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update
import java.io.File
import java.time.LocalDateTime
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.forEach

class Handler (reinitDB: Boolean = false){

    val dbConnection: Database = Database.connect(
        url = System.getenv("dbURL"),
        user = System.getenv("dbUser"),
        password = System.getenv("dbPassword")
    )

    init{
        if(reinitDB) dbInitializer(dbConnection)
    }

    fun dbInitializer(db: Database){
        transaction(db) {
            println("--------- Re-initializing Database ----------\n\t> Dropping all database tables")
            SchemaUtils.drop(ContactTable)
            SchemaUtils.drop(OrganizationTable)
            SchemaUtils.drop(CategoriesTable)
            SchemaUtils.drop(AccessibilityInformationTable)
            println("\t> Recreating database tables")
            SchemaUtils.create(AccessibilityInformationTable)
            SchemaUtils.create(CategoriesTable)
            SchemaUtils.create(OrganizationTable)
            SchemaUtils.create(ContactTable)

            println("\t> Adding Firebase data")
            addFirebaseData()
            println("----- Database initialization completed -----")
        }
    }
    fun addFirebaseData() {
        try {
            val gson = Gson()
            val file = File("app/src/main/resources/firebase_export.json")
            val jsonString = file.readText()

            // Parse the JSON to see what structure we're actually dealing with
            val jsonElement = JsonParser.parseString(jsonString)

            // If it's a root object with organizations array
            if (jsonElement.isJsonObject) {
                val rootObject = jsonElement.asJsonObject

                // Check if it has organizations array
                if (rootObject.has("organizations")) {
                    val organizationsArray = rootObject.getAsJsonArray("organizations")

                    // Process each organization
                    organizationsArray.forEachIndexed { index, element ->
                        try {
                            if (element.isJsonObject) {
                                val orgObject = element.asJsonObject

                                // Extract record if it exists
                                val recordElement = if (orgObject.has("record")) {
                                    orgObject.get("record")
                                } else {
                                    element // If record is the actual object
                                }

                                if (recordElement.isJsonObject) {
                                    val org = gson.fromJson(recordElement, OrganizationRecord::class.java)

                                    val accessibilityID = AccessibilityInformationTable.insert {
                                        it[entrance] = org.access_entrance
                                        it[automaticDoors] = org.access_automatic_doors
                                        it[genderNeutralBathroom] = org.access_gender_neutral_washrooms
                                        it[parking] = org.access_parking
                                        it[accessibleBathroom] = org.access_washrooms
                                    } get AccessibilityInformationTable.id

                                    val categoriesID = CategoriesTable.insert {
                                        it[isEducation] = org.cat_education
                                        it[individual] = org.edu_individual
                                        it[organization] = org.edu_organization
                                        it[postSecondary] = org.edu_post_secondary
                                        it[remoteOnline] = org.edu_remote_online
                                        it[workshopsOrTraining] = org.edu_workshops_training

                                        it[isHealthCare] = org.cat_health
                                        it[healthCentre] = org.health_centre
                                        it[counselor] = org.health_counselor
                                        it[familyDoctor] = org.health_family_doctor
                                        it[mental] = org.health_mental
                                        it[peerSupport] = org.health_peer_support
                                        it[physical] = org.health_physical
                                        it[private] = org.health_private
                                        it[public] = org.health_public
                                        it[specialist] = org.health_specialist
                                        it[trans] = org.health_trans

                                        it[isHospitality] = org.cat_hospitality
                                        it[bar] = org.hospitality_bars
                                        it[cafe] = org.hospitality_cafe
                                        it[catering] = org.hospitality_catering
                                        it[foodTruck] = org.hospitality_food_trucks
                                        it[hotel] = org.hospitality_hotels
                                        it[restaurant] = org.hospitality_restaurants

                                        it[isRetail] = org.cat_retail_service
                                        it[isAdult] = false // I dont have data on this so it'll need to be updated later
                                        it[adultProducts] = org.retail_adult_products
                                        it[artist] = org.retail_artist
                                        it[clothing] = org.retail_clothing
                                        it[consultant] = org.retail_consultant
                                        it[convenience] = org.retail_convienience
                                        it[digitalServices] = org.retail_digital_services
                                        it[entertainment] = org.retail_entertainment
                                        it[esthetics] = org.retail_esthetics
                                        it[fitnessCentre] = org.retail_fitness_centre
                                        it[groceries] = org.retail_groceries
                                        it[legal] = org.retail_legal
                                        it[skilledTrades] = org.retail_skilled_trades

                                        it[isOther] = org.cat_other
                                        it[employment] = org.other_employment
                                        it[foodSecurity] = org.other_food_security
                                        it[housing] = org.other_housing
                                        it[spiritual] = org.other_spiritual
                                        it[transportation] = org.other_transportation
                                    } get CategoriesTable.id


                                    // Check if there is already an organization with the current name
                                    val orgQuery = OrganizationTable.select(OrganizationTable.id)
                                        .where{OrganizationTable.name eq org.org_name}
                                        .firstOrNull()

                                    var orgID = -1
                                    if(orgQuery == null) {
                                        var extraNotes: String? = null
                                        if(org.extra_other_notes != ""){extraNotes = org.extra_other_notes}
                                        var streetAddr: String? = null
                                        if(org.org_street_addr != ""){streetAddr = org.org_street_addr}
                                        var orgCity: String? = null
                                        if(org.org_city != ""){orgCity = org.org_city}
                                        var orgPhone: String? = null
                                        if(org.org_phone != ""){orgPhone = org.org_phone}
                                        var orgSocials: String? = null
                                        if(org.org_socials != ""){orgSocials = org.org_socials}
                                        var orgWebsite: String? = null
                                        if(org.org_website != ""){orgWebsite = org.org_website}

                                        orgID = OrganizationTable.insert {
                                            it[name] = org.org_name
                                            it[streetAddress] = streetAddr
                                            it[description] = org.org_desc
                                            it[city] = orgCity
                                            it[province] = org.org_province
                                            it[phoneNumber] = orgPhone
                                            it[socialMedia] = orgSocials
                                            it[website] = orgWebsite
                                            it[queerOwned] = org.queer_owned
                                            it[queerInclusive] = org.queer_inclusive
                                            it[accessibilityInformation] = accessibilityID
                                            it[categoryInformation] = categoriesID
                                            it[otherInformation] = extraNotes
                                        } get OrganizationTable.id
                                    }else{
                                        orgID = orgQuery.get(OrganizationTable.id)
                                    }

                                    val contact1Record = ContactTable.select(ContactTable.id,
                                        ContactTable.name,
                                        ContactTable.pronouns,
                                        ContactTable.position)
                                        .where {ContactTable.name eq org.org_contact1_name}
                                        .andWhere { ContactTable.pronouns eq org.org_contact1_pronouns }
                                        .andWhere { ContactTable.position eq org.org_contact1_position }
                                        .firstOrNull()

                                    val contact2Record = ContactTable.select(ContactTable.id,
                                        ContactTable.name,
                                        ContactTable.pronouns,
                                        ContactTable.position)
                                        .where { ContactTable.name eq org.org_contact2_name }
                                        .andWhere { ContactTable.pronouns eq org.org_contact2_pronouns }
                                        .andWhere { ContactTable.position eq org.org_contact2_position }
                                        .firstOrNull()

                                    if(contact1Record == null && org.org_contact1_name != ""){
                                        var pos: String? = null
                                        if(org.org_contact1_position!="") { pos = org.org_contact1_position}

                                        ContactTable.insert{
                                            it[name] = org.org_contact1_name
                                            it[organizationID] = orgID
                                            it[pronouns] = org.org_contact1_pronouns
                                            it[position] = pos
                                            it[directEmail] = null
                                            it[directPhone] = null
                                        } get ContactTable.id
                                    }

                                    if (contact2Record == null && org.org_contact2_name != ""){
                                        var pos: String? = null
                                        if(org.org_contact2_position!="") { pos = org.org_contact2_position}

                                        ContactTable.insert{
                                            it[organizationID] = orgID
                                            it[name] = org.org_contact2_name
                                            it[pronouns] = org.org_contact2_pronouns
                                            it[position] = pos
                                            it[directEmail] = null
                                            it[directPhone] = null
                                        } get ContactTable.id
                                    }
                                }

                            }
                        } catch (e: Exception) {
                            println("Error processing organization $index (${element}}: ${e.message}")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error in addFirebaseData: ${e.message}")
        }
    }
    fun updateOrganizationTable(
        organizationID: Int,
        params: Map<String, String?>,
        booleanMappings: Map<String, Column<*>>,
        stringMappings: Map<String, Column<*>>,
    ): Boolean {
        return transaction {
            val oldRow = OrganizationTable
                .selectAll()
                .where { OrganizationTable.id eq organizationID }
                .singleOrNull()
                ?: return@transaction false // row not found

            var changed = false
            val newValues = mutableMapOf<Column<*>, Any?>()

            // Compare each column with the incoming "on"/"off" value
            booleanMappings.forEach { (paramKey, column) ->
                val newValue = params[paramKey] == "on"
                if (oldRow[column] != newValue) {
                    newValues[column] = newValue
                    changed = true
                }
            }

            stringMappings.forEach { (paramKey, column) ->
                if (!params.containsKey(paramKey)) return@forEach

                val newValue = params[paramKey]
                    ?.trim()
                    ?.takeIf { it.isNotEmpty() }


                if (oldRow[column] != newValue) {
                    newValues[column] = newValue
                    changed = true
                }
            }

            // Update if anything changed
            if (changed) {
                OrganizationTable.update({ OrganizationTable.id eq organizationID }) { stmt ->
                    newValues.forEach { (column, value) ->
                        @Suppress("UNCHECKED_CAST")
                        stmt[column as Column<Any?>] = value
                    }
                    stmt[OrganizationTable.lastUpdate] = LocalDateTime.now()
                }
            }
            changed
        }
    }
    fun updateAccessibilityTable(
        accessibilityID: Int,
        params: Map<String, String?>,
        booleanMappings: Map<String, Column<*>>? = null,
        stringMappings: Map<String, Column<*>>? = null,
    ): Boolean {
        return transaction {
            val oldRow = AccessibilityInformationTable
                .selectAll()
                .where { AccessibilityInformationTable.id eq accessibilityID }
                .singleOrNull()
                ?: return@transaction false // row not found

            var changed = false
            val newValues = mutableMapOf<Column<*>, Any?>()

            // Compare each column with the incoming "on"/"off" value
            booleanMappings?.forEach { (paramKey, column) ->
                val newValue = params[paramKey] == "on"
                if (oldRow[column] != newValue) {
                    newValues[column] = newValue
                    changed = true
                }
            }

            // Compare each column with the incoming "on"/"off" value
            stringMappings?.forEach { (paramKey, column) ->
                val newValue = params[paramKey]
                if (oldRow[column] != newValue) {
                    newValues[column] = newValue
                    changed = true
                }
            }

            // Update if anything changed
            if (changed) {
                AccessibilityInformationTable.update({ AccessibilityInformationTable.id eq accessibilityID }) { stmt ->
                    newValues.forEach { (column, value) ->
                        @Suppress("UNCHECKED_CAST")
                        stmt[column as Column<Any?>] = value
                    }
                    stmt[AccessibilityInformationTable.lastUpdate] = LocalDateTime.now()
                }
            }

            changed
        }
    }
    fun updateContactInfo(
        contactID: Int,
        params: Map<String, String?>,
        booleanMappings: Map<String, Column<*>>? = null,
        stringMappings: Map<String, Column<*>>,
    ): Boolean {
        return transaction {
            val oldRow = ContactTable
                .selectAll()
                .where { ContactTable.id eq contactID }
                .singleOrNull()
                ?: return@transaction false // row not found

            var changed = false
            val newValues = mutableMapOf<Column<*>, Any?>()

            // Compare each column with the incoming "on"/"off" value
            booleanMappings?.forEach { (paramKey, column) ->
                val newValue = params[paramKey] == "on"
                if (oldRow[column] != newValue) {
                    newValues[column] = newValue
                    changed = true
                }
            }

            // Compare each column with the incoming "on"/"off" value
            stringMappings.forEach { (paramKey, column) ->
                if (!params.containsKey(paramKey)) return@forEach

                val newValue = params[paramKey]
                    ?.trim()
                    ?.takeIf { it.isNotEmpty() }

                if (oldRow[column] != newValue) {
                    newValues[column] = newValue
                    changed = true
                }
            }

            // Update if anything changed
            if (changed) {
                ContactTable.update({ ContactTable.id eq contactID }) { stmt ->
                    newValues.forEach { (column, value) ->
                        @Suppress("UNCHECKED_CAST")
                        stmt[column as Column<Any?>] = value
                    }
                    stmt[ContactTable.lastUpdate] = LocalDateTime.now()
                }
            }
            changed
        }
    }
    fun updateCategoriesTable(
        categoriesID: Int,
        params: Map<String, String?>,
        booleanMappings: Map<String, Column<*>>? = null,
        stringMappings: Map<String, Column<*>>? = null,
    ): Boolean {
        return transaction {
            val oldRow = CategoriesTable
                .selectAll()
                .where { CategoriesTable.id eq categoriesID }
                .singleOrNull()
                ?: return@transaction false // row not found

            var changed = false
            val newValues = mutableMapOf<Column<*>, Any?>()

            // Compare each column with the incoming "on"/"off" value
            booleanMappings?.forEach { (paramKey, column) ->
                val newValue = params[paramKey] == "on"
                if (oldRow[column] != newValue) {
                    newValues[column] = newValue
                    changed = true
                }
            }

            // Compare each column with the incoming "on"/"off" value
            stringMappings?.forEach { (paramKey, column) ->
                val newValue = params[paramKey]
                if (oldRow[column] != newValue) {
                    newValues[column] = newValue
                    changed = true
                }
            }

            // Update if anything changed
            if (changed) {
                CategoriesTable.update({ CategoriesTable.id eq categoriesID }) { stmt ->
                    newValues.forEach { (column, value) ->
                        @Suppress("UNCHECKED_CAST")
                        stmt[column as Column<Any?>] = value
                    }
                    stmt[CategoriesTable.lastUpdate] = LocalDateTime.now()
                }
            }
            changed
        }
    }
}
