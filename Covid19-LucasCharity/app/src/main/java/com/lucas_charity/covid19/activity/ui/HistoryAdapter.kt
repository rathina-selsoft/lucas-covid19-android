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

class HistoryAdapter(private val context: Context,
    private val foodRequests: MutableList<FoodRequest>):
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

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

        init {
            ButterKnife.bind(this, itemView)
        }

    }

}