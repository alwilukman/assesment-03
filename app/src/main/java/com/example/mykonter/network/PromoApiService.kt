package com.example.mykonter.network

import com.example.mykonter.model.Promo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com" +
        "alwilukman/assesment03/static-api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PromoApiService {
    @GET("promo.json")
    suspend fun getPromo(): List<Promo>
}
object PromoApi {
    val service: PromoApiService by lazy {
        retrofit.create(PromoApiService::class.java)
    }

    fun getPromoUrl(nama: String): String {
        return "$BASE_URL$nama.png"
    }
}
enum class ApiStatus2 { LOADING, SUCCESS, FAILED }