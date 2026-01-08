package org.example.htmx.pages

import kotlinx.coroutines.flow.Flow
import kotlinx.html.ButtonType
import kotlinx.html.FlowContent
import kotlinx.html.HTML
import kotlinx.html.InputType
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.button
import kotlinx.html.checkBoxInput
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.fieldSet
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.h3
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.label
import kotlinx.html.legend
import kotlinx.html.onClick
import kotlinx.html.p
import kotlinx.html.a
import kotlinx.html.emailInput
import kotlinx.html.script
import kotlinx.html.section
import kotlinx.html.span
import kotlinx.html.style
import kotlinx.html.submitInput
import kotlinx.html.telInput
import kotlinx.html.textArea
import kotlinx.html.urlInput
import org.example.htmx.insertHead
import org.example.htmx.insertHeader
import java.time.format.DateTimeFormatter

fun HTML.addOrganizationPage(path: String,){
    insertHead(){
        script {
            src = "/static/js/form-next-button.js"
        }
    }
    body{
        insertHeader(path, "Add Organization")
        addOrganizationForm()
    }
}

fun FlowContent.addOrganizationForm(){
    div {
        id = "newOrganizationFormArea"
        form {
            id="newOrgForm"

            attributes["novalidate"] = "true"

            attributes["hx-post"] = "/add/new-organization"
            attributes["hx-swap"] = "outerHTML"

            formHeader("Disclaimer Information", 1)
            disclaimerInformation()
            orgInformation()
            contactData(1)
            categoriesData()
            accessibilityInformation()
        }
    }
}



fun FlowContent.formHeader(title: String, pageNumber: Int){
    h2 {
        id = "formPageTitle"
        style = "justify-self: center;"
        +title
    }
    div{
        style = "justify-self: center;"
        classes = setOf("progressBubbles")
        id = "progressBubbles"
        span { classes = if(pageNumber == 1) setOf("filledCircle") else setOf("circle") }
        span { classes = if(pageNumber == 2) setOf("filledCircle") else setOf("circle") }
        span { classes = if(pageNumber == 3) setOf("filledCircle") else setOf("circle") }
        span { classes = if(pageNumber == 4) setOf("filledCircle") else setOf("circle") }
        span { classes = if(pageNumber == 4) setOf("filledCircle") else setOf("circle") }
    }
    br
}
fun FlowContent.disclaimerInformation(){
    fieldSet {
        id = "newOrgDisclaimer"
        style = "display: flex; flex-direction: column;"


        h3 { +"What is this project?" }
        p { +"Inqueeries is a data repository of queer owned and inclusive companies/organizations. It is our goal to connect individuals on the 2SLGBTQIA+ spectrum to companies/organizations that are welcoming and supportive. We also aim to highlight such places that are owned and operated by such folks. Organizations who proceed with registration will be listed within search results and on other pages." }
        h3 { +"Ownership of data" }
        p { +"Inqueeries is a project developed by an individual, and seeks no profit. We maintain full ownership and are absolutely unwilling to disclose, disperse, or otherwise provide any information to a 3rd party in exchange for compensation, monetary or otherwise. Inqueeries our own database (hosted on AWS) and provides no access to third parties." }
        p { +"Should this project ever be taken over by another organization, it will be a requirement that this ownership and commitment to data privacy be maintained." }
        h3 { +"Requirements for listing with us"}
        p { +"An individuals contact must be provided as part of registration. This will include name, pronouns, position within the organization, email, and phone for purposes of information confirmation and updates. As we are not a profit seeking organization, we will never send you an email or otherwise reach out unless it is for the express purposes of verifying your organizations information, confirming a request for an update/deletion of your business/organization. data, or any other information that may directly impact the listing of your business/organization." }
        p { +"By default, any contact information given as part of this requirement will not be publicly listed and will be used for the  sole purposes of verifying details submitted, reaching out in cases where updates may be needed for a submission, or informing the submitting party of any change in their listing."}
        p { +"The provided contact person should be someone who is a representative of the company/organization and can make decisions on behalf of the company/organization being listed. Should we be unable to confirm the contact persons registration, the business will still be listed, but the listing will clearly state that all information pertaining to the listing is not verified." }
        p {
            +"Should you wish to claim ownership of a unverified listing, or raise a concern with a listing, please contact "
            a(href = "mailto:listings@inqueeries.ca") {
                +"listings@inqueeries.ca"
            }
        }
    button {
            id = "contactInfoNextButton"
            type = ButtonType.button
            onClick = "nextFormPage('newOrgDisclaimer', 'orgInfo')"
            +"Accept & Proceed"
        }
    }
}
fun FlowContent.orgInformation(){
    fieldSet {
        style = "display: none; flex-direction: column;"
        id = "orgInfo"
        label {
            htmlFor = "name"
            +"* Business Name:"
        }
        input {
            type = InputType.text
            name = "name"
            id = "name"
            required = true
            maxLength = "128"
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
            maxLength = "1024"
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
            maxLength = "128"
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
            maxLength = "2"
        }
        br
        label {
            htmlFor = "phone"
            +"Phone:"
        }
        telInput {
            name = "phone"
            id = "phone"
            maxLength = "12"
        }
        br

        label {
            htmlFor = "email"
            +"Business Email:"
        }
        emailInput {
            name = "email"
            id = "email"
        }
        br


        label {
            htmlFor = "website"
            +"Website"
        }
        urlInput {
            type = InputType.url
            name = "website"
            id = "website"
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
        }
        br

        div {
            classes = setOf("toggleWrapper")
            checkBoxInput {
                classes = setOf("toggle")
                name = "queerOwned"
                id = "queerOwned"
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
            maxLength = "512"
        }
        div{
            style = "display: flex; flex-direction: row;"
            button {
                style = "width: 50%;"
                id = "contactInfoNextButton"
                type = ButtonType.button
                onClick = "previousFormPage('orgInfo', 'newOrgDisclaimer')"
                +"Previous"
            }
            button {
                style = "width: 50%;"
                id = "contactInfoNextButton"
                type = ButtonType.button
                onClick = "nextFormPage('orgInfo', 'contactInfo')"
                +"Next"
            }
        }
    }
}
fun FlowContent.contactData(contactNumber: Int){
    fieldSet {
        id = "contactInfo"
        style = "display: none; flex-direction: column;"

        label {
            htmlFor = "contact${contactNumber}Name"
            +"* Name:"
        }
        input {
            type = InputType.text
            name = "contact${contactNumber}Name"
            id = "contact${contactNumber}Name"
            required = true
            maxLength = "128"
        }
        br

        label {
            htmlFor = "contact${contactNumber}Pronouns"
            +"Pronouns:"
        }
        input {
            type = InputType.text
            name = "contact${contactNumber}Pronouns"
            id = "contact${contactNumber}Pronouns"
            maxLength = "30"
        }
        br

        label {
            htmlFor = "contact${contactNumber}Position"
            +"Position:"
        }
        input {
            type = InputType.text
            name = "contact${contactNumber}Position"
            id = "contact${contactNumber}Position"
            maxLength = "120"
        }
        br

        label {
            htmlFor = "contact${contactNumber}DirectEmail"
            +"* Direct Email:"
        }
        emailInput {
            name = "contact${contactNumber}DirectEmail"
            id = "contact${contactNumber}DirectEmail"
            required = true
            maxLength = "128"
        }
        br

        label {
            htmlFor = "contact${contactNumber}DirectPhone"
            +"Phone Number:"
        }
        telInput {
            name = "contact${contactNumber}DirectPhone"
            id = "contact${contactNumber}DirectPhone"
            maxLength = "10"
        }
        br
        div{
            style = " display: flex; flex-direction: row;"
            button {
                style = "width: 50%;"
                id = "contactInfoNextButton"
                type = ButtonType.button
                onClick = "previousFormPage('contactInfo', 'orgInfo')"
                +"Previous"
            }
            button {
                style = "width: 50%;"
                id = "contactInfoNextButton"
                type = ButtonType.button
                onClick = "nextFormPage('contactInfo', 'categoriesInfo')"
                +"Next"
            }
        }
    }
}
fun FlowContent.categoriesData() {
    fieldSet {
        id = "categoriesInfo"
        style = "display: none; flex-direction: column;"

        // ------------- Education Categories -------------
        div {
            classes = setOf("toggleWrapper")
            checkBoxInput {
                classes = setOf("toggle", "categoryToggle")
                name = "isEducation"
                id = "isEducation"
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
        div {
            classes = setOf("categoriesSubgroup")
            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "individual"
                    id = "individual"
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
        div {
            classes = setOf("categoriesSubgroup")
            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "healthCentre"
                    id = "healthCentre"
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
        div {
            classes = setOf("categoriesSubgroup")
            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "bar"
                    id = "bar"
                }
                label {
                    htmlFor = "bar"
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
        }
        // ------------- Retail -------------
        div {
            classes = setOf("toggleWrapper")
            checkBoxInput {
                classes = setOf("toggle", "categoryToggle")
                name = "isRetail"
                id = "isRetail"
            }
            label {
                htmlFor = "isRetail"
                classes = setOf("toggleLabel")
                +"Retail"
                span {
                    classes = setOf("switch")
                    span { classes = setOf("track") }
                    span { classes = setOf("knob") }
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
                }
                label {
                    htmlFor = "isAdult"
                    classes = setOf("toggleLabel")
                    +"Adult"
                    span {
                        classes = setOf("switch")
                        span { classes = setOf("track") }
                        span { classes = setOf("knob") }
                    }
                }
            }

            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "adultProducts"
                    id = "adultProducts"
                }
                label {
                    htmlFor = "adultProducts"
                    classes = setOf("toggleLabel")
                    +"Adult Products"
                    span {
                        classes = setOf("switch")
                        span { classes = setOf("track") }
                        span { classes = setOf("knob") }
                    }
                }
            }

            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "artist"
                    id = "artist"
                }
                label {
                    htmlFor = "artist"
                    classes = setOf("toggleLabel")
                    +"Artist"
                    span {
                        classes = setOf("switch")
                        span { classes = setOf("track") }
                        span { classes = setOf("knob") }
                    }
                }
            }

            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "clothing"
                    id = "clothing"
                }
                label {
                    htmlFor = "clothing"
                    classes = setOf("toggleLabel")
                    +"Clothing"
                    span {
                        classes = setOf("switch")
                        span { classes = setOf("track") }
                        span { classes = setOf("knob") }
                    }
                }
            }

            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "consultant"
                    id = "consultant"
                }
                label {
                    htmlFor = "consultant"
                    classes = setOf("toggleLabel")
                    +"Consultant"
                    span {
                        classes = setOf("switch")
                        span { classes = setOf("track") }
                        span { classes = setOf("knob") }
                    }
                }
            }

            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "convenience"
                    id = "convenience"
                }
                label {
                    htmlFor = "convenience"
                    classes = setOf("toggleLabel")
                    +"Convenience"
                    span {
                        classes = setOf("switch")
                        span { classes = setOf("track") }
                        span { classes = setOf("knob") }
                    }
                }
            }

            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "digitalServices"
                    id = "digitalServices"
                }
                label {
                    htmlFor = "digitalServices"
                    classes = setOf("toggleLabel")
                    +"Digital Services"
                    span {
                        classes = setOf("switch")
                        span { classes = setOf("track") }
                        span { classes = setOf("knob") }
                    }
                }
            }

            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "entertainment"
                    id = "entertainment"
                }
                label {
                    htmlFor = "entertainment"
                    classes = setOf("toggleLabel")
                    +"Entertainment"
                    span {
                        classes = setOf("switch")
                        span { classes = setOf("track") }
                        span { classes = setOf("knob") }
                    }
                }
            }

            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "esthetics"
                    id = "esthetics"
                }
                label {
                    htmlFor = "esthetics"
                    classes = setOf("toggleLabel")
                    +"Esthetics"
                    span {
                        classes = setOf("switch")
                        span { classes = setOf("track") }
                        span { classes = setOf("knob") }
                    }
                }
            }

            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "fitnessCentre"
                    id = "fitnessCentre"
                }
                label {
                    htmlFor = "fitnessCentre"
                    classes = setOf("toggleLabel")
                    +"Fitness Centre"
                    span {
                        classes = setOf("switch")
                        span { classes = setOf("track") }
                        span { classes = setOf("knob") }
                    }
                }
            }

            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "groceries"
                    id = "groceries"
                }
                label {
                    htmlFor = "groceries"
                    classes = setOf("toggleLabel")
                    +"Groceries"
                    span {
                        classes = setOf("switch")
                        span { classes = setOf("track") }
                        span { classes = setOf("knob") }
                    }
                }
            }

            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "legal"
                    id = "legal"
                }
                label {
                    htmlFor = "legal"
                    classes = setOf("toggleLabel")
                    +"Legal"
                    span {
                        classes = setOf("switch")
                        span { classes = setOf("track") }
                        span { classes = setOf("knob") }
                    }
                }
            }

            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "skilledTrades"
                    id = "skilledTrades"
                }
                label {
                    htmlFor = "skilledTrades"
                    classes = setOf("toggleLabel")
                    +"Skilled Trades"
                    span {
                        classes = setOf("switch")
                        span { classes = setOf("track") }
                        span { classes = setOf("knob") }
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
            }
            label {
                htmlFor = "isOther"
                classes = setOf("toggleLabel")
                +"Other"
                span {
                    classes = setOf("switch")
                    span { classes = setOf("track") }
                    span { classes = setOf("knob") }
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
                }
                label {
                    htmlFor = "employment"
                    classes = setOf("toggleLabel")
                    +"Employment"
                    span {
                        classes = setOf("switch")
                        span { classes = setOf("track") }
                        span { classes = setOf("knob") }
                    }
                }
            }

            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "foodSecurity"
                    id = "foodSecurity"
                }
                label {
                    htmlFor = "foodSecurity"
                    classes = setOf("toggleLabel")
                    +"Food Security"
                    span {
                        classes = setOf("switch")
                        span { classes = setOf("track") }
                        span { classes = setOf("knob") }
                    }
                }
            }

            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "housing"
                    id = "housing"
                }
                label {
                    htmlFor = "housing"
                    classes = setOf("toggleLabel")
                    +"Housing"
                    span {
                        classes = setOf("switch")
                        span { classes = setOf("track") }
                        span { classes = setOf("knob") }
                    }
                }
            }

            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "spiritual"
                    id = "spiritual"
                }
                label {
                    htmlFor = "spiritual"
                    classes = setOf("toggleLabel")
                    +"Spirituality"
                    span {
                        classes = setOf("switch")
                        span { classes = setOf("track") }
                        span { classes = setOf("knob") }
                    }
                }
            }

            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "transport"
                    id = "transport"
                }
                label {
                    htmlFor = "transport"
                    classes = setOf("toggleLabel")
                    +"Transport"
                    span {
                        classes = setOf("switch")
                        span { classes = setOf("track") }
                        span { classes = setOf("knob") }
                    }
                }
            }
        }

        div{
            style = " display: flex; flex-direction: row;"
            button {
                style = "width: 50%;"
                id = "categoriesNextButton"
                type = ButtonType.button
                onClick = "previousFormPage('categoriesInfo', 'contactInfo')"
                +"Previous"
            }
            button {
                style = "width: 50%;"
                id = "categoriesNextButton"
                type = ButtonType.button
                onClick = "nextFormPage('categoriesInfo', 'accessibilityInfo')"
                +"Next"
            }
        }

    }
}
fun FlowContent.accessibilityInformation(){
    fieldSet {
        id = "accessibilityInfo"
        style = "display: none; flex-direction: column;"

        div {
            style = "display: flex; flex-direction: row; flex-wrap: wrap;justify-content: space-evenly;"
            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "automaticDoors"
                    id = "automaticDoors"
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
                }
                label {
                    htmlFor = "entrance"
                    classes = setOf("toggleLabel")
                    +"Accessible Entrance"
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
            div {
                classes = setOf("toggleWrapper")
                checkBoxInput {
                    classes = setOf("toggle")
                    name = "parking"
                    id = "parking"
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
        }

        div {
            style = "width: 100% display: flex; flex-direction: row;"
            button {
                id = "contactInfoNextButton"
                style = "width: 50%;"
                type = ButtonType.button
                onClick = "previousFormPage('accessibilityInfo', 'categoriesInfo')"
                +"Previous"
            }
            button {
                style = "width: 50%;"
                type = ButtonType.submit
                +"Submit"
            }
        }
        div { id = "submissionStatus"}
    }
}
