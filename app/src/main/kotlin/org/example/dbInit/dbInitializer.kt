package org.example.dbInit

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.sql.Connection
import java.sql.Statement
import java.lang.reflect.Field

class DbInitializer(val db: Database) {

    // Define the 'organizations' table
    object Organization : Table("organizations") {
        val id = integer("id").autoIncrement()
        val name = varchar("name", 128)
        val streetAddress = varchar("streetAddress", 1024) // Fixed typo in column name
        val city = varchar("city", 128)
        val province = varchar("province", 2)
        val phoneNumber = varchar("phoneNumber", 10)
        val socialMedia = text("socialMedia")
        val website = varchar("website", 256)
        val queerInclusive = bool("queerInclusive")
        val primaryContact = integer("primaryContact")
        val accessibilityInformation = integer("accessibilityInformationID")
        val category = integer("categoryInformationID")
    }

    init {
        // Run database operations within a transaction
        transaction(db) {
            // Step 1: Create the 'organizations' table if it doesn't exist
            SchemaUtils.create(Organization)
        }
    }
}