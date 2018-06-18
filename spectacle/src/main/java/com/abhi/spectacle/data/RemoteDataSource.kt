package com.abhi.spectacle.data

import com.abhi.spectacle.BuildConfig
import com.abhi.spectacle.data.poko.ImgurEntities
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.Deferred
import kotlinx.serialization.json.JSON
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

object ImgurService {

    private val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    private val contentType = MediaType.parse("application/json")!!
    private val json = JSON

    private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgur.com/3/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            //.addConverterFactory(stringBased(contentType, json::parse, json::stringify))
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build())
            .build()

    fun getImgurService(): ImgurApi = retrofit.create(ImgurApi::class.java)
}

interface ImgurApi {

    @Headers("Authorization: Client-ID ${BuildConfig.IMGUR_CLIENT_ID}")
    @GET("gallery/hot/viral/all/0")
    fun getImages(): Deferred<ImgurEntities>

}