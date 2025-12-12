package org.example.organization

data class HealthCategory(
    val specialist: Boolean,
    val peerSupport: Boolean,
    val transHealth: Boolean,
    val familyDoctor: Boolean,
    val privateProvider: Boolean,
    val mentalHealthProvider: Boolean,
    val healthCenter: Boolean,
    val physicalHealthProvider: Boolean,
    val publicProvider: Boolean,
    val counselor: Boolean,
    val communitySupport: Boolean
)