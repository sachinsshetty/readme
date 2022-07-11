package com.slabstech.readme

import com.slabstech.readme.AppConfiguration
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment

class App : Application<AppConfiguration?>() {
    override fun getName(): String {
        return "App"
    }

    override fun initialize(bootstrap: Bootstrap<AppConfiguration?>) {
        // TODO: application initialization
    }

    override fun run(
        configuration: AppConfiguration?,
        environment: Environment
    ) {
        // TODO: implement application
    }

    companion object {
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            App().run(*args)
        }
    }
}