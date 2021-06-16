package com.fitscorp.sl.apps.login

import com.google.gson.annotations.SerializedName


data class LoginUserMainResponse (

    @SerializedName("response") val response : LoginUserMainResponseDataMain
)


data class LoginUserMainResponseDataMain (

    @SerializedName("code") val code : Int,
    @SerializedName("data") val data : LoginUserMainResponseData,
    @SerializedName("message") val message : String
)

data class LoginUserMainResponseData (

    @SerializedName("user") val user : User,
    @SerializedName("org") val org : Org,
    @SerializedName("storeName") val storeName : String,
    @SerializedName("salesIds") val salesIds : List<String>
)

data class User (

    @SerializedName("userId") val userId : Int,
    @SerializedName("userType") val userType : String,
    @SerializedName("organizationId") val organizationId : Int,
    @SerializedName("currentStatus") val currentStatus : String,
    @SerializedName("email") val email : String,
    @SerializedName("firstName") val firstName : String,
    @SerializedName("mobileNo") val mobileNo : String,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updateAt") val updateAt : String,
    @SerializedName("userRole") val userRole : String,
    @SerializedName("regionId") val regionId : String,
    @SerializedName("storeId") val storeId : Int,
    @SerializedName("azureId") val azureId : String,
    @SerializedName("lastName") val lastName : String,
    @SerializedName("salesId") val salesId : String,
    @SerializedName("DeviceIds") val deviceIds : String,
    @SerializedName("id") val id : Int,
    @SerializedName("CompanyName") val companyName : String,
    @SerializedName("CompanyAddress") val companyAddress : String,
    @SerializedName("City") val city : String,
    @SerializedName("Country") val country : String,
    @SerializedName("ContactPersonName") val contactPersonName : String,
    @SerializedName("ContactNumber") val contactNumber : String,
    @SerializedName("ContactEmail") val contactEmail : String,
    @SerializedName("LoginEmail") val loginEmail : String,
    @SerializedName("CompanyStatus") val companyStatus : String,
    @SerializedName("OrganizationTheme") val organizationTheme : String,
    @SerializedName("AgentTheme") val agentTheme : String,
    @SerializedName("SalesConfigCSV") val salesConfigCSV : String,
    @SerializedName("CompanyDomain") val companyDomain : String,
    @SerializedName("token") val token : String
)


data class Org (
    @SerializedName("id") val id : Int,
    @SerializedName("CompanyName") val companyName : String,
    @SerializedName("CompanyAddress") val companyAddress : String,
    @SerializedName("City") val city : String,
    @SerializedName("Country") val country : String,
    @SerializedName("ContactPersonName") val contactPersonName : String,
    @SerializedName("ContactNumber") val contactNumber : String,
    @SerializedName("ContactEmail") val contactEmail : String,
    @SerializedName("LoginEmail") val loginEmail : String,
    @SerializedName("CompanyStatus") val companyStatus : String,
    @SerializedName("OrganizationTheme") val organizationTheme : String,
    @SerializedName("AgentTheme") val agentTheme : String,
    @SerializedName("SalesConfigCSV") val salesConfigCSV : String,
    @SerializedName("CompanyDomain") val companyDomain : String
)

