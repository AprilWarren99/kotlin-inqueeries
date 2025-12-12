package org.example.organization

data class Organization (
    val name: String,
    val description: String,
    val streetAddress: String,
    val city: String,
    val province: String,
    val phoneNumber: String,
    val socialMedia: List<String>,
    val website: String,
    val primaryContact: Contact,
    val queerInclusive: Boolean,
    val accessibilityInformation: AccessibiltyInformation,
    val category: OrganizationCategories,
)