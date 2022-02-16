package com.elmorshdi.trainingtask.network

import com.elmorshdi.trainingtask.model.Product
import com.elmorshdi.trainingtask.model.ProductResponse
import retrofit2.Response
import retrofit2.http.*

val apiService: ApiService by lazy {
    RetrofitInstate.retrofit.create(ApiService::class.java)
}

interface ApiService {

    @GET("products")
    suspend fun getProducts():Response<ProductResponse>


    @POST("products")
    suspend fun addProducts(@Body product: Product):Response<ProductResponse>

}