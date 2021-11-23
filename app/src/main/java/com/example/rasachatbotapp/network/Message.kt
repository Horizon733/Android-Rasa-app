package com.example.rasachatbotapp.network

import java.util.*


data class Message(
    var text: String?=null,
    var recipient_id: String,
    var time: Date = Calendar.getInstance().time,
    var isOut: Boolean = false,
)
