package org.example.dataClasses

import org.example.model.AccessibilityInformationTable
import org.jetbrains.exposed.v1.core.ResultRow
import java.time.LocalDateTime

data class AccessibilityInformation(
    val id: Int,
    val automaticDoors: Boolean,
    val entrance: Boolean,
    val genderNeutralBathroom: Boolean,
    val parking: Boolean,
    val accessibleBathroom: Boolean,

    val lastUpdate: LocalDateTime
){
    companion object{
        fun fromRow(resultRow: ResultRow) = AccessibilityInformation(
            id = resultRow[AccessibilityInformationTable.id],
            automaticDoors = resultRow[AccessibilityInformationTable.automaticDoors],
            entrance = resultRow[AccessibilityInformationTable.entrance],
            genderNeutralBathroom =resultRow[AccessibilityInformationTable.genderNeutralBathroom],
            parking = resultRow[AccessibilityInformationTable.parking],
            accessibleBathroom = resultRow[AccessibilityInformationTable.accessibleBathroom],
            lastUpdate = resultRow[AccessibilityInformationTable.lastUpdate],
        )
    }
}
