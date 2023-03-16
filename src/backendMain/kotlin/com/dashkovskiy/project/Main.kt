package com.dashkovskiy.project

import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.routing.*
import io.kvision.remote.kvisionInit

fun Application.main() {
    install(Compression)
    routing {}
    kvisionInit(appModule)
}

