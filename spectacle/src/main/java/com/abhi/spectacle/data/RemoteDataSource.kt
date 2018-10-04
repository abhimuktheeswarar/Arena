package com.abhi.spectacle.data

import android.content.Context
import com.abhi.spectacle.BuildConfig
import com.abhi.spectacle.SpectacleApplication
import com.abhi.spectacle.data.poko.ImgurEntities
import com.facebook.sonar.plugins.network.SonarOkhttpInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
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

    private val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)


    private val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://api.imgur.com/3/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            //.addConverterFactory(stringBased(contentType, json::parse, json::stringify))
            .addConverterFactory(MoshiConverterFactory.create())

    fun getImgurService(context: Context? = null): ImgurApi {

        context?.let {
            okHttpClientBuilder.addInterceptor(ChuckInterceptor(context))
            okHttpClientBuilder.addNetworkInterceptor(SonarOkhttpInterceptor((context.applicationContext as? SpectacleApplication)?.networkSonarPlugin))
        }

        retrofitBuilder.client(okHttpClientBuilder.build())

        return retrofitBuilder.build().create(ImgurApi::class.java)

    }
}

interface ImgurApi {

    @Headers("Authorization: Client-ID ${BuildConfig.IMGUR_CLIENT_ID}")
    @GET("gallery/hot/viral/all/0")
    fun getImages(): Deferred<ImgurEntities>

}