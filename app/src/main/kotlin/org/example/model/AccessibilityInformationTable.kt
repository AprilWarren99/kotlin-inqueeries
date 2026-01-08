package org.example.model

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.javatime.*

object AccessibilityInformationTable: Table("accessibility_information") {
    val id = integer("id").autoIncrement().uniqueIndex()
    val automaticDoors = bool("automaticDoors").default(false)
    val entrance = bool("entrance").default(false)
    val genderNeutralBathroom = bool("genderNeutralBathroom").default(false)
    val parking = bool("parking").default(false)
    val accessibleBathroom = bool("accessibleBathroom").default(false)

    val lastUpdate = datetime("lastUpdate").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}