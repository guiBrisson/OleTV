package br.tv.ole

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform