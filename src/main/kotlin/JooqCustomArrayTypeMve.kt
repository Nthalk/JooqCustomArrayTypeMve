import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import org.jooq.codegen.GenerationTool
import org.jooq.impl.DSL
import org.jooq.meta.jaxb.*
import org.jooq.meta.jaxb.Target
import org.jooq.meta.postgres.PostgresDatabase
import org.slf4j.LoggerFactory

class JooqCustomArrayTypeMve : CliktCommand(name = "gen") {
    companion object {
        val log = LoggerFactory.getLogger(JooqCustomArrayTypeMve::class.java)
    }

    val jdbcUrl by option("--jdbc-url", help = "The JDBC URL").default("FROM_DOCKER")
    val user by option("--user", help = "The user of the database").default("postgres")
    val password by option("--password", help = "The password of the database").default("password")
    val schema by option("--schema", help = "The schema of the database").default("public")

    val noInterfaceWorkaround by option("--no-interface-workaround", help = "Disable interface to workaround bug").flag()


    override fun help(context: Context): String {
        return "Generate database models"
    }

    override fun run() {
        System.setProperty("org.jooq.no-logo", "true")
        val jdbcUrlFixed = maybeSetupDocker()
        createInitialModels(jdbcUrlFixed)
        jooqCodeGen(jdbcUrlFixed)
        maybeTearDownDocker()
        compileGenProject()
    }

    private fun compileGenProject() {
        log.info("Compiling generated project...")
        val code = ProcessBuilder("./gradlew", "gen:compileJava")
            .inheritIO()
            .start()
            .waitFor()
        if (code != 0) {
            throw RuntimeException("Failed to compile generated project, exit code: $code, check output for more information!")
        }
    }

    private fun jooqCodeGen(jdbcUrlFixed: String) {
        log.info("Generating database models...")
        GenerationTool.generate(
            Configuration().withJdbc(
                Jdbc().withUrl(jdbcUrlFixed)
                    .withUsername(user)
                    .withPassword(password)
            )
                .withGenerator(
                    Generator()
                        .withGenerate(
                            Generate()
                                .withDefaultSchema(false)
                                .withDefaultCatalog(false)
                                .withInterfaces(if (noInterfaceWorkaround) false else true)
                        )
                        .withDatabase(
                            Database()
                                .withName(PostgresDatabase::class.java.name)
                                .withOutputSchemaToDefault(true)
                                .withIncludes(
                                    """
                                    test|tag
                                """.trimIndent()
                                )
                                .withInputSchema(schema)
                        )
                        .withTarget(
                            Target()
                                .withPackageName("example")
                                .withDirectory("gen/src/main/java")
                                .withClean(true)
                        )
                )
                .withLogging(Logging.INFO)
        )
    }

    private fun maybeSetupDocker(): String {
        return if (jdbcUrl == "FROM_DOCKER") {
            ProcessBuilder("docker", "remove", "--force", "JooqCustomArrayTypeMve").start().waitFor()
            ProcessBuilder(
                "docker",
                "run",
                "--name", "JooqCustomArrayTypeMve",
                "-e", "POSTGRES_PASSWORD=${password}",
                "-e", "POSTGRES_USER=${user}",
                "-p", "5432",
                "-d",
                "postgres:alpine"
            ).start().waitFor()

            // Fix postgresql to allow tcp connections from all hosts
            ProcessBuilder(
                "docker",
                "exec",
                "-i",
                "JooqCustomArrayTypeMve",
                "bash", "-c",
                """
                # Wait for postgres to start and create initial directories prior to modifying configuration
                while ! pg_isready; do sleep 1; done
                echo "listen_addresses='*'" >> /var/lib/postgresql/data/postgresql.conf 
                echo "host all all 0.0.0.0/0 md5" >> /var/lib/postgresql/data/pg_hba.conf
                # It appears that the postgres service will restart when the configuration files are modified?
                """.trimIndent()
            ).inheritIO().start().waitFor()

            val port = (0..20).firstNotNullOf {
                try {
                    ProcessBuilder("docker", "port", "JooqCustomArrayTypeMve").start().inputStream.bufferedReader()
                        .use {
                            val port = it.readLine().split(":")[1].toInt()
                            port
                        }
                } catch (e: Exception) {
                    Thread.sleep(1000)
                    null
                }
            }
            "jdbc:postgresql://127.0.0.1:$port/postgres"
        } else {
            jdbcUrl
        }
    }

    private fun maybeTearDownDocker() {
        if (jdbcUrl == "FROM_DOCKER") {
            log.info("Stopping docker container...")
            ProcessBuilder("docker", "remove", "--force", "JooqCustomArrayTypeMve").start()
        }

    }

    private fun createInitialModels(jdbcUrlFixed: String) {
        log.info("Creating initial models at $jdbcUrlFixed...")
        // Generate initial database
        DSL.using(jdbcUrlFixed, user, password).use {
            it.execute(
                """
                CREATE TYPE tag AS
                (
                    name  text,
                    value text
                );
                CREATE TABLE test
                (
                    id   SERIAL PRIMARY KEY,
                    tags tag[]
                );
                """
            )
        }
    }
}

fun main(args: Array<String>) {
    JooqCustomArrayTypeMve().main(args)
}
