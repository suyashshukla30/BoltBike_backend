package com.bike.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureHTTP() {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        // @TODO: Don't do this in production if possible. Try to limit it.
        anyHost() // DANGER: This allows requests from *any* origin (domain).
        // In production, you should replace this with allowHost()
        // and specify only the exact domains of your frontend applications.
        // Example: allowHost("www.myfrontend.com")
    }
}
