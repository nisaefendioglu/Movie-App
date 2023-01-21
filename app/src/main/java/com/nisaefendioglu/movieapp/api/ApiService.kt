package com.nisaefendioglu.movieapp.api

import com.nisaefendioglu.movieapp.model.MovieResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("?")
    fun getData(
        @Query("s") search: String, @Query("page") page: Int, @Query("type") type: String
    ): Call<MovieResponseModel>
}
