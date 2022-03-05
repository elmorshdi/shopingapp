package com.elmorshdi.trainingtask.datasource.network

import com.elmorshdi.trainingtask.datasource.model.ProductResponse
import com.elmorshdi.trainingtask.datasource.model.UserResponse
import com.elmorshdi.trainingtask.domain.model.Product
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @GET("products")
    suspend fun getProducts(): Response<ProductResponse>

    @POST("products")
    suspend fun addProducts(@Body product: Product): Response<ProductResponse>

    @POST("login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<UserResponse>

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int?): Response<ProductResponse>

}