package com.example.mykonter.network

import com.example.mykonter.model.Phone
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/" +
        "alwilukman/assesment-03/static-api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PhoneApiService {
    @GET("phone.json")
    suspend fun getPhone(): List<Phone>
}

object PhoneApi {
    val service: PhoneApiService by lazy {
        retrofit.create(PhoneApiService::class.java)
    }
    fun getPhoneUrl(nama: String): String {
        return "$BASE_URL$nama.jpg"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }