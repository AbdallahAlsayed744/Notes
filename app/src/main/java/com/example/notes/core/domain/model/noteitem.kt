package com.example.notes.core.domain.model

data class noteitem(
    var title: String,
    var description: String,
    var imageUrl: String,

    val dateAdded: Long,

    val id: Int = 0,
)
