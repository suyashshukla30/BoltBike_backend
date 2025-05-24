package com.bike

import com.bike.infrastructure.Firebase.initFirerBaseAdmin
import com.bike.infrastructure.database.initDatabase
import com.bike.plugins.configureHTTP
import com.bike.plugins.configureMonitoring
import com.bike.plugins.configureRouting
import com.bike.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    io.github.cdimascio.dotenv.dotenv {
        systemProperties = true
    }
    EngineMain.main(args)
}

fun Application.module() {
    val dbUrl = System.getenv("DB_URL") ?: error("Missing DB_URL")
    val dbUser = System.getenv("DB_USER") ?: error("Missing DB_USER")
    val dbPassword = System.getenv("DB_PASSWORD") ?: error("Missing DB_PASSWORD")

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


