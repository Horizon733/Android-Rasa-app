package com.example.rasachatbotapp

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rasachatbotapp.network.*
import kotlinx.coroutines.launch
import java.util.*

class MainActivityViewModel : ViewModel() {
    val message = listOf(
        Message(
            recipient_id = "01",
            text = "Hi"
        ),
        Message(
            recipient_id = "02",
            text = "Carousels",
            carousels = listOf(
                Carousel(
                    image = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
                    title = "Product 1",
                    subtitle = "descriptions",
                    button = RasaButton(
                        title = "Hi",
                        payload = "Hi"
                    )
                ),
                Carousel(
                    image = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
                    title = "Product 1",
                    subtitle = "descriptions",
                    button = RasaButton(
                        title = "Hi",
                        payload = "Hi"
                    )
                ),
                Carousel(
                    image = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
                    title = "Product 1",
                    subtitle = "descriptions",
                    link = UrlButton(
                        title = "Click here",
                        link = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                    )
                ),
                Carousel(
                    image = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
                    title = "Product 1",
                    subtitle = "descriptions",
                    link = UrlButton(
                        title = "Click here",
                        link = "https://github.com/facebook/stetho"
                    )
                ),
                Carousel(
                    image = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
                    title = "Product 1",
                    subtitle = "descriptions",
                    link = UrlButton(
                        title = "Click here",
                        link = "https://github.com/facebook/stetho"
                    )
                ),
                Carousel(
                    image = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
                    title = "Product 1",
                    subtitle = "descriptions",
                    link = UrlButton(
                        title = "Click here",
                        link = "http://facebook.github.io/stetho/"
                    )
                )
            )

        )
    )
    private val message_list: MutableList<Message> = mutableStateListOf()
    val messages: List<Message> = message_list

    private val connectivityState = mutableStateOf(true)
    val _connectivityState = connectivityState

    val username = "Dishant"

    init {
        message_list.addAll(message)
    }

    fun addMessage(message: Message) {
        message_list.add(0, message)
    }

    fun sendMessagetoRasa(message: Message) {
        addMessage(message)
        viewModelScope.launch {
            val response = rasaApiService.sendMessage(message)
            Log.e("Message", response.toString())
            if (response.code() == 200 && response.body() != null) {
                response.body()!!.forEach {
                    it.time = Calendar.getInstance().time
                    addMessage(it)
                }
            } else {
                addMessage(
                    Message(
                        "${response.code()} error occured",
                        "bot",
                        Calendar.getInstance().time
                    )
                )
            }
        }
    }
}