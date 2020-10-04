package com.example.pikabu.entity

class Feed(
    val type: String,
    val stories: List<Story>
) {
    companion object {
        const val ALL = "ALL"
        const val SAVED = "SAVED"
    }
}