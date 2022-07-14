package com.slabstech.readme

import com.slabstech.misc.BaseAppModule
import com.slabstech.readme.misc.TestAppModule

class TestApp : App() {
    fun createAppModule(): BaseAppModule {
        return TestAppModule()
    }
}