package org.example.model

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.javatime.*

object OrganizationTable : Table("organizations") {
    val id = integer("id").autoIncrement().uniqueIndex()
    val name = varchar("name", 128)
    val streetAddress = varchar("streetAddress", 1024).nullable()
    val description = text("description").nullable()
    val city = varchar("city", 128).nullable()
    val province = varchar("province", 2).nullable()
    val phoneNumber = varchar("phoneNumber", 12).nullable()
    val socialMedia = text("socialMedia").nullable()
    val website = varchar("website", 256).nullable()
    val queerOwned = bool("queerOwned").default(false)
    val queerInclusive = bool("queerInclusive").default(false)
    val accessibilityInformation = integer("accessibilityInformationID").references(AccessibilityInformation.id)
    val categoryInformation = integer("categoryInformationID").references(Categories.id)
    val otherInformation = varchar("other", 512).nullable()
    val lastUpdate = datetime("lastUpdate").defaultExpression(CurrentDateTime)
}