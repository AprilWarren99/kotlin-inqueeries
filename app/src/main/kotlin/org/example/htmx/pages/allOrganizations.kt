package org.example.htmx.pages

import kotlinx.html.FlowContent
import kotlinx.html.*
import org.example.htmx.insertHead
import org.example.htmx.insertHeader


fun HTML.allOrganizationsPage(orgs: List<Map<String, String?>>, path: String){
    insertHead()
    body{
        insertHeader(path, "Organizations")
        insertBody(orgs)
    }
}
private fun FlowContent.insertBody(orgs: List<Map<String, String?>>){
    table {
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
        orgs.forEach { org ->
            tr {
                td{
                    +"${org["id"]}"
                }
                td {
                    a("/update/${org["id"]}") {
                        +"${org["name"]}"
                    }
                }
                td{
                    +"${org["description"]}"
                }
                td{
                    +"${org["email"]}"}
                td{
                    +"${org["streetAddress"]}"
                }
                td{
                    +"${org["city"]}"
                }
                td{
                    +"${org["province"]}"
                }
                td{
                    +"${org["phoneNumber"]}"
                }
                td{
                    +"${org["socialMedia"]}"
                }
                td{
                    a("${org["socialMedia"]}"){
                        +"website"
                    }
                }
                td{
                    +"${org["queerOwned"]}"
                }
                td{
                    +"${org["queerInclusive"]}"
                }
                td{
                    +"${org["other"]}"
                }
                td{
                    +"${org["lastUpdate"]}"
                }
            }
        }
    }
}