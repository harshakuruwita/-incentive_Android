package com.fitscorp.sl.apps.home.model
import com.google.gson.annotations.SerializedName

data class ExecutiveLeaderBordResponse (

    @SerializedName("response") var response : ExecutiveResponse

)

data class ExecutiveResponse (

    @SerializedName("dataArr") var dataArr : List<ExecutiveDataArr>,
    @SerializedName("incentive") var incentive : List<ExecutiveIncentive>

)


data class ExecutiveIncentive (

    @SerializedName("Id") var Id : Int,
    @SerializedName("Name") var Name : String,
    @SerializedName("OrganizationId") var OrganizationId : Int,
    @SerializedName("StartDate") var StartDate : String,
    @SerializedName("EndDate") var EndDate : String,
    @SerializedName("CSVFileNames") var CSVFileNames : String,
    @SerializedName("IncentiveStatus") var IncentiveStatus : String,
    @SerializedName("CreatedAt") var CreatedAt : String,
    @SerializedName("UpdateAt") var UpdateAt : String,
    @SerializedName("CreatedBy") var CreatedBy : String,
    @SerializedName("CSVConfigure") var CSVConfigure : String,
    @SerializedName("Url") var Url : String,
    @SerializedName("ColorSettings") var ColorSettings : String,
    @SerializedName("documentData") var documentData : String,
    @SerializedName("documentType") var documentType : String

)

data class ExecutiveDataArr (

    @SerializedName("id") var id : String,
    @SerializedName("name") var name : String,
    @SerializedName("allPoints") var allPoints : Double,
    @SerializedName("topData") var topData : TopData,
    @SerializedName("position") var position : Int

)

data class TopData (

    @SerializedName("id") var id : String,
    @SerializedName("name") var name : String,
    @SerializedName("point") var point : Double

)