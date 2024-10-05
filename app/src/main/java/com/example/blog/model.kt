package com.example.blog


data class BlogPost(
 val id: Int,
    val title: Title,
    val content: Content
)

data class Title(val rendered: String)
data class Content(val rendered: String)