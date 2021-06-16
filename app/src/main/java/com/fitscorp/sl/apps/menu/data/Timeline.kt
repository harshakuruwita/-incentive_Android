package com.fitscorp.sl.apps.menu.data

import com.google.gson.annotations.SerializedName


data class TimelineMainRespone (

    @SerializedName("response") val response : TimelineResponse
)


data class TimelineResponse (

    @SerializedName("code") val code : Int,
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : String,
    @SerializedName("dataArr") val dataArr : List<DataArr>
)



data class DataArr (

    @SerializedName("LongName") val longName : String,
    @SerializedName("ShortName") val shortName : String,
    @SerializedName("value1") val value1 : String,
    @SerializedName("value2") val value2 : Int,
    @SerializedName("color1") val color1 : String,
    @SerializedName("color2") val color2 : String,
    @SerializedName("MeasureType") val measureType : String,
    @SerializedName("specialPlace") val specialPlace : String,
    @SerializedName("MobileName") val MobileName : String,
    @SerializedName("TargetMobileName") val TargetMobileName : String,
    @SerializedName("total") val total : Double,
    @SerializedName("target") val target : Double
)