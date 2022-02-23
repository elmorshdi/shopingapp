package com.elmorshdi.trainingtask.repository

import com.elmorshdi.trainingtask.model.Product
import com.elmorshdi.trainingtask.model.ProductResponse
import com.elmorshdi.trainingtask.model.UserResponse
import com.elmorshdi.trainingtask.network.apiService
import retrofit2.Response

class Repository {
    suspend fun getProducts(): Response<ProductResponse> {
        return apiService.getProducts()
    }
    suspend fun addProducts(product: Product): Response<ProductResponse> {
        return apiService.addProducts(product)
    }
    suspend fun deleteProduct(id:Int): Response<ProductResponse> {
        return apiService.deleteProduct(id)
    }
    suspend fun login(email :String,password :String): Response<UserResponse> {
        return apiService.login(email, password)
    }
}