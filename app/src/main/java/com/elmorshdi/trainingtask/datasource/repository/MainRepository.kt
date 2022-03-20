package com.elmorshdi.trainingtask.datasource.repository

import com.elmorshdi.trainingtask.datasource.model.ProductResponse
import com.elmorshdi.trainingtask.datasource.model.UserResponse
import com.elmorshdi.trainingtask.domain.model.Product
import com.elmorshdi.trainingtask.view.util.Resource
import retrofit2.Response

interface MainRepository {

    suspend fun getProducts(): Resource<ProductResponse>
    suspend fun addProducts(product: Product): Resource<ProductResponse>
    suspend fun deleteProduct(id:Int): Resource<ProductResponse>
    suspend fun login(email :String,password :String): Resource<UserResponse>
}