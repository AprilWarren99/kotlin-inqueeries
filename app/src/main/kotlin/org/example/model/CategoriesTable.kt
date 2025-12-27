package org.example.model

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.javatime.CurrentDateTime
import org.jetbrains.exposed.v1.javatime.datetime

object CategoriesTable: Table("categories_information"){
    val id = integer("id").autoIncrement().uniqueIndex()

    //education
    val isEducation = bool("isEducation").default(false)

    val individual = bool("individual").default(false)
    val organization = bool("organization").default(false)
    val postSecondary = bool("post_secondary").default(false)
    val remoteOnline = bool("remote_online").default(false)
    val workshopsOrTraining = bool("workshops_training").default(false)

    //health
    val isHealthCare = bool("is_health_care")

    // val healthCareProvider = bool("health_care_provider").default(false)
    val healthCentre = bool("health_centre").default(false)
    val counselor = bool("counselor").default(false)
    val familyDoctor = bool("family_doctor").default(false)
    val mental = bool("mental").default(false)
    val peerSupport = bool("peer_support").default(false)
    val physical = bool("physical").default(false)
    val private = bool("private").default(false)
    val public = bool("public").default(false)
    val specialist = bool("specialist").default(false)
    val trans = bool("trans").default(false)

    //hospitality
    val isHospitality = bool("is_hospitality").default(false)

    val bar = bool("bar").default(false)
    val cafe = bool("cafe").default(false)
    val catering = bool("catering").default(false)
    val foodTruck = bool("food_truck").default(false)
    val hotel = bool("hotel").default(false)
    val restaurant = bool("restaurant").default(false)

    //retail
    val isRetail = bool("is_retail")
    val isAdult = bool("is_adult") //19+
    val adultProducts = bool("adult_products").default(false)
    val artist = bool("artist").default(false)
    val clothing = bool("clothing").default(false)
    val consultant = bool("consultant").default(false)
    val convenience = bool("convenience").default(false)
    val digitalServices = bool("digital_services").default(false)
    val entertainment = bool("entertainment").default(false)
    val esthetics = bool("esthetics").default(false)
    val fitnessCentre = bool("fitness_centre").default(false)
    val groceries = bool("groceries").default(false)
    val legal = bool("legal").default(false)
    val skilledTrades = bool("skilled_trades").default(false)

    //other
    val isOther = bool("is_other").default(false)
    val employment = bool("employment").default(false)
    val foodSecurity = bool("food_security").default(false)
    val housing = bool("housing").default(false)
    val spiritual = bool("spiritual").default(false)
    val transportation = bool("transportation").default(false)

    val lastUpdate = datetime("lastUpdate").defaultExpression(CurrentDateTime)
}