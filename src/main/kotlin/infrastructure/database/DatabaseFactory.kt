package com.bike.infrastructure.database

import com.bike.infrastructure.database.tables.BikesTable
import com.bike.infrastructure.database.tables.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.initDatabase() {
    val config = environment.config.config("database")
    val url = config.property("url").getString()
    val driver = config.property("driver").getString()
    val user = config.property("user").getString()
    val password = config.property("password").getString()

    val hikariConfig = HikariConfig().apply {
        jdbcUrl = url
        driverClassName = driver
        username = user
        this.password = password
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    }

    val dataSource = HikariDataSource(hikariConfig)
    Database.connect(dataSource)
    transaction {
        SchemaUtils.create(UserTable,BikesTable)
    }
}
