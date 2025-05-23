package com.bike

import com.bike.infrastructure.Firebase.initFirerBaseAdmin
import com.bike.infrastructure.database.initDatabase
import com.bike.plugins.configureHTTP
import com.bike.plugins.configureMonitoring
import com.bike.plugins.configureRouting
import com.bike.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.EngineMain
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    io.github.cdimascio.dotenv.dotenv {
        systemProperties = true
    }
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    embeddedServer(Netty, port = port, host = "0.0.0.0") {
        module()
    }.start(wait = true)
    EngineMain.main(args)
}

fun Application.module() {
    val portStr = environment.config.propertyOrNull("ktor.deployment.port")?.getString()
    val port = portStr?.toIntOrNull() ?: 8080
    log.info("Starting on port $port")

    val dotenv = io.github.cdimascio.dotenv.dotenv()
    val dbUrl = dotenv["DB_URL"] ?: error("Missing DB_URL")
    val dbUser = dotenv["DB_USER"] ?: error("Missing DB_USER")
    val dbPassword = dotenv["DB_PASSWORD"] ?: error("Missing DB_PASSWORD")

    initDatabase(
        url = dbUrl,
        user = dbUser,
        password = dbPassword
    )

    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureRouting()
    initFirerBaseAdmin()
}


