package org.example.dataClasses

import org.example.model.OrganizationTable
import org.jetbrains.exposed.v1.core.ResultRow

data class Organization(
    val id: Int,
    val name: String,
    val description: String?,
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
){
    companion object{
        fun fromRow(resultRow: ResultRow) = Organization(
            id = resultRow[OrganizationTable.id],
            name = resultRow[OrganizationTable.name],
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
            otherInformation = resultRow[OrganizationTable.otherInformation]
        )
    }
}