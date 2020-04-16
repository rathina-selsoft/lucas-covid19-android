package com.lucas_charity.covid19.activity.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.lucas_charity.covid19.R
import com.lucas_charity.covid19.activity.FoodDetailActivity
import com.lucas_charity.covid19.activity.MainNavActivity

class HomeFragment : Fragment() {

    @BindView(R.id.btn_for_me)
    lateinit var forMeBtn: Button

    @BindView(R.id.btn_for_other)
    lateinit var forOtherBtn: Button

    private lateinit var mainActivity: MainNavActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        ButterKnife.bind(this, root)
        return root
    }

    @OnClick(R.id.btn_for_me)
    fun moveToFoodDetailForMe() {
        startActivity(Intent(mainActivity, FoodDetailActivity::class.java))
    }

    @OnClick(R.id.btn_for_other)
    fun moveToFoodDetail() {
        startActivity(Intent(mainActivity, FoodDetailActivity::class.java))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainNavActivity
    }
}
