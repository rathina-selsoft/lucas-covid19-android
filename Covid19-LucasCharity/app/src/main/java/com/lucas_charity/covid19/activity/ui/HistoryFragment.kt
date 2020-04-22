package com.lucas_charity.covid19.activity.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lucas_charity.covid19.R
import com.lucas_charity.covid19.utils.Utils
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
    private val dateFormat = "yyyy-MM-dd"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        ButterKnife.bind(this, root)

        dateCalendar = Calendar.getInstance()

        val sdf = SimpleDateFormat(dateFormat, Locale.US)
        Utils.log(sdf.format(dateCalendar.time))
        return root
    }

    @OnClick(R.id.filter_history)
    fun onFilterTapped(view: View) {

    }
}
