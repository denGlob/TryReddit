package com.ua.tryreddit.data.engines

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ua.tryreddit.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetEngine private constructor() {

    val retrofit: Retrofit by lazy { provideRetrofit() }
    val gson: Gson by lazy { provideGson() }
    val okHttpClient: OkHttpClient by lazy { provideOkHttpClient() }

    private object Holder {
        internal val HTTP_MODULE_INSTANCE = NetEngine()
    }

    companion object {

        val instance: NetEngine
            get() = Holder.HTTP_MODULE_INSTANCE
    }

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .build()
    }

    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)

        val bodyInterceptor = HttpLoggingInterceptor()
        bodyInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(bodyInterceptor)

        return builder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder()
            .create()

    }

    fun provideConnectionEstablished(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
            capabilities?.let {
                return it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && it.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            }
        } else {
            return cm.activeNetworkInfo?.isConnected == true
        }
        return false
    }

    fun provideConnectivityManager(context: Context) = context.getSystemService(Context.CONNECTIVITY_SERVICE)

    fun provideDefaultNetworkRequest() = NetworkRequest.Builder()
}