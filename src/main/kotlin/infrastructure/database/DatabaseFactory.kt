package com.bike.infrastructure.database

import com.bike.infrastructure.database.tables.BikesTable
import com.bike.infrastructure.database.tables.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.initDatabase(
    url: String? = null,
    driver: String? = null,
    user: String? = null,
    password: String? = null
) {
    val dbUrl = url ?: environment.config.propertyOrNull("database.url")?.getString()
    ?: throw IllegalArgumentException("Database url is missing")
    val dbDriver = driver ?: environment.config.propertyOrNull("database.driver")?.getString()
    ?: "org.postgresql.Driver"  // default driver if you want
    val dbUser = user ?: environment.config.propertyOrNull("database.user")?.getString()
    ?: throw IllegalArgumentException("Database user is missing")
    val dbPassword = password ?: environment.config.propertyOrNull("database.password")?.getString()
    ?: throw IllegalArgumentException("Database password is missing")

    val hikariConfig = HikariConfig().apply {
        jdbcUrl = dbUrl
        driverClassName = dbDriver
        username = dbUser
        this.password = dbPassword
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    }

    val dataSource = HikariDataSource(hikariConfig)
    Database.connect(dataSource)

    transaction {
        SchemaUtils.create(UserTable, BikesTable)
    }
}

