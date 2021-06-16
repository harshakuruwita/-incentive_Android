package com.fitscorp.sl.apps.login

import com.google.gson.annotations.SerializedName

data class ErrorResponse (

    @SerializedName("response") val response : ErrorMaonUserLogin

)
data class ErrorMaonUserLogin (

    @SerializedName("code") val code : Int,
    @SerializedName("e") val data : String
)

//{"response":{"code":400,"e":"The username or password provided in the request are invalid"}}