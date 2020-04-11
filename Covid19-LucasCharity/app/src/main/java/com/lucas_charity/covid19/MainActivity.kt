package com.lucas_charity.covid19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.lucas_charity.covid19.activity.FoodDetailActivity
import com.lucas_charity.covid19.activity.LoginActivity
import com.lucas_charity.covid19.models.User
import com.lucas_charity.covid19.utils.SessionManager
import com.lucas_charity.covid19.utils.Utils

class MainActivity : AppCompatActivity() {

    @BindView(R.id.btn_for_me)
    lateinit var forMeBtn: Button

    @BindView(R.id.btn_for_other)
    lateinit var forOtherBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        val sessionManager = SessionManager(this)
        val currentUser: User = sessionManager.getUserDetails()!!
        if (currentUser.userId == 0) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        Utils.user = currentUser
    }

    @OnClick(R.id.btn_for_me)
    fun moveToFoodDetailForMe() {
        startActivity(Intent(this, FoodDetailActivity::class.java))
    }

    @OnClick(R.id.btn_for_other)
    fun moveToFoodDetail() {
        startActivity(Intent(this, FoodDetailActivity::class.java))
    }


}
