package com.fitscorp.sl.apps.home.model

class UserRestModel {
    var email:String?=null
    var domain:String?=null

}



class UserOtpResendModel {
    var email:String?=null
    var domain:String?=null
    var isResendVerification:Boolean?=true

}


class TokenReGenerateModel {
    var grant_type:String?=null
    var refresh_token:String?=null

}