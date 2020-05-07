package com.lucas_charity.covid19.activity.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.lucas_charity.covid19.R
import com.lucas_charity.covid19.models.FoodRequest
import java.text.SimpleDateFormat
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HistoryAdapter(private val context: Context,
                     private val foodRequests: MutableList<FoodRequest>):
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private val SECOND_MILLIS = 1000
    private val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private val DAY_MILLIS = 24 * HOUR_MILLIS

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.history_row, parent, false)
        return HistoryViewHolder(v)
    }

    override fun getItemCount(): Int {
        return foodRequests.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val foodRequest = foodRequests[position]
        holder.fullName.text = foodRequest.fullName
        holder.phoneNumber.text = foodRequest.phoneNumber
        holder.address.text = foodRequest.address
        holder.date.text = "${foodRequest.date} on ${foodRequest.time}"
        holder.createdAt.text = "created on: ${getCreatedAtTime(foodRequest.createdAt)}"
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCreatedAtTime(createdAt: String?): String {

        val date = SimpleDateFormat("yyyy-MM-dd").parse(createdAt)
        var time = date.time
        if (time < 1000000000000L) {
            time *= 1000
        }

        val now = currentDate().time
        if (time > now || time <= 0) {
            return "in the future"
        }

        val diff = now - time
        return when {
            diff < MINUTE_MILLIS -> "moments ago"
            diff < 2 * MINUTE_MILLIS -> "a minute ago"
            diff < 60 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} minutes ago"
            diff < 2 * HOUR_MILLIS -> "an hour ago"
            diff < 24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS} hours ago"
            diff < 48 * HOUR_MILLIS -> "yesterday"
            else -> "${diff / DAY_MILLIS} days ago"
        }
    }

    private fun currentDate(): Date {
        val calendar = Calendar.getInstance()
        return calendar.time
    }

    class HistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.full_name)
        lateinit var fullName: TextView

        @BindView(R.id.phone_number)
        lateinit var phoneNumber: TextView

        @BindView(R.id.date)
        lateinit var date: TextView

        @BindView(R.id.address)
        lateinit var address: TextView

        @BindView(R.id.created_at)
        lateinit var createdAt: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

    }

}