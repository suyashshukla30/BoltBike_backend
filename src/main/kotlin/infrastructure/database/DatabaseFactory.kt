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
    val config = environment.config.config("database")

    val dbUrl = url ?: config.property("url").getString()
    val dbDriver = driver ?: config.property("driver").getString()
    val dbUser = user ?: config.property("user").getString()
    val dbPassword = password ?: config.property("password").getString()

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
