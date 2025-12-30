package org.example.dataClasses
import org.example.model.OrganizationTable
import org.jetbrains.exposed.v1.core.ResultRow
import java.time.LocalDateTime

data class Organization(
    val id: Int,
    val name: String,
    val description: String?,
    val email: String?,
    val streetAddress: String?,
    val city: String?,
    val province: String?,
    val phoneNumber: String?,
    val socialMedia: String?,
    val website: String?,
    val queerOwned: Boolean,
    val queerInclusive: Boolean,
    val accessibilityInformation: Int,
    val categoryInformation: Int,
    val otherInformation: String?,
    val lastUpdate: LocalDateTime
){
    companion object{
        fun fromRow(resultRow: ResultRow) = Organization(
            id = resultRow[OrganizationTable.id],
            name = resultRow[OrganizationTable.name],
            email = resultRow[OrganizationTable.email],
            description = resultRow[OrganizationTable.description],
            streetAddress = resultRow[OrganizationTable.streetAddress],
            city = resultRow[OrganizationTable.city],
            province = resultRow[OrganizationTable.province],
            phoneNumber = resultRow[OrganizationTable.phoneNumber],
            socialMedia = resultRow[OrganizationTable.socialMedia],
            website = resultRow[OrganizationTable.website],
            queerOwned = resultRow[OrganizationTable.queerOwned],
            queerInclusive = resultRow[OrganizationTable.queerInclusive],
            accessibilityInformation = resultRow[OrganizationTable.accessibilityInformation],
            categoryInformation = resultRow[OrganizationTable.categoryInformation],
            otherInformation = resultRow[OrganizationTable.otherInformation],
            lastUpdate = resultRow[OrganizationTable.lastUpdate] // Added this line
        )

        fun fromMap(data: Map<String, Any?>): Organization{
            return Organization(
                id = data["id"] as? Int ?: throw IllegalArgumentException("Missing id"),
                name = data["name"] as? String ?: throw IllegalArgumentException("Missing name"),
                email = data["email"] as? String,
                description = data["description"] as? String,
                streetAddress = data["streetAddress"] as? String,
                city = data["city"] as? String,
                province = data["province"] as? String,
                phoneNumber = data["phoneNumber"] as? String,
                socialMedia = data["socialMedia"] as? String,
                website = data["website"] as? String,
                queerOwned = data["queerOwned"] == "on",
                queerInclusive = data["queerInclusive"] == "on",
                accessibilityInformation = data["accessibilityInformation"] as? Int ?: 0,
                categoryInformation = data["categoryInformation"] as? Int ?: 0,
                otherInformation = data["otherInformation"] as? String,
                lastUpdate = data["lastUpdate"] as? LocalDateTime ?: throw IllegalArgumentException("Missing lastUpdate")
            )
        }
    }
}