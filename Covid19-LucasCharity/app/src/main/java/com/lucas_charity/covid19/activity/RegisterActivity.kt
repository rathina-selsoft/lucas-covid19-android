package com.lucas_charity.covid19.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.lucas_charity.covid19.R
import com.lucas_charity.covid19.models.User
import com.lucas_charity.covid19.models.UserResponse
import com.lucas_charity.covid19.remote.Api
import com.lucas_charity.covid19.remote.RetrofitEngine
import com.lucas_charity.covid19.utils.Utils
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {

    @BindView(R.id.txt_register)
    lateinit var registerTxt: TextView

    @BindView(R.id.full_name)
    lateinit var fullName: TextView

    @BindView(R.id.email)
    lateinit var email: TextView

    @BindView(R.id.phone)
    lateinit var phone: TextView

    @BindView(R.id.password)
    lateinit var password: ShowHidePasswordEditText

    @BindView(R.id.btn_register)
    lateinit var registerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.btn_register)
    fun registerTapped(view: View) {
        val user: User = User()
        var isValid = true;

        if (Utils.isValid(fullName.text.toString())) {
            user.fullName = fullName.text.toString()
        } else {
            isValid = false
            fullName.error = "Please enter Full Name"
        }
        if (Utils.isValidEmail(email.text.toString())) {
            user.email = email.text.toString()
        } else {
            isValid = false
            email.error = "Please enter Email"
        }
        if (Utils.isValid(phone.text.toString())) {
            user.phone = phone.text.toString()
        } else {
            isValid = false
            phone.error = "Please enter Phone Number"
        }
        if (Utils.isValid(password.text.toString())) {
            user.password = password.text.toString()
        } else {
            isValid = false
            password.error = "Please enter Password"
        }
        user.userType = 2

        if (isValid)
            sendRegister(user)
    }

    private fun sendRegister(user: User) {
        startActivity(Intent(this, LoginActivity::class.java))
//        val api: Api = RetrofitEngine.getClient
//        val call: Call<UserResponse> = api.registerNewUser(user)
//        call.enqueue(object : Callback<UserResponse?> {
//            override fun onResponse(
//                call: Call<UserResponse?>,
//                response: Response<UserResponse?>
//            ) {
//
//            }
//            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
//
//            }
//        })
    }


}
