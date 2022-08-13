package com.elmorshdi.trainingtask.repository

import com.elmorshdi.trainingtask.datasource.model.ProductResponse
import com.elmorshdi.trainingtask.datasource.model.UserResponse
import com.elmorshdi.trainingtask.datasource.repository.MainRepository
import com.elmorshdi.trainingtask.domain.model.Product
import com.elmorshdi.trainingtask.domain.repository.Repository
import com.elmorshdi.trainingtask.view.util.Resource

class FakeRepository : MainRepository {
    private var shouldReturnNetworkError=false
    fun setShouldReturnNetworkError(value:Boolean){
        shouldReturnNetworkError=value
    }

    override suspend fun getProducts(): Resource<ProductResponse> {
        return if (shouldReturnNetworkError){
            Resource.Error("error")
        }else{
            Resource.Success(ProductResponse(ArrayList(),true,0,"m"))
        }
    }

    override suspend fun addProducts(product: Product): Resource<ProductResponse> {
        return if (shouldReturnNetworkError){
            Resource.Error("error")
        }else{
            Resource.Success(ProductResponse(ArrayList<Product>(),true,0,"product Added"))
        }
    }

    override suspend fun deleteProduct(id: Int): Resource<ProductResponse> {
        return if (shouldReturnNetworkError){
            Resource.Error("error")
        }else{
            Resource.Success(ProductResponse(ArrayList<Product>(),true,0,"product deleted"))
        }
    }

    override suspend fun login(email: String, password: String): Resource<UserResponse> {
        return if (shouldReturnNetworkError){
            Resource.Error("error")
        }else{
            Resource.Success(UserResponse(null,"product deleted"))
        }
    }
}