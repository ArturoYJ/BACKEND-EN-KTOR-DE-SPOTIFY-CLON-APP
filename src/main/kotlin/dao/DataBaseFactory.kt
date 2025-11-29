package com.example.dao

import com.example.domain.models.Albums
import com.example.domain.models.Artists
import com.example.domain.models.Songs
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val driverClassName = config.property("storage.driverClassName").getString()
        val jdbcUrl = config.property("storage.jdbcUrl").getString()

        val dbUser = config.property("storage.dbUser").getString()
        val dbPassword = config.property("storage.dbPassword").getString()

        val database = Database.connect(
            url = jdbcUrl,
            driver = driverClassName,
            user = dbUser,
            password = dbPassword
        )

        transaction(database) {
            SchemaUtils.create(Artists, Albums, Songs)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}