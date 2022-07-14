package com.slabstech.readme

import io.dropwizard.Application
import io.dropwizard.testing.ConfigOverride
import io.dropwizard.testing.ResourceHelpers
import io.dropwizard.testing.junit5.DropwizardAppExtension
import io.vavr.control.Try
import liquibase.Contexts
import liquibase.Liquibase
import liquibase.database.jvm.JdbcConnection
import liquibase.exception.LiquibaseException
import liquibase.resource.ClassLoaderResourceAccessor
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.PostgreSQLContainer
import ru.vyarus.guicey.jdbi3.unit.UnitManager
import java.sql.Connection
import javax.ws.rs.client.Invocation

class DropwizardTestExtension : BeforeAllCallback, BeforeEachCallback {
    @Throws(Exception::class)
    override fun beforeAll(extensionContext: ExtensionContext) {
        app!!.before()
    }

    @Throws(Exception::class)
    override fun beforeEach(extensionContext: ExtensionContext) {
        liquibaseMigrationToTag(null)
    }

    private fun liquibaseMigrationToTag(tag: String?) {
        val unitManager = getService(UnitManager::class.java)
        if (unitManager != null) {
            unitManager.beginUnit()
        }
        Try.run { (unitManager?.get()?.connection ?: null)?.let { runMigrationToTag(tag, it) } }
                .andFinally { unitManager?.endUnit() }
                .get()
    }

    @Throws(LiquibaseException::class)
    private fun runMigrationToTag(tag: String?, connection: Connection) {
        val migrator = Liquibase(
                "postgres/migrations.xml",
                ClassLoaderResourceAccessor(),
                JdbcConnection(connection)
        )
        migrator.dropAll()
        migrator.update(tag, Contexts())
    }

    fun createRequestToAdminPort(path: String?): Invocation.Builder {
        return createRequest(path, java.util.Map.of(), java.util.Map.of(), true)
    }

    @JvmOverloads
    fun createRequest(
            path: String?,
            queryParams: Map<String?, String?> = java.util.Map.of(),
            header: Map<String?, String?>? = java.util.Map.of(),
            adminPort: Boolean = false): Invocation.Builder {
        val port = if (adminPort) app!!.adminPort else app!!.localPort
        var target = app.client()
                .target("http://localhost:$port")
                .path(path)
        for ((key, value) in queryParams) {
            target = target.queryParam(key, value)
        }
        return target.request()
    }

    fun <T> getService(clazz: Class<T>?): T? { return null
      //  return (app!!.getApplication<Application<AppConfiguration>>() as App).guiceBundle.injector.getInstance(clazz)
    }

    companion object {
        protected val configPath = ResourceHelpers.resourceFilePath("test-config.yml")
        private val container: PostgreSQLContainer<*>? = null
        private val app: DropwizardAppExtension<AppConfiguration>? = null
/*
        init {
            container = PostgreSQLContainer<Any?>("postgres:11.5")
                    .withTmpFs(java.util.Map.of("/var/lib/postgresql/data", "rw"))
            container!!.start()
            app = DropwizardAppExtension(
                    TestApp::class.java,
                    configPath,
                    ConfigOverride.config("server.applicationConnectors[0].port", "0"),  // use a randomly selected port
                    ConfigOverride.config("server.adminConnectors[0].port", "0"),  // use a randomly selected port
                    ConfigOverride.config("database.url", container.jdbcUrl),
                    ConfigOverride.config("database.user", container.username),
                    ConfigOverride.config("database.password", container.password),
                    ConfigOverride.config("logging.loggers.liquibase", "ERROR")
            )
        }

 */
    }
}