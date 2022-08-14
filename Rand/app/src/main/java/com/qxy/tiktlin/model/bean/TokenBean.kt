package com.qxy.tiktlin.model.bean

data class TokenBean(
    val data: Data2,
    val message: String
)

data class Data2(
    val access_token: String,
    val description: String,
    val error_code: String,
    val expires_in: String,
    val open_id: String,
    val refresh_expires_in: String,
    val refresh_token: String,
    val scope: String
)