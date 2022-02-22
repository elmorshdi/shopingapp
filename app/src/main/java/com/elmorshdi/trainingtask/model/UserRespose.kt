package com.elmorshdi.trainingtask.model

data class UserResponse(
    var data: Data?=null,
    var message: String?=null,
    var status: Boolean?=null,
    var token: String?=null
)

data class Data(
    var email: String?=null,
    var id: Int?=null,
    var name: String?=null
)