package com.nisaefendioglu.movieapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetailData(
    @SerializedName("Released") val Release:String,
    @SerializedName("Poster") val poster:String,
    @SerializedName("Plot") val plot:String,
    @SerializedName("imdbID") val idmovie:String
) : Parcelable
