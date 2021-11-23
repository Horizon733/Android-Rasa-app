package com.example.rasachatbotapp.network

import java.util.*


data class Message(
    var text: String?=null,
    var recipient_id: String,
    var time: Date = Calendar.getInstance().time,
    var image: String? = "",
    var isOut: Boolean = false,
    var buttons: List<RasaButton>? = null
)

data class RasaButton(
    val title: String,
    val payload: String
)