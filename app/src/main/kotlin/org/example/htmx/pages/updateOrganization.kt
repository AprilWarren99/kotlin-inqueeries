package org.example.htmx.pages

import io.ktor.htmx.HxSwap
import io.ktor.htmx.html.hx
import kotlinx.html.*
import org.example.dataClasses.Contact
import org.example.dataClasses.Organization
import org.example.htmx.insertHead
import org.example.htmx.insertHeader
import java.time.format.DateTimeFormatter

fun HTML.updateOrganizationPage(org: Organization, contacts: List<Contact>, path: String){
    insertHead()
    body{
        insertHeader(path, org.name)
        insertOrgForm(org, contacts)
    }
}

private fun FlowContent.insertOrgForm(org: Organization, contacts: List<Contact>){
    form(action="/update", method = FormMethod.post){
        style="display: flex; justify-content: space-evenly;"
        fieldSet {
            classes = setOf("update-form-business-data")
            legend { +"Organization Data" }
            label{
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

            label{
                htmlFor = "description"
                +"Description:"
            }
            textArea{
                name = "description"
                id = "description"
                rows = "5"
                cols = "30"
                +"${org.description}"
            }
            br

            label{
                htmlFor = "streetAddress"
                +"Street Address:"
            }
            input{
                type = InputType.text
                name = "streetAddress"
                id = "streetAddress"
                value = org.streetAddress ?: ""
            }
            br

            label{
                htmlFor = "city"
                +"City:"
            }
            input{
                type = InputType.text
                name = "city"
                id = "city"
                value = org.city ?: ""
            }
            br

            label{
                htmlFor = "province"
                +"Province:"
            }
            input{
                type = InputType.text
                name = "province"
                id = "province"
                value = org.province ?: ""
            }
            br


            label{
                htmlFor = "phone"
                +"Phone:"
            }
            input{
                type = InputType.text
                name = "phone"
                id = "phone"
                value = org.phoneNumber.toString()
            }
            br

            label{
                htmlFor = "email"
                +"Business Email:"
            }
            input{
                type = InputType.text
                name = "email"
                id = "email"
                value = org.email ?: ""
            }
            br


            label{
                htmlFor = "website"
                +"Website"
            }
            input{
                type = InputType.url
                name = "website"
                id = "website"
                value = org.website ?: ""
            }
            br
            label{
                htmlFor = "socialMedia"
                +"Social Media:"
            }
            input{
                type = InputType.url
                name = "socialMedia"
                id = "socialMedia"
                value = org.website ?: ""
            }
            br

            label{
                htmlFor = "queerOwned"
                +"Queer Owned"
            }
            label{
                input{
                    type = InputType.radio
                    name = "queerOwned"
                    checked = org.queerOwned == true
                }
                +"yes"
            }
            label{
                input{
                    type = InputType.radio
                    name = "queerOwned"
                    checked = org.queerOwned == false
                }
                +"no"
            }
            br

            label{
                htmlFor = "queerInclusive"
                +"Queer Inclusive"
            }
            label{
                input{
                    type = InputType.radio
                    name = "queerInclusive"
                    checked = org.queerInclusive == true
                }
                +"yes"
            }
            label{
                input{
                    type = InputType.radio
                    name = "queerInclusive"
                    checked = org.queerInclusive == false
                }
                +"no"
            }
            br

            label{
                htmlFor = "otherNotes"
                +"Other Notes"
            }
            textArea {
                name = "otherNotes"
                id = "otherNotes"
                rows = "5"
                cols = "30"
                +"${org.otherInformation}"
            }
            p{
                +"Last Updated: ${org.lastUpdate.format(
                    DateTimeFormatter.ofPattern("MMMM, dd, YYYY - hh:mm a")
                )}"
            }
        }
        div {
            style="display: flex; flex-direction: column;"
            contacts.forEachIndexed { index, contact ->
                val contactNumber = index + 1
                fieldSet {
                    classes = setOf("update-form-contact")
                    legend { +"Contact $contactNumber" }
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
                        value = contact.position
                    }
                    br

                    label {
                        htmlFor = "contact${index}DirectEmail"
                        +"Direct Email:"
                    }
                    input {
                        type = InputType.text
                        name = "contact${index}"
                        id = "contact${index}"
                        value = contact.directPhone ?: ""
                    }
                    br

                    label {
                        htmlFor = "contact${index}directPhone"
                        +"Phone Number:"
                    }
                    input {
                        type = InputType.text
                        name = "contact${index}directPhone"
                        id = "contact${index}directPhone"
                        value = contact.directPhone ?: ""
                    }
                    br

                    p {
                        +"Last Updated: ${
                            contact.lastUpdate.format(
                                DateTimeFormatter.ofPattern("MMMM, dd, YYYY - hh:mm a")
                            )
                        }"
                    }
                }
            }

            div{
                id = "contactFormContainer"
            }
            button {
                style="width: 50%; max-width: 200px; margin: 10px; align-self: center;"
                attributes.hx{
                    get = "/update/get-new-contact-form"
                    target = "#contactFormContainer"
                    swap = HxSwap.outerHtml
                    trigger = "click"
                }
                +"+ Add new contact"
            }
        }
    }
}

private fun FIELDSET.newContact(){
    fieldSet {
        classes = setOf("update-form-contact")
        legend { +"New Contact" }
        label {
            htmlFor = "newName"
            +"Name:"
        }
        input {
            type = InputType.text
            name = "newName"
            id = "newName"
        }
        br

        label {
            htmlFor = "newPronouns"
            +"Pronouns:"
        }
        input {
            type = InputType.text
            name = "newPronouns"
            id = "newPronouns"
        }
        br

        label {
            htmlFor = "newPosition"
            +"Position:"
        }
        input {
            type = InputType.text
            name = "newPosition"
            id = "newPosition"
        }
        br

        label {
            htmlFor = "newEmail"
            +"Direct Email:"
        }
        input {
            type = InputType.text
            name = "newEmail"
            id = "newEmail"
        }
        br

        label {
            htmlFor = "newDirectPhone"
            +"Phone Number:"
        }
        input {
            type = InputType.text
            name = "newDirectPhone"
            id = "newDirectPhone"
        }
        br

        p {
            +"Not yet added!"
        }
    }
}
