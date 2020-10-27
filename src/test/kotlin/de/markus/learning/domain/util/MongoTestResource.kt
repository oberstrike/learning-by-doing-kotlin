package de.markus.learning.domain.util

import de.flapdoodle.embed.mongo.Command
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.*
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.config.IRuntimeConfig
import de.flapdoodle.embed.process.config.io.ProcessOutput
import de.flapdoodle.embed.process.runtime.Network
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.jboss.logging.Logger
import java.io.IOException


class MongoTestResource : QuarkusTestResourceLifecycleManager {

    private var MONGO: MongodExecutable? = null

    private val LOGGER: Logger = Logger.getLogger(MongoTestResource::class.java)

    override fun start(): Map<String?, String?>? {
        try {
            val version: Version.Main = Version.Main.V4_0
            val port = 27018
            LOGGER.infof("Starting Mongo %s on port %s", version, port)
            val config = MongodConfigBuilder()
                    .version(version)
                    .cmdOptions(MongoCmdOptionsBuilder().useNoJournal(false).build())
                    .net(Net(port, Network.localhostIsIPv6()))
                    .build()
            MONGO = getMongodExecutable(config)
            try {
                MONGO!!.start()
            } catch (e: Exception) {
                //every so often mongo fails to start on CI runs
                //see if this helps
                Thread.sleep(1000)
                MONGO!!.start()
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }

        return mapOf("quarkus.mongodb.connection-string" to "mongodb://localhost:27018")
    }

    private fun getMongodExecutable(config: IMongodConfig): MongodExecutable? {
        return try {
            doGetExecutable(config)
        } catch (e: Exception) {
            // sometimes the download process can timeout so just sleep and try again
            try {
                Thread.sleep(1000)
            } catch (ignored: InterruptedException) {
            }
            doGetExecutable(config)
        }
    }

    private fun doGetExecutable(config: IMongodConfig): MongodExecutable? {
        val runtimeConfig: IRuntimeConfig = RuntimeConfigBuilder()
                .defaults(Command.MongoD)
                .processOutput(ProcessOutput.getDefaultInstanceSilent())
                .build()
        return MongodStarter.getInstance(runtimeConfig).prepare(config)
    }

    override fun stop() {
        if (MONGO != null) {
            MONGO!!.stop()
        }
    }

}