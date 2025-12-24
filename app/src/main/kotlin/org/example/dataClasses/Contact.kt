package org.example.dataClasses

import org.example.model.ContactTable
import org.example.model.OrganizationTable
import org.jetbrains.exposed.v1.core.ResultRow

data class Contact(
    val id: Int,
    val organizationID: Int,
    val name: String,
    val pronouns: String?,
    val position: String,
    val directEmail: String?,
    val directPhone: String?,
){
    companion object{
        fun fromRow(resultRow: ResultRow) = Contact(
            id = resultRow[ContactTable.id],
            organizationID = resultRow[ContactTable.organizationID],
            name = resultRow[ContactTable.name],
            pronouns = resultRow[ContactTable.pronouns],
            position = resultRow[ContactTable.position],
            directEmail = resultRow[ContactTable.directEmail],
            directPhone = resultRow[ContactTable.directPhone]
        )
    }
}
