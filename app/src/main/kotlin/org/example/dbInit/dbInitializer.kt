package org.example.dbInit

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.isNotNull
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.sql.Connection
import java.sql.Statement
import java.lang.reflect.Field

class DbInitializer(val db: Database) {

    // Define the 'organizations' table
    object Organization : Table("organizations") {
        val id = integer("id").autoIncrement()
        val name = varchar("name", 128).isNotNull()
        val streetAddress = varchar("streetAddress", 1024).nullable()
        val city = varchar("city", 128).nullable()
        val province = varchar("province", 2).nullable()
        val phoneNumber = varchar("phoneNumber", 10).nullable()
        val socialMedia = text("socialMedia").nullable()
        val website = varchar("website", 256).nullable()
        val queerOwned = bool("queerOwned").default(false)
        val queerInclusive = bool("queerInclusive").default(false)
        val primaryContact = integer("primaryContact").references(Contact.id)
        val secondaryContact = integer("secondaryContact").references(Contact.id).nullable()
        val accessibilityInformation = integer("accessibilityInformationID").references(AccessibilityInformation.id)
        val categoryInformation = integer("categoryInformationID").references(Categories.id)
        val otherInformation = varchar("other", 512).nullable()
    }

    object Contact: Table("contact"){
        val id = integer("id").autoIncrement().uniqueIndex()
        val name = varchar("Name", 120)
        val pronouns = varchar("pronouns", 15).nullable()
        val position = varchar("position", 120)
        val directEmail = varchar("directEmail", 128).nullable()
        val directPhone = varchar("directPhone", 10).nullable()
    }
    object AccessibilityInformation: Table("accessibility_information") {
        val id = integer("id").autoIncrement().uniqueIndex()
        val automaticDoors = bool("automaticDoors").default(false)
        val entrance = bool("entrance").default(false)
        val genderNeutralBathroom = bool("genderNeutralBathroom").default(false)
        val parking = bool("parking").default(false)
        val accessibleBathroom = bool("accessibleBathroom").default(false)
    }

    object Categories: Table("categories_information"){
        val id = integer("id").autoIncrement().uniqueIndex()

        //education
        val isEducation = bool("isEducation").default(false)

        val individual = bool("individual").default(false)
        val organization = bool("organization").default(false)
        val postSecondary = bool("post_secondary").default(false)
        val remoteOnline = bool("remote_online").default(false)

        //health
        val isHealthCare = bool("is_health_care")

        val healthCareProvider = bool("health_care_provider").default(false)
        val healthCentre = bool("health_centre").default(false)
        val counselor = bool("counselor").default(false)
        val familyDoctor = bool("family_doctor").default(false)
        val mental = bool("mental").default(false)
        val peerSupport = bool("peer_support").default(false)
        val physical = bool("physical").default(false)
        val private = bool("private").default(false)
        val public = bool("public").default(false)
        val specialist = bool("specialist").default(false)
        val trans = bool("trans").default(false)

        //hospitality
        val isHospitality = bool("is_hospitality").default(false)

        val bar = bool("bar").default(false)
        val cafe = bool("cafe").default(false)
        val catering = bool("catering").default(false)
        val foodTruck = bool("food_truck").default(false)
        val hotel = bool("hotel").default(false)
        val restaurant = bool("restaurant").default(false)

        //retail
        val isRetail = bool("is_retail")
        val isAdult = bool("is_adult") //19+
        val adultProducts = bool("adult_products").default(false)
        val artist = bool("artist").default(false)
        val clothing = bool("clothing").default(false)
        val consultant = bool("consultant").default(false)
        val convenience = bool("convenience").default(false)
        val digitalServices = bool("digital_services").default(false)
        val entertainment = bool("entertainment").default(false)
        val esthetics = bool("esthetics").default(false)
        val fitnessCentre = bool("fitness_centre").default(false)
        val groceries = bool("groceries").default(false)
        val legal = bool("legal").default(false)
        val skilledTrades = bool("skilled_trades").default(false)

        //other
        val isOther = bool("is_other").default(false)
        val employment = bool("employment").default(false)
        val foodSecurity = bool("food_security").default(false)
        val housing = bool("housing").default(false)
        val spiritual = bool("spiritual").default(false)
        val transportation = bool("transportation").default(false)

    }

    init {
        // Run database operations within a transaction
        transaction(db) {
            SchemaUtils.drop(Organization)
            SchemaUtils.drop(Categories)
            SchemaUtils.drop(Contact)
            SchemaUtils.drop(AccessibilityInformation)
            SchemaUtils.create(AccessibilityInformation)
            SchemaUtils.create(Categories)
            SchemaUtils.create(Contact)
            SchemaUtils.create(Organization)

            Categories.insert{
                it[adultProducts] = false
            }
        }
    }
}