package com.fitscorp.sl.apps

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.fitscorp.sl.apps.common.MERCHANT_URL
import com.fitscorp.sl.apps.di.BaseActivity
import com.fitscorp.sl.apps.home.model.UserRestModel
import com.fitscorp.sl.apps.login.FrogotPassword
import com.fitscorp.sl.apps.login.FrogotPasswordVM
import com.fitscorp.sl.apps.login.LoginActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.progressBar
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_resend__code.*


class Resend_Code : BaseActivity() {
    @Inject
    lateinit var frogotpwVM: FrogotPasswordVM

    companion object {
        fun startActivity(context: Activity) {
            val intent = Intent(context, Resend_Code::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resend__code)

        App.getInstance().appComponent.inject(this)

        btn_resendcode.setOnClickListener {
            submitLogin()
        }


    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun submitLogin() {

        if (txt_email_resend_code.text.isEmpty()) {
            showAlert("Email is required!")

            return
        }else if(!isEmailValid(txt_email_resend_code.text.toString())){
            showAlert("Invalid Email!")

            return
        }
        val domainVAL = MERCHANT_URL

        var email = txt_email_resend_code.text.toString()



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
                    builder.setMessage("OTP code resent. Please check your Email.")

                    // Set a positive button and its click listener on alert dialog
                    builder.setPositiveButton("Ok"){dialog, which ->
                        LoginActivity.startActivity(this@Resend_Code)
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


