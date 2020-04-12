package com.lucas_charity.covid19.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.lucas_charity.covid19.R
import com.lucas_charity.covid19.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

class FoodDetailActivity : AppCompatActivity() {

    @BindView(R.id.full_name)
    lateinit var fullName: EditText

    @BindView(R.id.phone)
    lateinit var phone: EditText

    @BindView(R.id.address)
    lateinit var address: EditText

    @BindView(R.id.location)
    lateinit var location: Switch

    @BindView(R.id.txt_date)
    lateinit var dateTxt: TextView

    @BindView(R.id.date)
    lateinit var date: TextView

    @BindView(R.id.txt_time)
    lateinit var timeTxt: TextView

    @BindView(R.id.time)
    lateinit var time: TextView

    @BindView(R.id.btn_submit)
    lateinit var submitBtn: Button

    val dateFormat = "MMM/dd/yyyy";
    val timeFormat = "HH:mm a";

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)
        ButterKnife.bind(this)

        fullName.setText(Utils.user!!.fullName)
        phone.setText(Utils.user!!.phoneNumber)

        date.text = SimpleDateFormat(dateFormat).format(System.currentTimeMillis())
        time.text = SimpleDateFormat(timeFormat).format(System.currentTimeMillis())
    }

    @OnClick(R.id.date)
    fun dateTapped() {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val sdf = SimpleDateFormat(dateFormat, Locale.US)
                date.text = sdf.format(cal.time)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    @SuppressLint("SimpleDateFormat")
    @OnClick(R.id.time)
    fun timeTapped() {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            time.text = SimpleDateFormat(timeFormat).format(cal.time)
        }
        TimePickerDialog(
            this,
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            false
        ).show()

    }
}

