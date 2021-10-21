package com.example.rasachatbotapp.network


data class Message(
    var text: String,
    var recipient_id: String,
    var time: String = "",
    var image: String = ""

)