package com.nisaefendioglu.movieapp.model

import com.google.gson.annotations.SerializedName

data class MovieResponseModel(
    @SerializedName("Search") var search: List<DataModel>?,
    @SerializedName("totalResults") var totalResults: String?,
    @SerializedName("Response") var response: Boolean?
)
