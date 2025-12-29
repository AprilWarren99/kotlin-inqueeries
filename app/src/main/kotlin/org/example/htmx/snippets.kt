package org.example.htmx
import kotlinx.html.*

fun HTML.insertHead(imports: HEAD.() -> Unit = {}) {
    head {
        script {
            src = "https://unpkg.com/htmx.org@2.0.4"
        }
        link(
            rel = "stylesheet",
            href = "/static/css/styles.css",
            type = "text/css"
        )
        imports()  // call the lambda here
    }
}

 fun FlowContent.insertHeader(path: String, heading: String){
     a(href="/"){
         img(src="/static/images/QRLogo2_nobg.svg"){
             style = "float: inline-start;"
             width = "76px"
         }
     }
    h1{
        style="text-align: center;"
        +heading
    }
}