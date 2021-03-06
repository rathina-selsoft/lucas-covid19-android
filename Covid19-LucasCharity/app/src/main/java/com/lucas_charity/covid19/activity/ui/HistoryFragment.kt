package com.lucas_charity.covid19.activity.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lucas_charity.covid19.MainActivity
import com.lucas_charity.covid19.R
import com.lucas_charity.covid19.activity.MainNavActivity
import com.lucas_charity.covid19.models.DateRequest
import com.lucas_charity.covid19.models.FoodRequest
import com.lucas_charity.covid19.models.FoodRequestResponse
import com.lucas_charity.covid19.remote.Api
import com.lucas_charity.covid19.remote.RetrofitEngine
import com.lucas_charity.covid19.utils.Utils
import kotlinx.android.synthetic.main.activity_food_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : Fragment() {

    @BindView(R.id.history_available)
    lateinit var historyAvailableTxt: TextView

    @BindView(R.id.rv_history)
    lateinit var historyRV: RecyclerView

    @BindView(R.id.filter_history)
    lateinit var filterFab: FloatingActionButton

    lateinit var dateCalendar: Calendar
    lateinit var fromDateCalendar: Calendar
    lateinit var toDateCalendar: Calendar

    private val dateFormat = "yyyy-MM-dd"
    lateinit var mainActivity: MainNavActivity
    var foodRequests: MutableList<FoodRequest> = ArrayList<FoodRequest>()
    lateinit var historyAdapter: HistoryAdapter
    private val showDateFormat = "MMM/dd/yyyy"

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        ButterKnife.bind(this, root)

        dateCalendar = Calendar.getInstance()

        val dateRequest = DateRequest()
        dateRequest.userId = Utils.user?.userId
        dateRequest.fromDate = SimpleDateFormat(dateFormat).format(dateCalendar.time)
        dateRequest.toDate = SimpleDateFormat(dateFormat).format(dateCalendar.time)

        getFoodHistory(dateRequest)

        historyRV.layoutManager =
            LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)

        historyAdapter = HistoryAdapter(mainActivity, foodRequests)
        historyRV.adapter = historyAdapter

        getHistoryViews()
        return root
    }

    private fun getFoodHistory(dateRequest: DateRequest) {
        foodRequests.clear()
        val api: Api = RetrofitEngine.getClient
        val call: Call<FoodRequestResponse> = api.foodRequestHistory(dateRequest)

        call.enqueue(object : Callback<FoodRequestResponse?> {
            override fun onResponse(
                call: Call<FoodRequestResponse?>,
                response: Response<FoodRequestResponse?>
            ) {
                if (response.isSuccessful) {
                    val foodDetailResponse = response.body()
                    if (foodDetailResponse?.success!!) {
                        foodRequests.addAll(foodDetailResponse.foodRequests!!)
                        historyAdapter.notifyDataSetChanged()
                        getHistoryViews()
                    } else {
                        val toast = Utils.showToast(
                            mainActivity,
                            foodDetailResponse.message!!
                        )
                        toast.show()
                    }
                }
            }

            override fun onFailure(call: Call<FoodRequestResponse?>, t: Throwable) {

            }
        })
    }

    @SuppressLint("SimpleDateFormat")
    @OnClick(R.id.filter_history)
    fun onFilterTapped(view: View) {
        val filterDialog = AlertDialog.Builder(mainActivity)
        val filterView = this.layoutInflater.inflate(R.layout.filter_view, null)
        filterDialog.setView(filterView)
        val filterByDateDialog: AlertDialog = filterDialog.create()

        val fromDate: TextView = filterView.findViewById(R.id.from_date) as TextView
        val toDate: TextView = filterView.findViewById(R.id.to_date) as TextView
        val filterBtn: Button = filterView.findViewById(R.id.filter_btn) as Button

        fromDateCalendar = Calendar.getInstance()
        toDateCalendar = Calendar.getInstance()

        fromDate.text = SimpleDateFormat(showDateFormat).format(fromDateCalendar.time)
        toDate.text = SimpleDateFormat(showDateFormat).format(toDateCalendar.time)

        fromDate.setOnClickListener {
            chooseDateTapped(fromDate, fromDateCalendar)
            fromDate.text = SimpleDateFormat(showDateFormat).format(fromDateCalendar.time)
        }

        toDate.setOnClickListener {
            chooseDateTapped(toDate, toDateCalendar)
            toDate.text = SimpleDateFormat(showDateFormat).format(toDateCalendar.time)
        }

        filterBtn.setOnClickListener {
            val dateRequest = DateRequest()
            dateRequest.userId = Utils.user?.userId
            dateRequest.fromDate = SimpleDateFormat(dateFormat).format(fromDateCalendar.time)
            dateRequest.toDate = SimpleDateFormat(dateFormat).format(toDateCalendar.time)

            getFoodHistory(dateRequest)
            filterByDateDialog.dismiss()
        }

        filterByDateDialog.show()
    }

    private fun chooseDateTapped(tv: TextView, viewDateCalendar: Calendar) {
        DatePickerDialog(
            mainActivity,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                viewDateCalendar.set(Calendar.YEAR, year)
                viewDateCalendar.set(Calendar.MONTH, monthOfYear)
                viewDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val sdf = SimpleDateFormat(showDateFormat, Locale.US)
                tv.text = sdf.format(viewDateCalendar.time)
            },
            viewDateCalendar.get(Calendar.YEAR),
            viewDateCalendar.get(Calendar.MONTH),
            viewDateCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun getHistoryViews() {
        if (foodRequests.size == 0) {
            historyAvailableTxt.visibility = View.VISIBLE
            historyRV.visibility = View.GONE
        } else {
            historyAvailableTxt.visibility = View.GONE
            historyRV.visibility = View.VISIBLE
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainNavActivity
    }
}
