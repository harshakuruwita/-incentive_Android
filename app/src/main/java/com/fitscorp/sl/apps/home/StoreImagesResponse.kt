package com.fitscorp.sl.apps.home

import com.google.gson.annotations.SerializedName

data class StoreImagesResponse (
    @SerializedName("response") val response : ResponseData)

data class ResponseData (
    @SerializedName("code") val code : Int,
    @SerializedName("data") val data:AgentTheme
)

data class AgentTheme (

    @SerializedName("AgentTheme") val AgentTheme:MainData

)

data class MainData (

    @SerializedName("background") val background:List<largeLogo>

)

data class largeLogo (
    @SerializedName("url") val url : String,
    @SerializedName("path") val path : String,
    @SerializedName("uid") val uid : String
)
data class smallLogo (
    @SerializedName("url") val url : String,
    @SerializedName("path") val path : String,
    @SerializedName("uid") val uid : String
)