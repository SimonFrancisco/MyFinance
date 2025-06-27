package francisco.simon.myfinance.data.api

import francisco.simon.myfinance.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/**
 * Creates api service with needed settings
 * API KEY is taken from local.properties, replace BuildConfig.API_KEY with your token
 * @author Simon Francisco
 */
object ApiFactory {
    private const val TOKEN_FORMAT = "Bearer ${BuildConfig.API_KEY}"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", TOKEN_FORMAT)
            chain.proceed(request.build())
        }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiService: ApiService = retrofit.create()
}