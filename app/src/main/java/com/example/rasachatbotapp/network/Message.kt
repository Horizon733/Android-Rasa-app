package com.example.rasachatbotapp.network

import java.util.*


data class Message(
    var text: String?=null,
    var recipient_id: String,
    var time: Long = Calendar.getInstance().timeInMillis,
    var image: String? = "",
    var isOut: Boolean = false,
    var buttons: List<RasaButton>? = null
)

data class RasaButton(
    val title: String,
    val payload: String
)


val message_dummy = listOf(
    Message(
        text = "Great!",
        recipient_id = "bot",
        isOut = false
    ),
    Message(
        text = "I am good",
        recipient_id = "user",
        isOut = true
    ),
    Message(
        text = "Hi, How are you?",
        recipient_id = "bot",
        isOut = false
    ),
    Message(
        text = "Hi",
        recipient_id = "user",
        isOut = true
    )
)