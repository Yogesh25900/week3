package com.example.week3.model

data class PostModel(
    var profileImage: String? = null,
    var username: String? = null,
    var postImage: String? = null,
    var caption: String? = null,
    var timestamp: String? = null
) {
    // No-argument constructor
    constructor() : this(null, null, null, null, null)
}
