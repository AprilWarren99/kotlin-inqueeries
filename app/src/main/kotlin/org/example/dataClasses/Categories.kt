package org.example.dataClasses

import org.example.model.CategoriesTable
import org.jetbrains.exposed.v1.core.ResultRow

data class Categories (
    val id: Int,

    //education
    val isEducation: Boolean,

    val individual: Boolean,
    val organization: Boolean,
    val postSecondary: Boolean,
    val remoteOnline: Boolean,
    val workshopsOrTraining: Boolean,

    //health
    val isHealthCare: Boolean,

    val healthCentre: Boolean,
    val counselor: Boolean,
    val familyDoctor: Boolean,
    val mental: Boolean,
    val peerSupport: Boolean,
    val physical: Boolean,
    val private: Boolean,
    val public: Boolean,
    val specialist: Boolean,
    val trans: Boolean,

    //hospitality
    val isHospitality: Boolean,

    val bar: Boolean,
    val cafe: Boolean,
    val catering: Boolean,
    val foodTruck: Boolean,
    val hotel: Boolean,
    val restaurant: Boolean,

    //retail
    val isRetail: Boolean,
    val isAdult: Boolean, //19+
    val adultProducts: Boolean,
    val artist: Boolean,
    val clothing: Boolean,
    val consultant: Boolean,
    val convenience: Boolean,
    val digitalServices: Boolean,
    val entertainment: Boolean,
    val esthetics: Boolean,
    val fitnessCentre: Boolean,
    val groceries: Boolean,
    val legal: Boolean,
    val skilledTrades: Boolean,

    //other
    val isOther: Boolean,
    val employment: Boolean,
    val foodSecurity: Boolean,
    val housing: Boolean,
    val spiritual: Boolean,
    val transportation: Boolean,
){
    companion object{
        fun fromRow(resultRow: ResultRow) = Categories(
            id = resultRow[CategoriesTable.id],

            //education
            isEducation = resultRow[CategoriesTable.isEducation],

            individual = resultRow[CategoriesTable.individual],
            organization = resultRow[CategoriesTable.organization],
            postSecondary = resultRow[CategoriesTable.postSecondary],
            remoteOnline = resultRow[CategoriesTable.remoteOnline],
            workshopsOrTraining = resultRow[CategoriesTable.workshopsOrTraining],

            //health
            isHealthCare = resultRow[CategoriesTable.isHealthCare],

            // healthCareProvider:
            healthCentre = resultRow[CategoriesTable.healthCentre],
            counselor = resultRow[CategoriesTable.counselor],
            familyDoctor = resultRow[CategoriesTable.familyDoctor],
            mental = resultRow[CategoriesTable.mental],
            peerSupport = resultRow[CategoriesTable.peerSupport],
            physical = resultRow[CategoriesTable.physical],
            private = resultRow[CategoriesTable.private],
            public = resultRow[CategoriesTable.public],
            specialist = resultRow[CategoriesTable.specialist],
            trans = resultRow[CategoriesTable.trans],

            //hospitality
            isHospitality = resultRow[CategoriesTable.isHospitality],

            bar = resultRow[CategoriesTable.bar],
            cafe = resultRow[CategoriesTable.cafe],
            catering = resultRow[CategoriesTable.catering],
            foodTruck = resultRow[CategoriesTable.foodTruck],
            hotel = resultRow[CategoriesTable.hotel],
            restaurant = resultRow[CategoriesTable.restaurant],

            //retail
            isRetail = resultRow[CategoriesTable.isRetail],
            isAdult = resultRow[CategoriesTable.isAdult], //19+
            adultProducts = resultRow[CategoriesTable.adultProducts],
            artist = resultRow[CategoriesTable.artist],
            clothing = resultRow[CategoriesTable.clothing],
            consultant = resultRow[CategoriesTable.consultant],
            convenience = resultRow[CategoriesTable.convenience],
            digitalServices = resultRow[CategoriesTable.digitalServices],
            entertainment = resultRow[CategoriesTable.entertainment],
            esthetics = resultRow[CategoriesTable.esthetics],
            fitnessCentre = resultRow[CategoriesTable.fitnessCentre],
            groceries = resultRow[CategoriesTable.groceries],
            legal = resultRow[CategoriesTable.legal],
            skilledTrades = resultRow[CategoriesTable.skilledTrades],

            //other
            isOther = resultRow[CategoriesTable.isOther],
            employment = resultRow[CategoriesTable.employment],
            foodSecurity = resultRow[CategoriesTable.foodSecurity],
            housing = resultRow[CategoriesTable.housing],
            spiritual = resultRow[CategoriesTable.spiritual],
            transportation = resultRow[CategoriesTable.transportation],
        )
    }
}