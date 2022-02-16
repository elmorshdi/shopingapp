package com.elmorshdi.trainingtask.model

data class ProductResponse(
    var data :ArrayList<Product>? = null,
    var status: Boolean? = null,
    var count: Int? = null,
    var message: String? = null)