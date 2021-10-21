package com.example.rasachatbotapp.network

sealed class ConnectionState{
    object Available: ConnectionState()
    object Unavailable: ConnectionState()
}
