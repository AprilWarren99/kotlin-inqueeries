package org.example.organization

data class AccessibiltyInformation(
    val parking: Boolean,
    val entrance: Boolean,
    val genderNeutralBathroom: Boolean,
    val accessibleWashroom: Boolean,
    val automaticDoorsOutside: Boolean,
    val automaticDoorsInside: Boolean
)