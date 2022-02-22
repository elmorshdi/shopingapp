package com.elmorshdi.trainingtask.network

import com.elmorshdi.trainingtask.model.Product
import com.elmorshdi.trainingtask.model.ProductResponse
import com.elmorshdi.trainingtask.model.User
import com.elmorshdi.trainingtask.model.UserResponse
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
     @POST("login")
    suspend fun login(@Query("email")  email :String,
                      @Query( "password")password :String
     ):Response<UserResponse>
    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int?):Response<ProductResponse>

}