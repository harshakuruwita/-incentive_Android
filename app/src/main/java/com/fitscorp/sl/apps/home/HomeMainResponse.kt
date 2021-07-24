package com.fitscorp.sl.apps.home

import com.google.gson.annotations.SerializedName


data class HomeMainResponse (

    @SerializedName("response") val response : HomeMain
)

data class HomeMain (

    @SerializedName("Periods") val periods : List<Periods>,
    @SerializedName("code") val code : Int,
    @SerializedName("success") val success : Boolean
)


data class Periods (

    @SerializedName("url") val url : String,
    @SerializedName("documentType") val documentType : String,
    @SerializedName("documentData") val documentData : String,
    @SerializedName("incentiveId") val incentiveId : Int,
    @SerializedName("timePeriodArr") val timePeriodArr : List<TimePeriodArr>,
    @SerializedName("incentiveName") val incentiveName : String
)

data class TimePeriodArr (

    @SerializedName("name") val name : String,
    @SerializedName("dropDownList") val dropDownList : List<DropDownList>
)


data class DropDownList (

    @SerializedName("EndDate") val endDate : String,
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("StartDate") val startDate : String
)