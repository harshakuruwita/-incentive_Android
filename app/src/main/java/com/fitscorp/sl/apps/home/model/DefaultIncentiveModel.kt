package com.fitscorp.sl.apps.home.model

import com.google.gson.annotations.SerializedName


data class DefaultIncentiveModel (

    @SerializedName("response") var response : DefialtIncentiveResponse

)

data class Data (

    @SerializedName("Id") var Id : Int,
    @SerializedName("OrganizationId") var OrganizationId : Int,
    @SerializedName("Category") var Category : String,
    @SerializedName("UserId") var UserId : String,
    @SerializedName("StoreId") var StoreId : String,
    @SerializedName("Detail") var Detail : String,
    @SerializedName("CreatedAt") var CreatedAt : String,
    @SerializedName("UpdatedAt") var UpdatedAt : String

)

data class DefialtIncentiveResponse (

    @SerializedName("code") var code : Int,
    @SerializedName("success") var success : Boolean,
    @SerializedName("data") var data : Data

)