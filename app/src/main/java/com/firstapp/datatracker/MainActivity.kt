package com.firstapp.datatracker

import android.Manifest
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.firstapp.datatracker.databinding.ActivityMainBinding
import dev.jahidhasanco.networkusage.Interval
import dev.jahidhasanco.networkusage.NetSpeed
import dev.jahidhasanco.networkusage.NetworkType
import dev.jahidhasanco.networkusage.NetworkUsageManager
import dev.jahidhasanco.networkusage.Util

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataUsagesAdapter: DataAdapter
    private var usagesDataList = ArrayList<DataModel>()
    private lateinit var networkUsage: NetworkUsageManager
    private var message = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ProfilePreferences.init(this)

        setupPermissions()

        startService(Intent(this, NetworkMonitorService::class.java))

        binding.limitButton.setOnClickListener {
            startActivity(Intent(this, SetLimitActivity::class.java))
        }

        binding.graphButton.setOnClickListener {
            startActivity(Intent(this, ChartActivity::class.java))
        }

        binding.dataUsagesTv.setOnClickListener {
            Toast.makeText(this, message, Toast.LENGTH_LONG)
                .show()
        }

        binding.monthlyDataUsagesRv.layoutManager = LinearLayoutManager(this)
        binding.monthlyDataUsagesRv.setHasFixedSize(true)

        networkUsage = NetworkUsageManager(this, Util.getSubscriberId(this))

        val lastMonth = networkUsage.getUsage(Interval.last30days, NetworkType.MOBILE)
        val mean = (
                lastMonth.downloads +
                        lastMonth.uploads
                ) / 30 / 1024 / 1024

        val handler = Handler(Looper.getMainLooper())

        val runnableCode = object : Runnable {
            override fun run() {
                val now = networkUsage.getUsageNow(NetworkType.ALL)
                val speeds = NetSpeed.calculateSpeed(now.timeTaken, now.downloads, now.uploads)
                val todayM = networkUsage.getUsage(Interval.today, NetworkType.MOBILE)
                binding.dataUsagesTv.text = Util.formatData(todayM.downloads, todayM.uploads)[2]
                binding.downUsagesTv.text = speeds[1].speed + " " + speeds[1].unit
                binding.upUsagesTv.text = speeds[2].speed + " " + speeds[2].unit

                val threshold = 0.1 // 10 درصد
                val difference = (todayM.downloads + todayM.uploads).toFloat() / 1024 / 1024 - mean
                val percentage = (difference / mean) * 100
                when {
                    percentage < -threshold -> {
                        binding.dataUsagesTv.setTextColor(Color.parseColor("#00FF00"))
                        message = "مصرف دیتای کم: کم تر از میانگین 30 روز اخیر"
                    }
                    percentage > threshold -> {
                        binding.dataUsagesTv.setTextColor(Color.parseColor("#FC0729"))
                        message = "مصرف دیتای بالا: بیشتر از میانگین 30 روز اخیر"
                    }
                    else -> {
                        binding.dataUsagesTv.setTextColor(Color.parseColor("#FE981A"))
                        message = "مصرف دیتای متوسط: در حد میانگین 30 روز اخیر"
                    }
                }

                val last30DaysTotalMobile = networkUsage.getUsage(
                    Interval.last30days, NetworkType.MOBILE
                )
                binding.mobileDataThisMonth.text = Util.formatData(
                    last30DaysTotalMobile.downloads,
                    last30DaysTotalMobile.uploads
                )[2]

                handler.postDelayed(this, 1000)
            }
        }
        runnableCode.run()

    }

    private fun collectData() {
        usagesDataList.clear()

        val last30DaysMobile = networkUsage.getMultiUsage(
            Interval.lastMonthDaily, NetworkType.MOBILE
        )
        for (i in last30DaysMobile.indices) {
            usagesDataList.add(
                DataModel(
                    Util.formatData(
                        last30DaysMobile[i].downloads,
                        last30DaysMobile[i].uploads
                    )[2],
                    last30DaysMobile[i].date
                )
            )
        }

        val last7DaysTotalMobile = networkUsage.getUsage(
            Interval.last7days, NetworkType.MOBILE
        )
        usagesDataList.add(
            DataModel(
                Util.formatData(
                    last7DaysTotalMobile.downloads,
                    last7DaysTotalMobile.uploads
                )[2],
                "Last 7 Days"
            )
        )

        val lastMonth = networkUsage.getUsage(Interval.last30days, NetworkType.MOBILE)
        val mean = (
                lastMonth.downloads +
                        lastMonth.uploads
                ) / 30 / 1024 / 1024

        val todayM = networkUsage.getUsage(Interval.today, NetworkType.MOBILE)

        val threshold = 0.1 // 10 درصد
        val difference = (todayM.downloads + todayM.uploads).toFloat() / 1024 / 1024 - mean
        val percentage = (difference / mean) * 100
        when {
            percentage < -threshold -> {
                binding.dataUsagesTv.setTextColor(Color.parseColor("#00FF00"))
            }
            percentage > threshold -> {
                binding.dataUsagesTv.setTextColor(Color.parseColor("#FC0729"))
            }
            else -> {
                binding.dataUsagesTv.setTextColor(Color.parseColor("#FE981A"))
            }
        }

        dataUsagesAdapter = DataAdapter(usagesDataList)
        binding.monthlyDataUsagesRv.adapter = dataUsagesAdapter
    }


    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_PHONE_STATE
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_PHONE_STATE), 34
            )
        }

        if (!checkUsagePermission()) {
            Toast.makeText(this, "لطفا دسترسی مصرف اینترنت را تایید کنید", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun checkUsagePermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode: Int = appOps.checkOpNoThrow(
            "android:get_usage_stats", Process.myUid(),
            packageName

        )
        val granted = mode == AppOpsManager.MODE_ALLOWED
        if (!granted) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
            return false
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        collectData()
    }

}