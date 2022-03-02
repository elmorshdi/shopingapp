package com.elmorshdi.trainingtask.datasource.model

import com.elmorshdi.trainingtask.domain.model.UserData

data class UserResponse(
    var data: UserData?=null,
    var message: String?=null,
    var status: Boolean?=null,
    var token: String?=null
)

