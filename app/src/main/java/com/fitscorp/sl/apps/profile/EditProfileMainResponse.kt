package com.fitscorp.sl.apps.profile

import com.google.gson.annotations.SerializedName

data class EditProfileMainResponse (

    @SerializedName("response") val response : EditProfileMainResponseData
)


data class EditProfileMainResponseData (

    @SerializedName("code") val code : Int,
    @SerializedName("success") val success : Boolean

)