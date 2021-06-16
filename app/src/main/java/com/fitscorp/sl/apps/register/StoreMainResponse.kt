package com.fitscorp.sl.apps.register

import com.google.gson.annotations.SerializedName




data class StoreMainResponse (

    @SerializedName("response") val response : StoreMainResponseData
)

data class StoreMainResponseData (

    @SerializedName("code") val code : Int,
    @SerializedName("success") val success : Boolean,
    @SerializedName("data") val data : List<StoreMain>
)

data class StoreMain (

    @SerializedName("id") val id : Int,
    @SerializedName("storeName") val storeName : String,
    @SerializedName("storeId") val storeId : String,
    @SerializedName("storeStatus") val storeStatus : String,
    @SerializedName("organizationId") val organizationId : Int,
    @SerializedName("region") val region : Int,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String
)