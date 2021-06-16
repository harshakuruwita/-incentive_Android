package com.fitscorp.sl.apps.login


import android.app.Activity
import android.content.Intent
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.fitscorp.sl.apps.App
import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.di.BaseActivity
import com.fitscorp.sl.apps.home.MainActivity
import com.fitscorp.sl.apps.home.model.UserAuthModel
import com.fitscorp.sl.apps.home.model.UserRestModel
import com.fitscorp.sl.apps.register.RegisterActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

import com.fitscorp.sl.apps.common.MERCHANT_URL

class FrogotPassword : BaseActivity() {


    @Inject
    lateinit var frogotpwVM: FrogotPasswordVM

    companion object {
        fun startActivity(context: Activity) {
            val intent = Intent(context, FrogotPassword::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frogotpassword_activity)

        App.getInstance().appComponent.inject(this)

        btn_signup.setOnClickListener {
            submitLogin()
        }


    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun submitLogin() {

        if (txt_email.text.isEmpty()) {
            showAlert("Email is required!")
           // Toast.makeText(this, "Email is required !", Toast.LENGTH_LONG).show()
            return
        }else if(!isEmailValid(txt_email.text.toString())){
            showAlert("Invalid e-mail!")
          //  Toast.makeText(this, "Invalid e-mail !", Toast.LENGTH_LONG).show()
            return
        }
        val domainVAL = MERCHANT_URL

        var email = txt_email.text.toString()



        var p: UserRestModel = UserRestModel()
        p.email = email
        p.domain = domainVAL

        sendResetLink(p)

    }

    private fun showAlert(message: String) {
        val dialogBuilder = AlertDialog.Builder(this)


        dialogBuilder.setMessage(message)
        dialogBuilder.setNegativeButton("Ok") { dialog, which ->

        }
        val alert = dialogBuilder.create()

        alert.setTitle("Error")

        alert.show()
    }

    private fun sendResetLink(p: UserRestModel) {

        val domainVAL = MERCHANT_URL
        subscription.add(frogotpwVM.sendResetLink(p).subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressBar.visibility = View.VISIBLE }
            .doOnTerminate { progressBar.visibility = View.GONE }
            .doOnError { progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {




                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Success")
                    builder.setMessage("Reset password link sent. Please check your e-mail to reset your password.")

                    // Set a positive button and its click listener on alert dialog
                    builder.setPositiveButton("Ok"){dialog, which ->
                        LoginActivity.startActivity(this@FrogotPassword)
                    }



                    // Finally, make the alert dialog using builder
                    val dialog: AlertDialog = builder.create()

                    // Display the alert dialog on app interface
                    dialog.show()




                } else {
                    showAlert("User not available!")
                   // showMessage("User not available !")
                }
            }, {
                Log.d("====0======", it.stackTrace.toString())
                progressBar.visibility = View.GONE
             //   showMessage(R.string.service_loading_fail)
            })

        )
    }


}


