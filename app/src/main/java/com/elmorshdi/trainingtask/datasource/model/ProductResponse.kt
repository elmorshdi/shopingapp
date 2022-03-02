package com.elmorshdi.trainingtask.datasource.model

import com.elmorshdi.trainingtask.domain.model.Product

data class ProductResponse(
    var data :ArrayList<Product>? = null,
    var status: Boolean? = null,
    var count: Int? = null,
    var message: String? = null)