package com.fitscorp.sl.apps.menu.data

import com.google.gson.annotations.SerializedName


data class Leaderboard (

    @SerializedName("response") val response : LeaderboardMainResponse)

data class LeaderboardMainResponse (

    @SerializedName("dataArr") val dataArr : List<LeaderboardData>,
    @SerializedName("incentive") val incentive : List<IncentiveboardData>,
    @SerializedName("message") val message : String,
    @SerializedName("code") val code : Int,
    @SerializedName("success") val success : Boolean

)


data class LeaderboardData (

    @SerializedName("Average_Sale_Price_per_accessory") val average_Sale_Price_per_accessory : Double,
    @SerializedName("Attachment_Rate") val attachment_Rate : Double,
    @SerializedName("Pacificomm_Sales_Count") val pacificomm_Sales_Count : Double,
    @SerializedName("Point") val point : Double,
    @SerializedName("Points_Results") val points_Results : Double,
    @SerializedName("advancedEQ") val advancedEQ : Double,
    @SerializedName("User Id") val userId : String,
    @SerializedName("StorePrimaryId") val StorePrimaryId : Int,
    @SerializedName("Position") val position : Int
)

data class IncentiveboardData (

    @SerializedName("ColorSettings") val ColorSettings : String

)
data class ColorSettings (

    @SerializedName("leaderBoard") val leaderBoard : String,
    @SerializedName("firstPlace") val firstPlace : String

)