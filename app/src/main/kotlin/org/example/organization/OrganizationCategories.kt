package org.example.organization

data class OrganizationCategories(
    val health: HealthCategory,
    val hospitality: HospitalityCategory,
    val retail: RetailCategory,
    val other: OtherCategory,
)