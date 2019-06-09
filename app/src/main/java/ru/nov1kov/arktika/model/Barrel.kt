package ru.nov1kov.arktika.model

data class Barrel(
    val id: String,
    val type: Int = 0,
    var done: Boolean = false
)