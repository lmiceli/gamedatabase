package com.lmiceli.gamedatabase.data.remote.https

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitFactory {
    fun buildRetrofit(
        context: Context,
        mosh: Moshi,
    ): Retrofit {

        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(AuthorizationInterceptor())
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.igdb.com/v4/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(mosh))
            .build()
    }

}
