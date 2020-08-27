package id.udata.analytics.utils

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import id.udata.analytics.api.AnalyticsApiClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.MINUTES

/**
 * @return HTTP Interceptor
 */
fun provideCacheInterceptor() = run {
    Interceptor { chain ->
        val response = chain.proceed(chain.request())
        response.newBuilder()
            .header("Cache-Control", "public, max-age=60")
            .removeHeader("Pragma")
            .build()
    }
}

/**
 * @return HTTP Logging Interceptor
 */
fun provideHttpLoggingInterceptor() = run {
    HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

/**
 * @return OkHttpClient
 */
fun provideOkHttpClient() = run {
    val okHttpClientBuilder = OkHttpClient.Builder().apply {
        followRedirects(false)
        followSslRedirects(false)
        addInterceptor(provideCacheInterceptor())
        addInterceptor(provideHttpLoggingInterceptor())
        addInterceptor { chain ->
            val request = chain.request()
            val requestBuilder = request.newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(requestBuilder)
        }
        connectTimeout(1, MINUTES)
        readTimeout(1, MINUTES)
        writeTimeout(1, MINUTES)
    }
    okHttpClientBuilder.build()
}

/**
 * @param baseUrl Base Url for API
 * @param okHttpClient default value from global method
 * @return AnalyticsApiClient
 */
fun provideClient(
    baseUrl: String,
    okHttpClient: OkHttpClient = provideOkHttpClient()
): AnalyticsApiClient {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(AnalyticsApiClient::class.java)
}