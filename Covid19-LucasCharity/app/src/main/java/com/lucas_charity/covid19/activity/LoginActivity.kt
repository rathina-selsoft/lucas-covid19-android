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
import com.lucas_charity.covid19.utils.SessionManager
import com.lucas_charity.covid19.utils.Utils
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText

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
            user.email = email.text.toString()
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
        val user = User()
        user.userId = 1

        val sessionManager = SessionManager(this)
        sessionManager.setUserDetails(user)

        startActivity(Intent(this, MainActivity::class.java))
        this.finish()
//        val api: Api = RetrofitEngine.getClient
//        val call: Call<UserResponse> = api.loginUser(user)
//        call.enqueue(object : Callback<UserResponse?> {
//            override fun onResponse(
//                call: Call<UserResponse?>,
//                response: Response<UserResponse?>
//            ) {
//
//            }
//
//            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
//
//            }
//        })
    }
}
