package com.example.pikabu.entity

class Message(val type: String) {
    companion object {
        const val CONNECTION_ERROR = "CONNECTION_ERROR"
        const val NO_DATA_ERROR = "NO_DATA_ERROR"
        const val UNKNOWN_ERROR = "UNKNOWN_ERROR"
    }
}