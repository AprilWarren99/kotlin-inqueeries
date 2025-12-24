package org.example.model

import org.jetbrains.exposed.v1.core.Table

object ContactTable: Table("contact"){
    val id = integer("id").autoIncrement().uniqueIndex()
    val organizationID = integer("organizationID").references(OrganizationTable.id)
    val name = varchar("name", 120)
    val pronouns = varchar("pronouns", 30).nullable()
    val position = varchar("position", 120)
    val directEmail = varchar("directEmail", 128).nullable()
    val directPhone = varchar("directPhone", 10).nullable()
}