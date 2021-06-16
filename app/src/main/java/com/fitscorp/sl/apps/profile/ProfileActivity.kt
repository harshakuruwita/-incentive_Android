package com.fitscorp.sl.apps.profile

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.home.MainActivity
import com.fitscorp.sl.apps.login.LoginActivity
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.layout_email
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.fitscorp.sl.apps.App
import com.fitscorp.sl.apps.common.saveData
import com.fitscorp.sl.apps.home.SplashActivity
import com.fitscorp.sl.apps.login.LoginActivityVM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.progressBar
import java.util.regex.Pattern
import javax.inject.Inject


class ProfileActivity : AppCompatActivity() {

    val subscription = CompositeDisposable()
    lateinit var dataUser:String

    @Inject
    lateinit var profileVM: ProfileVM
    @Inject
    lateinit var loginVM: LoginActivityVM
    companion object {
        fun startActivity(context: Activity,data:String) {

            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra("userdata",data)
            context.startActivity(intent)
        }
    }

     private fun readExtra():String{
        lateinit var dataUser:String

         val bundle=intent.extras

         dataUser= bundle?.getSerializable("userdata") as String


         return dataUser
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)



        App.getInstance().appComponent.inject(this)
        val strData=readExtra()
        val gson = Gson()
        val type = object : TypeToken<LoginUserMainResponse>() {}.type
        val dataLogin=gson.fromJson<LoginUserMainResponse>(strData, type)

        val user=dataLogin.response.data.user




       var secondSalesId = "-"
        val storeName = dataLogin.response.data.storeName

        (layout_name.findViewById(R.id.user_details_lable) as TextView).text = "Name"
        (layout_name.findViewById(R.id.user_details_value) as TextView).text = user.firstName + " " + user.lastName

        (layout_email.findViewById(R.id.user_details_lable) as TextView).text = "Email"
        (layout_email.findViewById(R.id.user_details_value) as TextView).text = user.email

        (layout_phone.findViewById(R.id.user_details_lable) as TextView).text = "Mobile"
        (layout_phone.findViewById(R.id.user_details_value) as TextView).text = user.mobileNo.toString()

        (layout_store.findViewById(R.id.user_details_lable) as TextView).text = "Store Name"
        (layout_store.findViewById(R.id.user_details_value) as TextView).text = storeName


        (layout_id.findViewById(R.id.user_details_lable) as TextView).text = "Store ID"
        (layout_id.findViewById(R.id.user_details_value) as TextView).text = user.storeId.toString()

        (layout_salesId.findViewById(R.id.user_details_lable) as TextView).text = "Primary SalesID"
        (layout_salesId.findViewById(R.id.user_details_value) as TextView).text = user.salesId

        if(dataLogin.response.data.salesIds.isNotEmpty()){
             secondSalesId = dataLogin.response.data.salesIds.toString()

        }else{
             secondSalesId = "-"
        }
        (layout_salesId_second.findViewById(R.id.user_details_lable) as TextView).text = "Secondary Sales ID"
        (layout_salesId_second.findViewById(R.id.user_details_value) as TextView).text = secondSalesId



        txt_logout.setOnClickListener {


            val builder = AlertDialog.Builder(this)
            builder.setTitle("Are you sure you want to logout?")
            builder.setMessage("")
            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton("YES") { dialog, which ->
                profileVM.callLogout()
                SplashActivity.startActivity(this@ProfileActivity)

                finish()
            }

            builder.setNegativeButton("NO") { dialog, which ->
                Toast.makeText(applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT).show()
            }


            builder.show()


          //  LoginActivity.startActivity(this@ProfileActivity)
           // finish()
        }

        btn_backImgBtn.setOnClickListener {

            finish()
        }

        btn_contactus.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("fromActivity", "contact")
            startActivity(intent)
            finish()
        }

        btn_edit_name.setOnClickListener {
            val intent = Intent(this, EditProfile::class.java)
            val data = profileVM.getLoginData()

            EditProfile.startActivity(this,data)
            finish()
        }
        getLoginUser()
    }


    private fun getLoginUser() {

        subscription.add(loginVM.getLoginUser().subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())

            .subscribe({
                if (it.isSuccess) {
//
                    val data= loginVM.loginUserMainResponse
                    if(data!!.response.data.user.userRole.equals("SALES_REP")){

                    }else if(data!!.response.data.user.userRole.equals("STORE_MANAGER")){

                    }

                    else {


                        Toast.makeText(this,"This user do not have permission to login mobile application",Toast.LENGTH_LONG).show()
                    }
                    //  MainActivity.startActivity(this@LoginActivity)
//                    RegisterActivity.startActivity(this@LoginActivity)

                } else {

                    val resError=   it.message.toString()
                    //  showMessage("This user do not have permission to login mobile application")
                    Log.d("4567","0989")



                }
            }, {
                Log.d("====1======", it.stackTrace.toString())
                progressBar.visibility = View.GONE
                //  showMessage(R.string.service_loading_fail)
            })

        )
//
    }
}
