package com.fitscorp.sl.apps.register


class Register{
    var email:String?=null
    var firstName:String?=null
    var lastName:String?=null
    var mobileNo:String?=null
    var storeId:Int?=null
    var salesId:String?=null
    var salesIdList:ArrayList<String>?=null
    var userType:String?=null
    var currentStatus:String?=null
    var organizationId:Int?=null
    var userRole:String?=null
    var domain:String?=null
}

//fun registerUser(@Field("") email: String,
//                 @Field("") firstName: String,
//                 @Field("") lastName: String,
//                 @Field("") mobileNo: String,
//                 @Field("") storeId: String,
//                 @Field("") salesId: Int,
//                 @Field("") salesIdList: String,
//                 @Field("") userType: String,
//                 @Field("") currentStatus: String,
//                 @Field("") organizationId: Int,
//                 @Field("") userRole: String
//{
//    "lastName" : "yes",
//    "email" : "as3@mailinator.com",
//    "firstName" : "harsh",
//    "storeId" : 42,
//    "organizationId" : 75,
//    "mobileNo" : 3222233333,
//    "salesId" : 333,
//    "currentStatus" : "pending",
//    "userRole" : "STORE_MANAGER",
//    "userType" : "ORGANIZATION",
//    "salesIdList" : [
//
//    ]
//}