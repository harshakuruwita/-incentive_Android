package com.fitscorp.sl.apps.home.model
import com.google.gson.annotations.SerializedName

data class ExecutiveLeaderResponse (

    @SerializedName("response") val response : List<ExecutiveLeaderBordResponse>
)

data class ExecutiveLeaderBordResponse (

    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("allPoints") val allPoints : String,
    @SerializedName("topData") val topData : ExecutiveLeaderTopData,
    @SerializedName("position") val position : Int
)

data class ExecutiveLeaderTopData (

    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("point") val point : Double
)