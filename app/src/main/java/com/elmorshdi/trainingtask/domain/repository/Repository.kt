package com.elmorshdi.trainingtask.domain.repository

import com.elmorshdi.trainingtask.datasource.model.ProductResponse
import com.elmorshdi.trainingtask.datasource.model.UserResponse
import com.elmorshdi.trainingtask.datasource.network.ApiService
import com.elmorshdi.trainingtask.datasource.repository.MainRepository
import com.elmorshdi.trainingtask.domain.model.Product
import com.elmorshdi.trainingtask.view.util.Resource
import okhttp3.ResponseBody
import org.json.JSONObject
import javax.inject.Inject


class Repository @Inject constructor(private val apiService: ApiService) : MainRepository {
    override suspend fun getProducts(): Resource<ProductResponse> {
        return try {
            val response = apiService.getProducts()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                try {
                    Resource.Error(getError(response.errorBody()!!))
                } catch (e: java.lang.Exception) {
                    Resource.Error(e.message!!)
                }
            }
        } catch (e: Exception) {
                 Resource.Error("An error Occurred Check your Internet Connection")

        }
    }

    override suspend fun addProducts(product: Product): Resource<ProductResponse> {
        return try {
            val response = apiService.addProducts(product)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                try {
                    Resource.Error(getError(response.errorBody()!!))

                } catch (e: java.lang.Exception) {
                    Resource.Error(e.message!!)
                }
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error Occurred")
        }
    }

    override suspend fun deleteProduct(id: Int): Resource<ProductResponse> {
        return try {
            val response = apiService.deleteProduct(id)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                try {
                    Resource.Error(getError(response.errorBody()!!))

                } catch (e: java.lang.Exception) {
                    Resource.Error(e.message!!)
                }
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error Occurred")
        }
    }

    override suspend fun login(email: String, password: String): Resource<UserResponse> {
        return try {
            val response = apiService.login(email, password)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                try {
                    Resource.Error(getError(response.errorBody()!!))

                } catch (e: Exception) {
                    Resource.Error(e.message!!)
                }
            }
        } catch (e: Exception) {
                 Resource.Error("An error Occurred Check your Internet Connection")

        }
    }

    private fun getError(response: ResponseBody): String {
        val jObjError = JSONObject(response.string())
        return jObjError.getString("message")
    }
 }

