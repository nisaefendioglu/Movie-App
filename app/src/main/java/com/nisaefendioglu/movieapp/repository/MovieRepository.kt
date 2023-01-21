package com.nisaefendioglu.movieapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nisaefendioglu.movieapp.model.MovieResponseModel
import com.nisaefendioglu.movieapp.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository(private val omdbService : ApiService){

    private var _movieLiveData = MutableLiveData<MovieResponseModel?>()
    val movieLiveData : LiveData<MovieResponseModel?>
    get() = _movieLiveData

    private var _movieErrorLiveData = MutableLiveData<Boolean>()
    val movieErrorLiveData : LiveData<Boolean>
    get() = _movieErrorLiveData

    fun getData(search : String, page : Int, type : String){
       val result = omdbService.getData(search,page,type)
       result.enqueue(object : Callback<MovieResponseModel>{
           override fun onResponse(
               call: Call<MovieResponseModel>,
               response: Response<MovieResponseModel>
           ) {
               val responseBody = response.body()
               _movieLiveData.postValue(responseBody)
               _movieErrorLiveData.postValue(responseBody?.response ?: true)
           }

           override fun onFailure(call: Call<MovieResponseModel>, t: Throwable) {
               _movieErrorLiveData.postValue(true)
           }
       })
    }

    fun getDataDetail(search : String, page : Int, type : String){
        val result = omdbService.getData(search,page,type)
        result.enqueue(object : Callback<MovieResponseModel>{
            override fun onResponse(
                call: Call<MovieResponseModel>,
                response: Response<MovieResponseModel>
            ) {
                val responseBody = response.body()
                _movieLiveData.postValue(responseBody)
                _movieErrorLiveData.postValue(responseBody?.response ?: true)
            }

            override fun onFailure(call: Call<MovieResponseModel>, t: Throwable) {
                _movieErrorLiveData.postValue(true)
            }
        })
    }

}
