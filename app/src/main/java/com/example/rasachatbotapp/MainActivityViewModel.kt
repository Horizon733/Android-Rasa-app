package com.example.rasachatbotapp

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rasachatbotapp.network.Message
import com.example.rasachatbotapp.network.rasaApiService
import kotlinx.coroutines.launch
import java.util.*

class MainActivityViewModel: ViewModel() {

    private val message_list: MutableList<Message> = mutableStateListOf(*chats.toTypedArray())
    val messages: List<Message> = message_list

    private val connectivityState = mutableStateOf(true)
    val _connectivityState = connectivityState

    val username = "Dishant"

    fun addMessage(message: Message){
        message_list.add(0, message)
    }

    fun sendMessagetoRasa(message: Message){
        addMessage(message)
        viewModelScope.launch {
            val response = rasaApiService.sendMessage(message)
            Log.e("Message", response.toString())
            if(response.code() == 200 && response.body() != null) {
                response.body()!!.forEach{
                    Log.e("bot messgae", it.text)
                    addMessage(it)
                }
            }else{
                addMessage(Message("bot", "${response.code()} error occured", Calendar.getInstance().time.toString()))
            }
        }
    }
}