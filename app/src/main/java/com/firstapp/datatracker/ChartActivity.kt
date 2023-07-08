package com.firstapp.datatracker

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.firstapp.datatracker.databinding.ActivityChartBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dev.jahidhasanco.networkusage.Interval
import dev.jahidhasanco.networkusage.NetworkType
import dev.jahidhasanco.networkusage.NetworkUsageManager
import dev.jahidhasanco.networkusage.Util

class ChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chart)

        binding.backButton.setOnClickListener {
            finish()
        }

        val networkUsage = NetworkUsageManager(this, Util.getSubscriberId(this))

        val usagesDates = mutableListOf<String>()
        val usages = mutableListOf<Float>()

        val last30DaysMobile = networkUsage.getMultiUsage(
            Interval.lastWeekDaily, NetworkType.MOBILE
        )
        for (i in last30DaysMobile.indices) {
            usagesDates.add(
                last30DaysMobile[i].date.split(",")[0]
            )
            usages.add(
                (last30DaysMobile[i].downloads + last30DaysMobile[i].uploads).toFloat()/1024/1024
            )
        }

        val entries = mutableListOf<BarEntry>()
        for (i in usages.indices) {
            entries.add(BarEntry(i.toFloat(), usages[i]))
        }

        val barDataSet = BarDataSet(entries, "حجم (مگابایت)")
        barDataSet.color = Color.parseColor("#AB47BC")

        val xAxis = binding.chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(usagesDates)

        val data = BarData(barDataSet)
        binding.chart.data = data
        binding.chart.invalidate()
    }
}