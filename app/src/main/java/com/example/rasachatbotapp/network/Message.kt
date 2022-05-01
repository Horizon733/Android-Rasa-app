package com.example.rasachatbotapp.network

import java.util.*


data class Message(
    var text: String?=null,
    var recipient_id: String,
    var time: Date = Calendar.getInstance().time,
    var image: String? = "",
    var isOut: Boolean = false,
    var buttons: List<RasaButton>? = null,
    var carousels: List<Carousel>?=null
)

data class RasaButton(
    val title: String,
    val payload: String
)

data class Carousel(
    val image: String,
    val title: String,
    val subtitle: String,
    val button: RasaButton?=null,
    val link: UrlButton?=null
)

data class UrlButton(
    val title: String,
    val link: String
)