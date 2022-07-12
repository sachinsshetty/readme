package com.slabstech.readme

class TestApp : App() {
    override fun createAppModule(): BaseAppModule {
        return TestAppModule()
    }
}