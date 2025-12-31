package org.example.htmx.pages

import io.ktor.htmx.HxSwap
import io.ktor.htmx.html.hx
import io.ktor.utils.io.ExperimentalKtorApi
import kotlinx.html.*
import org.example.dataClasses.AccessibilityInformation
import org.example.dataClasses.Categories
import org.example.dataClasses.Contact
import org.example.dataClasses.Organization
import org.example.htmx.insertHead
import org.example.htmx.insertHeader
import java.time.format.DateTimeFormatter

fun HTML.updateOrganizationPage(org: Organization, contacts: List<Contact>, path: String,
                                accessInfo: AccessibilityInformation,
                                categoriesInfo: Categories){
    insertHead(){
        script {
            src = "/static/js/form-toggles.js"
        }
    }
    body{
        insertHeader(path, org.name)
        insertOrgForm(org, contacts, accessInfo, categoriesInfo)
    }
}

@OptIn(ExperimentalKtorApi::class)
private fun FlowContent.insertOrgForm(org: Organization, contacts: List<Contact>,
                                      accessInfo: AccessibilityInformation,
                                      categoriesInfo: Categories){

    form{
        hiddenInput {
            name = "accessibilityID"
            id = "accessibilityID"
            value = accessInfo.id.toString()
        }
        hiddenInput {
            name = "categoriesID"
            id = "categoriesID"
            value = categoriesInfo.id.toString()
        }

        div {
            style="display: flex; justify-content: space-evenly;"
            fieldSet {
                style="display: flex; flex-direction: column;"
                classes = setOf("update-form-business-data")
                legend { +"Organization Data" }
                label {
                    htmlFor = "name"
                    +"Business Name:"
                }
                input {
                    type = InputType.text
                    name = "name"
                    id = "name"
                    value = org.name
                }
                br

                label {
                    htmlFor = "description"
                    +"Description:"
                }
                textArea {
                    name = "description"
                    id = "description"
                    rows = "5"
                    cols = "30"
                    +"${org.description}"
                }
                br

                label {
                    htmlFor = "streetAddress"
                    +"Street Address:"
                }
                input {
                    type = InputType.text
                    name = "streetAddress"
                    id = "streetAddress"
                    value = org.streetAddress ?: ""
                }
                br

                label {
                    htmlFor = "city"
                    +"City:"
                }
                input {
                    type = InputType.text
                    name = "city"
                    id = "city"
                    value = org.city ?: ""
                }
                br

                label {
                    htmlFor = "province"
                    +"Province:"
                }
                input {
                    type = InputType.text
                    name = "province"
                    id = "province"
                    value = org.province ?: ""
                }
                br
                label {
                    htmlFor = "phone"
                    +"Phone:"
                }
                input {
                    type = InputType.text
                    name = "phone"
                    id = "phone"
                    value = org.phoneNumber ?: ""
                }
                br

                label {
                    htmlFor = "email"
                    +"Business Email:"
                }
                input {
                    type = InputType.text
                    name = "email"
                    id = "email"
                    value = org.email ?: ""
                }
                br


                label {
                    htmlFor = "website"
                    +"Website"
                }
                input {
                    type = InputType.url
                    name = "website"
                    id = "website"
                    value = org.website ?: ""
                }
                br
                label {
                    htmlFor = "socialMedia"
                    +"Social Media:"
                }
                input {
                    type = InputType.text
                    name = "socialMedia"
                    id = "socialMedia"
                    value = org.socialMedia ?: ""
                }
                br

                div {
                    classes = setOf("toggleWrapper")
                    checkBoxInput {
                        classes = setOf("toggle")
                        name = "queerOwned"
                        id = "queerOwned"
                        checked = org.queerOwned == true
                    }
                    label {
                        htmlFor = "queerOwned"
                        classes = setOf("toggleLabel")
                        +"Queer Owned: "

                        span {
                            classes = setOf("switch")
                            span {
                                classes = setOf("track")
                            }
                            span {
                                classes = setOf("knob")
                            }
                        }
                    }
                }
                br

                div {
                    classes = setOf("toggleWrapper")
                    checkBoxInput {
                        classes = setOf("toggle")
                        name = "queerInclusive"
                        id = "queerInclusive"
                        checked = org.queerInclusive == true
                    }
                    label {
                        htmlFor = "queerInclusive"
                        classes = setOf("toggleLabel")
                        +"Queer Inclusive: "

                        span {
                            classes = setOf("switch")
                            span {
                                classes = setOf("track")
                            }
                            span {
                                classes = setOf("knob")
                            }
                        }
                    }
                }

                label {
                    htmlFor = "otherInformation"
                    +"Other Notes"
                }
                textArea {
                    name = "otherInformation"
                    id = "otherInformation"
                    rows = "5"
                    cols = "30"
                    +(org.otherInformation ?: "")
                }
                p {
                    +"Last Updated: ${
                        org.lastUpdate.format(
                            DateTimeFormatter.ofPattern("MMMM, dd, YYYY - hh:mm a")
                        )
                    }"
                }
            }
            div {
                style = "display: flex; flex-direction: column;"
                contacts.forEachIndexed { index, contact ->
                    val contactNumber = index + 1
                    fieldSet {
                        style="display: flex; flex-direction: column;"
                        classes = setOf("update-form-contact")
                        legend { +"Contact $contactNumber" }

                        hiddenInput {
                            name = "contact${index}ID"
                            id = "contact${index}ID"
                            value = contact.id.toString()
                        }
                        hiddenInput {
                            name = "contact${index}organizationID"
                            id = "contact${index}organizationID"
                            value = contact.organizationID.toString()
                        }
                        label {
                            htmlFor = "contact${index}Name"
                            +"Name:"
                        }
                        input {
                            type = InputType.text
                            name = "contact${index}Name"
                            id = "contact${index}Name"
                            value = contact.name
                        }
                        br

                        label {
                            htmlFor = "contact${index}Pronouns"
                            +"Pronouns:"
                        }
                        input {
                            type = InputType.text
                            name = "contact${index}Pronouns"
                            id = "contact${index}Pronouns"
                            value = contact.pronouns ?: ""
                        }
                        br

                        label {
                            htmlFor = "contact${index}Position"
                            +"Position:"
                        }
                        input {
                            type = InputType.text
                            name = "contact${index}Position"
                            id = "contact${index}Position"
                            value = contact.position ?: ""
                        }
                        br

                        label {
                            htmlFor = "contact${index}DirectEmail"
                            +"Direct Email:"
                        }
                        input {
                            type = InputType.text
                            name = "contact${index}DirectEmail"
                            id = "contact${index}DirectEmail"
                            value = contact.directEmail ?: ""
                        }
                        br

                        label {
                            htmlFor = "contact${index}DirectPhone"
                            +"Phone Number:"
                        }
                        input {
                            type = InputType.text
                            name = "contact${index}DirectPhone"
                            id = "contact${index}DirectPhone"
                            value = contact.directPhone ?: ""
                        }
                        br

                        hiddenInput{
                            name = "contact${index}lastUpdate"
                            id = "contact${index}lastUpdate"
                            value = contact.lastUpdate.toString()
                        }

                        p {
                            +"Last Updated: ${
                                contact.lastUpdate.format(
                                    DateTimeFormatter.ofPattern("MMMM, dd, YYYY - hh:mm a")
                                )
                            }"
                        }
                    }
                }

                div {
                    id = "contactFormContainer"
                }
                button {
                    style = "width: 50%; max-width: 200px; margin: 10px; align-self: center;"
                    attributes.hx {
                        get = "/update/get-new-contact-form"
                        target = "#contactFormContainer"
                        swap = HxSwap.outerHtml
                        trigger = "click"
                    }
                    +"+ Add new contact"
                }
            }
        }
        div{
            style="margin: 0 auto; display: flex; flex-direction: column; width: 80%; max-width: 1500px; align-items: center;"
            fieldSet {
                style = "width: 100%; max-width: 1200px; display: flex; flex-wrap: wrap; justify-content: center; gap: 1em;"
                legend{ +"Accessibility Information"}
                div {
                    classes = setOf("toggleWrapper")
                    checkBoxInput {
                        classes = setOf("toggle")
                        name = "automaticDoors"
                        id = "automaticDoors"
                        checked = accessInfo.automaticDoors == true
                    }
                    label {
                        htmlFor = "automaticDoors"
                        classes = setOf("toggleLabel")
                        +"Automatic Doors"
                        span {
                            classes = setOf("switch")
                            span {
                                classes = setOf("track")
                            }
                            span {
                                classes = setOf("knob")
                            }
                        }
                    }
                }
                div {
                    classes = setOf("toggleWrapper")
                    checkBoxInput {
                        classes = setOf("toggle")
                        name = "entrance"
                        id = "entrance"
                        checked = accessInfo.entrance == true
                    }
                    label {
                        htmlFor = "entrance"
                        classes = setOf("toggleLabel")
                        +"Entrance"
                        span {
                            classes = setOf("switch")
                            span {
                                classes = setOf("track")
                            }
                            span {
                                classes = setOf("knob")
                            }
                        }
                    }
                }
                div {
                    classes = setOf("toggleWrapper")
                    checkBoxInput {
                        classes = setOf("toggle")
                        name = "parking"
                        id = "parking"
                        checked = accessInfo.parking == true
                    }
                    label {
                        htmlFor = "parking"
                        classes = setOf("toggleLabel")
                        +"Accessible Parking"
                        span {
                            classes = setOf("switch")
                            span {
                                classes = setOf("track")
                            }
                            span {
                                classes = setOf("knob")
                            }
                        }
                    }
                }
                div {
                    classes = setOf("toggleWrapper")
                    checkBoxInput {
                        classes = setOf("toggle")
                        name = "accessibleBathroom"
                        id = "accessibleBathroom"
                        checked = accessInfo.accessibleBathroom == true
                    }
                    label {
                        htmlFor = "accessibleBathroom"
                        classes = setOf("toggleLabel")
                        +"Accessible Bathroom"
                        span {
                            classes = setOf("switch")
                            span {
                                classes = setOf("track")
                            }
                            span {
                                classes = setOf("knob")
                            }
                        }
                    }
                }
                div {
                    classes = setOf("toggleWrapper")
                    checkBoxInput {
                        classes = setOf("toggle")
                        name = "genderNeutralBathroom"
                        id = "genderNeutralBathroom"
                        checked = accessInfo.genderNeutralBathroom == true
                    }
                    label {
                        htmlFor = "genderNeutralBathroom"
                        classes = setOf("toggleLabel")
                        +"Gender Neutral Bathroom"
                        span {
                            classes = setOf("switch")
                            span {
                                classes = setOf("track")
                            }
                            span {
                                classes = setOf("knob")
                            }
                        }
                    }
                }
                p { +"Last Updated: ${
                    accessInfo.lastUpdate.format(
                        DateTimeFormatter.ofPattern("MMMM, dd, YYYY - hh:mm a")
                    )
                }" }
            }
            fieldSet {
                style = "width: 100%; max-width: 1200px; display: flex; flex-direction: column; justify-content: flex-start; gap: 1em;"
                legend { +"Categories" }
                // ------------- Education Categories -------------
                div {
                    classes = setOf("toggleWrapper")
                    checkBoxInput {
                        classes = setOf("toggle", "categoryToggle")
                        name = "isEducation"
                        id = "isEducation"
                        checked = categoriesInfo.isEducation == true
                    }
                    label {
                        htmlFor = "isEducation"
                        classes = setOf("toggleLabel")
                        +"Education"
                        span {
                            classes = setOf("switch")
                            span {
                                classes = setOf("track")
                            }
                            span {
                                classes = setOf("knob")
                            }
                        }
                    }
                }
                div{
                    classes = setOf("categoriesSubgroup")
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "individual"
                            id = "individual"
                            checked = categoriesInfo.individual == true
                        }
                        label {
                            htmlFor = "individual"
                            classes = setOf("toggleLabel")
                            +"Individual"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "organization"
                            id = "organization"
                            checked = categoriesInfo.organization == true
                        }
                        label {
                            htmlFor = "organization"
                            classes = setOf("toggleLabel")
                            +"Organization"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "postSecondary"
                            id = "postSecondary"
                            checked = categoriesInfo.postSecondary == true
                        }
                        label {
                            htmlFor = "postSecondary"
                            classes = setOf("toggleLabel")
                            +"Post Secondary"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "remoteOnline"
                            id = "remoteOnline"
                            checked = categoriesInfo.remoteOnline == true
                        }
                        label {
                            htmlFor = "remoteOnline"
                            classes = setOf("toggleLabel")
                            +"Remote/Online"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "workshopsOrTraining"
                            id = "workshopsOrTraining"
                            checked = categoriesInfo.workshopsOrTraining == true
                        }
                        label {
                            htmlFor = "workshopsOrTraining"
                            classes = setOf("toggleLabel")
                            +"Workshop Or Training"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                }
                // ------------- Health care categories -------------
                div {
                    classes = setOf("toggleWrapper")
                    checkBoxInput {
                        classes = setOf("toggle", "categoryToggle")
                        name = "isHealthCare"
                        id = "isHealthCare"
                        checked = categoriesInfo.isHealthCare == true
                    }
                    label {
                        htmlFor = "isHealthCare"
                        classes = setOf("toggleLabel")
                        +"Health Care"
                        span {
                            classes = setOf("switch")
                            span {
                                classes = setOf("track")
                            }
                            span {
                                classes = setOf("knob")
                            }
                        }
                    }
                }
                div{
                    classes = setOf("categoriesSubgroup")
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "healthCentre"
                            id = "healthCentre"
                            checked = categoriesInfo.healthCentre == true
                        }
                        label {
                            htmlFor = "healthCentre"
                            classes = setOf("toggleLabel")
                            +"Health Centre"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "counselor"
                            id = "counselor"
                            checked = categoriesInfo.counselor == true
                        }
                        label {
                            htmlFor = "counselor"
                            classes = setOf("toggleLabel")
                            +"Counselor"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "familyDoctor"
                            id = "familyDoctor"
                            checked = categoriesInfo.familyDoctor == true
                        }
                        label {
                            htmlFor = "familyDoctor"
                            classes = setOf("toggleLabel")
                            +"Family Doctor"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "mental"
                            id = "mental"
                            checked = categoriesInfo.mental == true
                        }
                        label {
                            htmlFor = "mental"
                            classes = setOf("toggleLabel")
                            +"Mental Health"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "peerSupport"
                            id = "peerSupport"
                            checked = categoriesInfo.peerSupport == true
                        }
                        label {
                            htmlFor = "peerSupport"
                            classes = setOf("toggleLabel")
                            +"Peer Support"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "physical"
                            id = "physical"
                            checked = categoriesInfo.physical == true
                        }
                        label {
                            htmlFor = "physical"
                            classes = setOf("toggleLabel")
                            +"Physical Health"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "private"
                            id = "private"
                            checked = categoriesInfo.private == true
                        }
                        label {
                            htmlFor = "private"
                            classes = setOf("toggleLabel")
                            +"Private"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "public"
                            id = "public"
                            checked = categoriesInfo.public == true
                        }
                        label {
                            htmlFor = "public"
                            classes = setOf("toggleLabel")
                            +"Public"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "specialist"
                            id = "specialist"
                            checked = categoriesInfo.specialist == true
                        }
                        label {
                            htmlFor = "specialist"
                            classes = setOf("toggleLabel")
                            +"Specialist"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "trans"
                            id = "trans"
                            checked = categoriesInfo.trans == true
                        }
                        label {
                            htmlFor = "trans"
                            classes = setOf("toggleLabel")
                            +"Gender Affirming Care"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                }
                // ------------- Hospitality -------------
                div {
                    classes = setOf("toggleWrapper")
                    checkBoxInput {
                        classes = setOf("toggle", "categoryToggle")
                        name = "isHospitality"
                        id = "isHospitality"
                        checked = categoriesInfo.isHospitality == true
                    }
                    label {
                        htmlFor = "isHospitality"
                        classes = setOf("toggleLabel")
                        +"Hospitality"
                        span {
                            classes = setOf("switch")
                            span {
                                classes = setOf("track")
                            }
                            span {
                                classes = setOf("knob")
                            }
                        }
                    }
                }
                div{
                    classes = setOf("categoriesSubgroup")
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "bar"
                            id = "bar"
                            checked = categoriesInfo.bar == true
                        }
                        label {
                            htmlFor = "Bar"
                            classes = setOf("toggleLabel")
                            +"Bar"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                        div {
                            classes = setOf("toggleWrapper")
                            checkBoxInput {
                                classes = setOf("toggle")
                                name = "cafe"
                                id = "cafe"
                                checked = categoriesInfo.cafe == true
                            }
                            label {
                                htmlFor = "cafe"
                                classes = setOf("toggleLabel")
                                +"Cafe"
                                span {
                                    classes = setOf("switch")
                                    span {
                                        classes = setOf("track")
                                    }
                                    span {
                                        classes = setOf("knob")
                                    }
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "catering"
                            id = "catering"
                            checked = categoriesInfo.catering == true
                        }
                        label {
                            htmlFor = "catering"
                            classes = setOf("toggleLabel")
                            +"Catering"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "foodTruck"
                            id = "foodTruck"
                            checked = categoriesInfo.foodTruck == true
                        }
                        label {
                            htmlFor = "foodTruck"
                            classes = setOf("toggleLabel")
                            +"Food Truck"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "hotel"
                            id = "hotel"
                            checked = categoriesInfo.hotel == true
                        }
                        label {
                            htmlFor = "hotel"
                            classes = setOf("toggleLabel")
                            +"Hotel"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "restaurant"
                            id = "restaurant"
                            checked = categoriesInfo.restaurant == true
                        }
                        label {
                            htmlFor = "restaurant"
                            classes = setOf("toggleLabel")
                            +"Restaurant"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                }
                // ------------- Retail -------------
                div {
                    classes = setOf("toggleWrapper")
                    checkBoxInput {
                        classes = setOf("toggle", "categoryToggle")
                        name = "isRetail"
                        id = "isRetail"
                        checked = categoriesInfo.isRetail == true
                    }
                    label {
                        htmlFor = "isRetail"
                        classes = setOf("toggleLabel")
                        +"Retail"
                        span {
                            classes = setOf("switch")
                            span {
                                classes = setOf("track")
                            }
                            span {
                                classes = setOf("knob")
                            }
                        }
                    }
                }
                div {
                    classes = setOf("categoriesSubgroup")
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "isAdult"
                            id = "isAdult"
                            checked = categoriesInfo.isAdult == true
                        }
                        label {
                            htmlFor = "isAdult"
                            classes = setOf("toggleLabel")
                            +"Adult"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "adultProducts"
                            id = "adultProducts"
                            checked = categoriesInfo.adultProducts == true
                        }
                        label {
                            htmlFor = "adultProducts"
                            classes = setOf("toggleLabel")
                            +"Adult Products"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                        div {
                            classes = setOf("toggleWrapper")
                            checkBoxInput {
                                classes = setOf("toggle")
                                name = "artist"
                                id = "artist"
                                checked = categoriesInfo.artist == true
                            }
                            label {
                                htmlFor = "artist"
                                classes = setOf("toggleLabel")
                                +"Artist"
                                span {
                                    classes = setOf("switch")
                                    span {
                                        classes = setOf("track")
                                    }
                                    span {
                                        classes = setOf("knob")
                                    }
                                }
                            }
                        }
                        div {
                            classes = setOf("toggleWrapper")
                            checkBoxInput {
                                classes = setOf("toggle")
                                name = "clothing"
                                id = "clothing"
                                checked = categoriesInfo.clothing == true
                            }
                            label {
                                htmlFor = "clothing"
                                classes = setOf("toggleLabel")
                                +"Clothing"
                                span {
                                    classes = setOf("switch")
                                    span {
                                        classes = setOf("track")
                                    }
                                    span {
                                        classes = setOf("knob")
                                    }
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "consultant"
                            id = "consultant"
                            checked = categoriesInfo.consultant == true
                        }
                        label {
                            htmlFor = "consultant"
                            classes = setOf("toggleLabel")
                            +"Consultant"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "convenience"
                            id = "convenience"
                            checked = categoriesInfo.convenience == true
                        }
                        label {
                            htmlFor = "convenience"
                            classes = setOf("toggleLabel")
                            +"Convenience"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "digitalServices"
                            id = "digitalServices"
                            checked = categoriesInfo.digitalServices == true
                        }
                        label {
                            htmlFor = "digitalServices"
                            classes = setOf("toggleLabel")
                            +"Digital Services"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "entertainment"
                            id = "entertainment"
                            checked = categoriesInfo.entertainment == true
                        }
                        label {
                            htmlFor = "entertainment"
                            classes = setOf("toggleLabel")
                            +"Entertainment"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "esthetics"
                            id = "esthetics"
                            checked = categoriesInfo.esthetics == true
                        }
                        label {
                            htmlFor = "esthetics"
                            classes = setOf("toggleLabel")
                            +"Esthetics"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "fitnessCentre"
                            id = "fitnessCentre"
                            checked = categoriesInfo.fitnessCentre == true
                        }
                        label {
                            htmlFor = "fitnessCentre"
                            classes = setOf("toggleLabel")
                            +"Fitness Centre"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "groceries"
                            id = "groceries"
                            checked = categoriesInfo.groceries == true
                        }
                        label {
                            htmlFor = "groceries"
                            classes = setOf("toggleLabel")
                            +"Groceries"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "legal"
                            id = "legal"
                            checked = categoriesInfo.legal == true
                        }
                        label {
                            htmlFor = "legal"
                            classes = setOf("toggleLabel")
                            +"Legal"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "skilledTrades"
                            id = "skilledTrades"
                            checked = categoriesInfo.skilledTrades == true
                        }
                        label {
                            htmlFor = "skilledTrades"
                            classes = setOf("toggleLabel")
                            +"Skilled Trades"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                }
                // ------------- Other -------------
                div {
                    classes = setOf("toggleWrapper")
                    checkBoxInput {
                        classes = setOf("toggle", "categoryToggle")
                        name = "isOther"
                        id = "isOther"
                        checked = categoriesInfo.isOther == true
                    }
                    label {
                        htmlFor = "isOther"
                        classes = setOf("toggleLabel")
                        +"Other"
                        span {
                            classes = setOf("switch")
                            span {
                                classes = setOf("track")
                            }
                            span {
                                classes = setOf("knob")
                            }
                        }
                    }
                }
                div {
                    classes = setOf("categoriesSubgroup")
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "employment"
                            id = "employment"
                            checked = categoriesInfo.employment == true
                        }
                        label {
                            htmlFor = "employment"
                            classes = setOf("toggleLabel")
                            +"Employment"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("categoriesSubgroup")
                        div {
                            classes = setOf("toggleWrapper")
                            checkBoxInput {
                                classes = setOf("toggle")
                                name = "foodSecurity"
                                id = "foodSecurity"
                                checked = categoriesInfo.foodSecurity == true
                            }
                            label {
                                htmlFor = "foodSecurity"
                                classes = setOf("toggleLabel")
                                +"Food Security"
                                span {
                                    classes = setOf("switch")
                                    span {
                                        classes = setOf("track")
                                    }
                                    span {
                                        classes = setOf("knob")
                                    }
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "housing"
                            id = "housing"
                            checked = categoriesInfo.housing == true
                        }
                        label {
                            htmlFor = "housing"
                            classes = setOf("toggleLabel")
                            +"Housing"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "spiritual"
                            id = "spiritual"
                            checked = categoriesInfo.spiritual == true
                        }
                        label {
                            htmlFor = "spiritual"
                            classes = setOf("toggleLabel")
                            +"Spirituality"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf("toggleWrapper")
                        checkBoxInput {
                            classes = setOf("toggle")
                            name = "transport"
                            id = "transport"
                            checked = categoriesInfo.transportation == true
                        }
                        label {
                            htmlFor = "transport"
                            classes = setOf("toggleLabel")
                            +"Transport"
                            span {
                                classes = setOf("switch")
                                span {
                                    classes = setOf("track")
                                }
                                span {
                                    classes = setOf("knob")
                                }
                            }
                        }
                    }
                }
            }
            submitInput {
                attributes["hx-post"] = "/update/${org.id}"
                attributes["hx-target"] = "#submissionStatus"
            }
        }
        div { id = "submissionStatus"}
    }
}