package com.bike

import com.bike.infrastructure.database.initDatabase
import com.bike.plugins.configureHTTP
import com.bike.plugins.configureMonitoring
import com.bike.plugins.configureRouting
import com.bike.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()  // <- This must be here
    configureMonitoring()
    configureHTTP()
    configureRouting()
    initDatabase()
}

