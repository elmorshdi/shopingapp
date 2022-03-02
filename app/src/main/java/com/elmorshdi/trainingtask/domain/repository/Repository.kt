package com.elmorshdi.trainingtask.domain.repository

import androidx.lifecycle.LiveData
import com.elmorshdi.trainingtask.datasource.model.ProductResponse
import com.elmorshdi.trainingtask.datasource.model.UserResponse
import com.elmorshdi.trainingtask.datasource.network.ApiService
import com.elmorshdi.trainingtask.domain.model.Product
import retrofit2.Response
import javax.inject.Inject


class Repository @Inject constructor(private val apiService: ApiService) {

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