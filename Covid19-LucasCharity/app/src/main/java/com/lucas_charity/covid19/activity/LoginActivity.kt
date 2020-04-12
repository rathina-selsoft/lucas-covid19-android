package com.lucas_charity.covid19.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.lucas_charity.covid19.MainActivity
import com.lucas_charity.covid19.R
import com.lucas_charity.covid19.models.User
import com.lucas_charity.covid19.models.UserResponse
import com.lucas_charity.covid19.remote.Api
import com.lucas_charity.covid19.remote.RetrofitEngine
import com.lucas_charity.covid19.utils.SessionManager
import com.lucas_charity.covid19.utils.Utils
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("NAME_SHADOWING")
class LoginActivity : AppCompatActivity() {

    @BindView(R.id.txt_login)
    lateinit var loginTxt: TextView

    @BindView(R.id.email)
    lateinit var email: EditText

    @BindView(R.id.password)
    lateinit var password: ShowHidePasswordEditText

    @BindView(R.id.btn_login)
    lateinit var loginBtn: Button

    @BindView(R.id.txt_register)
    lateinit var accountTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.btn_login)
    fun loginTapped(view: View) {

        val user: User = User()
        var isValid = true;

        if (Utils.isValidEmail(email.text.toString())) {
            user.emailId = email.text.toString()
        } else {
            isValid = false
            email.error = "Please enter Email"
        }

        if (Utils.isValid(password.text.toString())) {
            user.password = password.text.toString()
        } else {
            isValid = false
            password.error = "Please enter Password"
        }

        if (isValid)
            sendLogin(user)
    }

    @OnClick(R.id.txt_register)
    fun openRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
        this.finish()
    }

    private fun sendLogin(user: User) {
        val api: Api = RetrofitEngine.getClient
        val call: Call<UserResponse> = api.loginUser(user)
        call.enqueue(object : Callback<UserResponse?> {
            override fun onResponse(
                call: Call<UserResponse?>,
                response: Response<UserResponse?>
            ) {
                if (response.isSuccessful) {
                    val userResp = response.body()
                    if (userResp!!.success!!) {
                        val sessionManager = SessionManager(this@LoginActivity)
                        sessionManager.setUserDetails(userResp.user!!)
                        Utils.showToast(this@LoginActivity, "Login Success")
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        this@LoginActivity.finish()
                    } else {
                        Utils.showToast(this@LoginActivity, userResp.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {

            }
        })

    }
}
