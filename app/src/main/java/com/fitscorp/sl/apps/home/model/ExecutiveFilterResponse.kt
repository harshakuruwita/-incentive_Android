package com.fitscorp.sl.apps.home.model



import com.google.gson.annotations.SerializedName

data class ExecutiveFilterResponse (

    @SerializedName("response") var response : Response

)

data class Response (

    @SerializedName("code") var code : Int,
    @SerializedName("data") var regionData : List<RegionData>,
    @SerializedName("success") var success : Boolean

)

data class RegionData (

    @SerializedName("id") var id : String,
    @SerializedName("name") var name : String,
    @SerializedName("regionId") var regionId : String,
    @SerializedName("regionName") var regionName : String,
    @SerializedName("storeData") var storeData : List<StoreData>

)


data class SalesData (

    @SerializedName("userType") var userType : String,
    @SerializedName("userId") var userId : String,
    @SerializedName("firstName") var firstName : String,
    @SerializedName("lastName") var lastName : String,
    @SerializedName("salesId") var salesId : String,
    @SerializedName("userRole") var userRole : String,
    @SerializedName("storeName") var storeName : String,
    @SerializedName("storeId") var storeId : String,
    @SerializedName("region") var region : Int,
    @SerializedName("regionName") var regionName : String,
    @SerializedName("organizationId") var organizationId : Int

)

data class StoreData (

    @SerializedName("storeId") var storeId : String,
    @SerializedName("storeName") var storeName : String,
    @SerializedName("salesData") var salesData : List<SalesData>

)