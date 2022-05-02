package com.example.rasachatbotapp.network

import androidx.compose.runtime.snapshots.SnapshotStateList
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


private val retrofit = Retrofit.Builder()
    .baseUrl("https://rasa-horizon733.cloud.okteto.net")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val rasaApiService = retrofit.create(RasaApiService::class.java)

interface RasaApiService {
    @POST("webhooks/rest/webhook")
    suspend fun sendMessage(
        @Body message: Message
    ): Response<SnapshotStateList<Message>>
}