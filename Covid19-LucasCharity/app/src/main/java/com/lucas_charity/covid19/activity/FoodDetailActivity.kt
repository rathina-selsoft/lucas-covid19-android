package com.lucas_charity.covid19.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import androidx.core.app.ActivityCompat
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.lucas_charity.covid19.R
import com.lucas_charity.covid19.utils.Utils
import java.text.SimpleDateFormat
import java.util.*
import android.provider.Settings
import android.widget.*
import com.google.android.gms.location.*
import com.lucas_charity.covid19.models.FoodDetail
import com.lucas_charity.covid19.models.FoodDetailResponse
import com.lucas_charity.covid19.remote.Api
import com.lucas_charity.covid19.remote.RetrofitEngine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    val timeFormat = "hh:mm a";
    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    var latitude: Double = 0.0
    var longitude: Double = 0.0
    lateinit var dateCalendar: Calendar
    lateinit var timeCalendar: Calendar

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)
        ButterKnife.bind(this)

        fullName.setText(Utils.user!!.fullName)
        phone.setText(Utils.user!!.phoneNumber)

        dateCalendar = Calendar.getInstance()
        timeCalendar = Calendar.getInstance()

        date.text = SimpleDateFormat(dateFormat).format(dateCalendar.time)
        time.text = SimpleDateFormat(timeFormat).format(timeCalendar.time)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        location.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                getCurrentLocation()
            }
        }
    }

    private fun getCurrentLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        latitude = location.latitude
                        longitude = location.longitude
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            latitude = mLastLocation.latitude
            longitude = mLastLocation.longitude
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID // location permission id
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getCurrentLocation()
            }
        }
    }

    @OnClick(R.id.date)
    fun dateTapped() {
        dateCalendar = Calendar.getInstance()
        DatePickerDialog(
            this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                dateCalendar.set(Calendar.YEAR, year)
                dateCalendar.set(Calendar.MONTH, monthOfYear)
                dateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val sdf = SimpleDateFormat(dateFormat, Locale.US)
                date.text = sdf.format(dateCalendar.time)
            },
            dateCalendar.get(Calendar.YEAR),
            dateCalendar.get(Calendar.MONTH),
            dateCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    @SuppressLint("SimpleDateFormat")
    @OnClick(R.id.time)
    fun timeTapped() {
        timeCalendar = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            timeCalendar.set(Calendar.HOUR_OF_DAY, hour)
            timeCalendar.set(Calendar.MINUTE, minute)
            time.text = SimpleDateFormat(timeFormat).format(timeCalendar.time)
        }
        TimePickerDialog(
            this,
            timeSetListener,
            timeCalendar.get(Calendar.HOUR_OF_DAY),
            timeCalendar.get(Calendar.MINUTE),
            false
        ).show()

    }

    @OnClick(R.id.btn_submit)
    fun submitTapped() {
        val foodDetail = FoodDetail()
        var isValid = true;

        if (Utils.isValid(fullName.text.toString())) {
            foodDetail.fullName = fullName.text.toString()
        } else {
            isValid = false
            fullName.error = "Please enter Full Name"
        }

        if (Utils.isValid(phone.text.toString())) {
            foodDetail.phoneNumber = phone.text.toString()
        } else {
            isValid = false
            phone.error = "Please enter Full Name"
        }

        if (location.isChecked) {
            foodDetail.latitude = latitude
            foodDetail.longitude = longitude
        }

        foodDetail.date = date.text.toString()
        foodDetail.time = time.text.toString()
        foodDetail.dateLong = dateCalendar.timeInMillis
        foodDetail.timeLong = timeCalendar.timeInMillis
        foodDetail.address = address.text.toString()
        foodDetail.userId = Utils.user?.userId

        if (isValid)
            sendFoodRequest(foodDetail)
    }

    private fun sendFoodRequest(foodDetail: FoodDetail) {
        val api: Api = RetrofitEngine.getClient
        val call: Call<FoodDetailResponse> = api.addFood(foodDetail)
        call.enqueue(object : Callback<FoodDetailResponse?> {
            override fun onResponse(
                call: Call<FoodDetailResponse?>,
                response: Response<FoodDetailResponse?>
            ) {
                if (response.isSuccessful) {
                    val foodDetailResponse = response.body()
                    if (foodDetailResponse?.success!!) {
                        val toast = Utils.showToast(this@FoodDetailActivity, "Food request added")
                        toast.show()
                    } else {
                        val toast = Utils.showToast(
                            this@FoodDetailActivity,
                            foodDetailResponse.message!!
                        )
                        toast.show()
                    }
                }
            }

            override fun onFailure(call: Call<FoodDetailResponse?>, t: Throwable) {

            }
        })
    }
}

