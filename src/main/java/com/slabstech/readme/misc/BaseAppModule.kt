package com.slabstech.misc

import com.codahale.metrics.MetricRegistry
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import io.dropwizard.setup.Environment

import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class BaseAppModule : AbstractModule() {
    override fun configure() {
        bind(StateEventMapper::class.java).to(NoopMapper::class.java)
       
    }

    @Provides
    fun logger(): Logger {
        return LOGGER
    }



    companion object {
        private val LOGGER = LoggerFactory.getLogger(App::class.java)
    }
}