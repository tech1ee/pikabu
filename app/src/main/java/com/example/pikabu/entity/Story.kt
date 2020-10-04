package com.example.pikabu.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Story(
    @PrimaryKey val id: Int?,
    val title: String?,
    val body: String?,
    val images: List<String>?,
    var saved: Boolean = false
)