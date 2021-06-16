package com.fitscorp.sl.apps.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.fitscorp.sl.apps.App
import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.common.getStoreImage
import com.fitscorp.sl.apps.di.BaseActivity
import com.fitscorp.sl.apps.home.model.FCMModel
import com.fitscorp.sl.apps.home.model.UserRestModel
import com.fitscorp.sl.apps.login.FrogotPasswordVM
import com.fitscorp.sl.apps.login.LoginActivity
import com.fitscorp.sl.apps.login.LoginActivityVM
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.fitscorp.sl.apps.register.RegisterActivity
import com.fitscorp.sl.apps.splash.WelcomeLoginActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.progressBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    var userRole:String?=null
    var storeId:Int?=null

    @Inject
    lateinit var loginVM: LoginActivityVM

    @Inject
    lateinit var spalshVM: SplashVM

    companion object {
        fun startActivity(context: Activity) {
            val intent = Intent(context, SplashActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        App.getInstance().appComponent.inject(this)
        getStoreImages("33")

          val image = spalshVM.sharedPref.getStoreImage()
        Glide.with(this).load(image).into(img_background)
     //   Log.d("---908---",image)
        ////



        if(loadLocalData()){
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("fromActivity", "splash")
            startActivity(intent)
            finish()
          //  MainActivity.startActivity(this@SplashActivity)
        }


        btn_login.setOnClickListener {

            LoginActivity.startActivity(this@SplashActivity)
        }




    }


    override fun onBackPressed() {
        //finish()
        finishAffinity()
    }



    fun loadLocalData(): Boolean {
        var isDataAvailable:Boolean = false
        val data = loginVM.getData()

        val gson = Gson()
        val type = object : TypeToken<LoginUserMainResponse>() {}.type

        if(gson.fromJson<LoginUserMainResponse>(data, type)==null) {

        }else{
            isDataAvailable=true
        }

        return isDataAvailable
    }


    private fun getStoreImages(organizationId: String) {

        subscription.add(spalshVM.getStoreImages(organizationId).subscribeOn(
            Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isSuccess) {

                    val storeDataObj=spalshVM.storeImageDataObj
                    val logo = storeDataObj!!.response.data.AgentTheme.background[0].path.toString()
                   // val logo = storeDataObj!!.response.data.AgentTheme.
                    Glide.with(this).load(logo).into(img_background)
                    Log.d("===334======", logo)

                } else {
                  //  showMessage(R.string.service_loading_fail)
                }
            }, {
                Log.d("====0======", it.stackTrace.toString())

              //  showMessage(R.string.service_loading_fail)
            })

        )
    }

}
