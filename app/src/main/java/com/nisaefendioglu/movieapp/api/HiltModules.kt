package com.nisaefendioglu.movieapp.api

import com.nisaefendioglu.movieapp.api.helper.Constants
import com.nisaefendioglu.movieapp.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object HiltModules {

    private val client: OkHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val url =
            chain.request().url().newBuilder().addQueryParameter("apikey", Constants.API_KEY)
                .build()
        chain.proceed(chain.request().newBuilder().url(url).build())
    }.build()

    @Provides
    fun provideRetrofitInterface(): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(client)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideRepository(movieInterface: ApiService): MovieRepository {
        return MovieRepository(movieInterface)
    }
}
