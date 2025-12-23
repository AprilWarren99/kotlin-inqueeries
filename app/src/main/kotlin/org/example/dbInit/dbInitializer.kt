package org.example.dbInit

import com.google.gson.JsonObject
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

import org.example.model.Organization
import org.example.model.AccessibilityInformation
import org.example.model.Contact
import org.example.model.Categories
import java.io.File
import kotlinx.serialization.*
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.andWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.update

// Wrapper class to hold the organizations array
data class OrganizationResponse(
    @SerialName("organizations")
    val organizations: List<OrganizationWrapper>
)

// Wrapper class for each organization
data class OrganizationWrapper(
    @SerialName("Document ID")
    val documentId: String,

    @SerialName("record")
    val record: Organization
)
data class OrganizationRecord(
    @SerialName("org_name") val org_name: String,
    @SerialName("org_postal") val org_postal: String,
    @SerialName("org_desc") val org_desc: String,
    @SerialName("org_socials") val org_socials: String,
    @SerialName("org_province") val org_province: String,
    @SerialName("org_phone") val org_phone: String,
    @SerialName("org_city") val org_city: String,
    @SerialName("queer_owned") val queer_owned: Boolean,
    @SerialName("org_contact1_name") val org_contact1_name: String,
    @SerialName("org_contact1_pronouns") val org_contact1_pronouns: String,
    @SerialName("org_contact1_position") val org_contact1_position: String,
    @SerialName("org_contact2_name") val org_contact2_name: String,
    @SerialName("org_contact2_pronouns") val org_contact2_pronouns: String,
    @SerialName("org_contact2_position") val org_contact2_position: String,
    @SerialName("org_website") val org_website: String,
    @SerialName("org_street_addr") val org_street_addr: String,
    @SerialName("queer_inclusive") val queer_inclusive: Boolean,
    @SerialName("extra_other_notes") val extra_other_notes: String,
    @SerialName("access_automatic_doors") val access_automatic_doors: Boolean,
    @SerialName("access_entrance") val access_entrance: Boolean,
    @SerialName("access_gender_neutral_washrooms") val access_gender_neutral_washrooms: Boolean,
    @SerialName("access_washrooms") val access_washrooms: Boolean,
    @SerialName("access_parking") val access_parking: Boolean,
    @SerialName("cat_education") val cat_education: Boolean,
    @SerialName("edu_organization") val edu_organization: Boolean,
    @SerialName("edu_post_secondary") val edu_post_secondary: Boolean,
    @SerialName("edu_workshops_training") val edu_workshops_training: Boolean,
    @SerialName("edu_individual") val edu_individual: Boolean,
    @SerialName("edu_remote_online") val edu_remote_online: Boolean,
    @SerialName("cat_health") val cat_health: Boolean,
    @SerialName("health_family_doctor") val health_family_doctor: Boolean,
    @SerialName("health_centre") val health_centre: Boolean,
    @SerialName("health_physical") val health_physical: Boolean,
    @SerialName("health_private") val health_private: Boolean,
    @SerialName("health_public") val health_public: Boolean,
    @SerialName("health_counselor") val health_counselor: Boolean,
    @SerialName("health_mental") val health_mental: Boolean,
    @SerialName("health_care_provider") val health_care_provider: Boolean,
    @SerialName("health_trans") val health_trans: Boolean,
    @SerialName("health_specialist") val health_specialist: Boolean,
    @SerialName("health_peer_support") val health_peer_support: Boolean,
    @SerialName("cat_hospitality") val cat_hospitality: Boolean,
    @SerialName("hospitality_catering") val hospitality_catering: Boolean,
    @SerialName("hospitality_cafe") val hospitality_cafe: Boolean,
    @SerialName("hospitality_hotels") val hospitality_hotels: Boolean,
    @SerialName("hospitality_restaurants") val hospitality_restaurants: Boolean,
    @SerialName("hospitality_bars") val hospitality_bars: Boolean,
    @SerialName("hospitality_food_trucks") val hospitality_food_trucks: Boolean,
    @SerialName("cat_other") val cat_other: Boolean,
    @SerialName("other_employment") val other_employment: Boolean,
    @SerialName("other_transportation") val other_transportation: Boolean,
    @SerialName("cat_retail_service") val cat_retail_service: Boolean,
    @SerialName("retail_groceries") val retail_groceries: Boolean,
    @SerialName("retail_clothing") val retail_clothing: Boolean,
    @SerialName("retail_legal") val retail_legal: Boolean,
    @SerialName("retail_entertainment") val retail_entertainment: Boolean,
    @SerialName("retail_digital_services") val retail_digital_services: Boolean,
    @SerialName("retail_fitness_centre") val retail_fitness_centre: Boolean,
    @SerialName("retail_adult_products") val retail_adult_products: Boolean,
    @SerialName("retail_artist") val retail_artist: Boolean,
    @SerialName("retail_convienience") val retail_convienience: Boolean,
    @SerialName("retail_consultant") val retail_consultant: Boolean,
    @SerialName("retail_esthetics") val retail_esthetics: Boolean,
    @SerialName("retail_skilled_trades") val retail_skilled_trades: Boolean,
    @SerialName("other_spiritual") val other_spiritual: Boolean,
    @SerialName("other_food_security") val other_food_security: Boolean,
    @SerialName("other_housing") val other_housing: Boolean
)

class DbInitializer(val db: Database) {
    init {
        // Run database operations within a transaction
        transaction(db) {
            SchemaUtils.drop(Contact)
            SchemaUtils.drop(Organization)
            SchemaUtils.drop(Categories)
            SchemaUtils.drop(AccessibilityInformation)
            SchemaUtils.create(AccessibilityInformation)
            SchemaUtils.create(Categories)
            SchemaUtils.create(Organization)
            SchemaUtils.create(Contact)

            addFirebaseData()
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

                                    // Extract document ID if it exists
                                    val documentId = if (orgObject.has("Document ID")) {
                                        orgObject.get("Document ID").asString
                                    } else {
                                        "Unknown"
                                    }

                                    // Extract record if it exists
                                    val recordElement = if (orgObject.has("record")) {
                                        orgObject.get("record")
                                    } else {
                                        element // If record is the actual object
                                    }

                                    if (recordElement.isJsonObject) {
                                        val org = gson.fromJson(recordElement, OrganizationRecord::class.java)

                                        val accessibilityID = AccessibilityInformation.insert {
                                            it[entrance] = org.access_entrance
                                            it[automaticDoors] = org.access_automatic_doors
                                            it[genderNeutralBathroom] = org.access_gender_neutral_washrooms
                                            it[parking] = org.access_parking
                                            it[accessibleBathroom] = org.access_washrooms
                                        } get AccessibilityInformation.id

                                        val categoriesID = Categories.insert {
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
                                        } get Categories.id


                                        // Check if there is already an organization with the current name
                                        val orgQuery = Organization.select(Organization.id)
                                            .where{Organization.name eq org.org_name}
                                            .firstOrNull()

                                        var orgID = -1
                                        if(orgQuery == null) {
                                            orgID = Organization.insert {
                                                it[name] = org.org_name
                                                it[streetAddress] = org.org_street_addr
                                                it[description] = org.org_desc
                                                it[city] = org.org_city
                                                it[province] = org.org_province
                                                it[phoneNumber] = org.org_phone
                                                it[socialMedia] = org.org_socials
                                                it[website] = org.org_website
                                                it[queerOwned] = org.queer_owned
                                                it[queerInclusive] = org.queer_inclusive
                                                it[accessibilityInformation] = accessibilityID
                                                it[categoryInformation] = categoriesID
                                                it[otherInformation] = org.extra_other_notes
                                            } get Organization.id
                                        }else{
                                            orgID = orgQuery.get(Organization.id)
                                        }

                                        val contact1Record = Contact.select(Contact.id,
                                            Contact.name,
                                            Contact.pronouns,
                                            Contact.position)
                                            .where {Contact.name eq org.org_contact1_name}
                                            .andWhere { Contact.pronouns eq org.org_contact1_pronouns }
                                            .andWhere { Contact.position eq org.org_contact1_position }
                                            .firstOrNull()

                                        val contact2Record = Contact.select(Contact.id,
                                            Contact.name,
                                            Contact.pronouns,
                                            Contact.position)
                                            .where { Contact.name eq org.org_contact2_name }
                                            .andWhere { Contact.pronouns eq org.org_contact2_pronouns }
                                            .andWhere { Contact.position eq org.org_contact2_position }
                                            .firstOrNull()

                                        if(contact1Record == null && org.org_contact1_name != ""){
                                            Contact.insert{
                                                it[name] = org.org_contact1_name
                                                it[organizationID] = orgID
                                                it[pronouns] = org.org_contact1_pronouns
                                                it[position] = org.org_contact1_position
                                                it[directEmail] = null
                                                it[directPhone] = null
                                            } get Contact.id
                                        }

                                        if (contact2Record == null && org.org_contact2_name != ""){
                                            Contact.insert{
                                                it[organizationID] = orgID
                                                it[name] = org.org_contact2_name
                                                it[pronouns] = org.org_contact2_pronouns
                                                it[position] = org.org_contact2_position
                                                it[directEmail] = null
                                                it[directPhone] = null
                                            } get Contact.id
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
    }
