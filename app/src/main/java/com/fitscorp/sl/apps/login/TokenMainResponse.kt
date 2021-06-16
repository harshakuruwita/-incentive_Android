package com.fitscorp.sl.apps.login

import com.google.gson.annotations.SerializedName


data class LoginServiceMainResponse (

    @SerializedName("response") val response : TokenResponse
)


data class TokenResponse (

    @SerializedName("code") val code : Int,
    @SerializedName("e") val ermessage : String,
    @SerializedName("data") val data : TokenData

)

data class TokenData (

    @SerializedName("access_token") val access_token : String,
    @SerializedName("token_type") val token_type : String,
    @SerializedName("expires_in") val expires_in : String,
    @SerializedName("refresh_token") val refresh_token : String
)