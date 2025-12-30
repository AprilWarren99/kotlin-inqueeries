package org.example.htmx.pages

import io.ktor.htmx.HxSwap
import io.ktor.htmx.html.hx
import kotlinx.html.FlowContent
import kotlinx.html.*
import org.example.htmx.insertHead
import org.example.htmx.insertHeader
import org.example.model.OrganizationTable
import java.time.format.DateTimeFormatter
import kotlin.text.split


fun HTML.allOrganizationsPage(orgs: List<Map<String, String?>>, path: String){
    insertHead()
    body{
        insertHeader(path, "Organizations")
        insertBody(orgs)
    }
}
private fun FlowContent.insertBody(orgs: List<Map<String, String?>>){
    form {
        method = FormMethod.post
        action = "/all/search"

        label {
            htmlFor = "Search"
            +"Search"
        }
        input {
            attributes.hx {
                post = "/all/search"
                target = "#results"
                swap = HxSwap.outerHtml
                trigger = "keyup changed delay:500ms"
            }
            type = InputType.text
            name = "search"
            id = "search"
            placeholder = "Search..."
        }
    }
    table {
        id="resultsTable"
        thead {
            tr {
                th {
                    attributes["scope"]  = "col"
                    +"ID"
                }
                th {
                    attributes["scope"]  = "col"
                    +"Name"
                }
                th {
                    attributes["scope"]  = "col"
                    +"Description"
                }
                th {
                    attributes["scope"]  = "col"
                    +"Email"
                }
                th {
                    attributes["scope"]  = "col"
                    +"Street Address"
                }
                th {
                    attributes["scope"]  = "col"
                    +"City"
                }
                th {
                    attributes["scope"]  = "col"
                    +"Province"
                }
                th {
                    attributes["scope"]  = "col"
                    +"Phone Number"
                }
                th {
                    attributes["scope"]  = "col"
                    +"Social Media"
                }
                th {
                    attributes["scope"]  = "col"
                    +"Website"
                }
                th { attributes["scope"]  = "col"
                    +"Queer Owned"
                }
                th {
                    attributes["scope"]  = "col"
                    +"Queer Inclusive"
                }
                th {
                    attributes["scope"]  = "col"
                    +"Other"
                }
                th { attributes["scope"]  = "col"
                    +"Last Updated"
                }
            }
        }
        tbody {
            id="results"
            orgs.forEach { org ->
                tr {
                    td {
                        +"${org["id"]}"
                    }
                    td {
                        a("/update/${org["id"]}") {
                            +"${org["name"]}"
                        }
                    }
                    td {
                        val descLength = org["description"]!!.split(" ").size

                        if(descLength >= 10){
                            +(org["description"]?.split(" ")
                                ?.subList(0, minOf(10,
                                    descLength))
                                ?.joinToString(" ") + "...")
                        }else{
                            +(org["description"] ?: " ")
                        }

                    }
                    td {
                        +"${org["email"]}"
                    }
                    td {
                        +"${org["streetAddress"]}"
                    }
                    td {
                        +"${org["city"]}"
                    }
                    td {
                        +"${org["province"]}"
                    }
                    td {
                        +"${org["phoneNumber"]}"
                    }
                    td {
                        +"${org["socialMedia"]}"
                    }
                    td {
                        a("${org["website"]}") {
                            +"website"
                        }
                    }
                    td {
                        +"${org["queerOwned"]}"
                    }
                    td {
                        +"${org["queerInclusive"]}"
                    }
                    td {
                        +"${org["other"]}"
                    }
                    td {
                        +"""${org["lastUpdate"]?.format(
                            DateTimeFormatter.ofPattern("MMMM, dd, YYYY - hh:mm a")
                            )}"""
                    }
                }
            }
        }
    }
}