package com.firstapp.datatracker

import android.Manifest
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
import dev.jahidhasanco.networkusage.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataUsagesAdapter: DataAdapter
    private var usagesDataList = ArrayList<DataModel>()
    private val JOB_ID = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ProfilePreferences.init(this)

        setupPermissions()

        startService(Intent(this, NetworkMonitorService::class.java))

        val networkUsage = NetworkUsageManager(this, Util.getSubscriberId(this))

        val handler = Handler(Looper.getMainLooper())
        val runnableCode = object : Runnable {
            override fun run() {
                val now = networkUsage.getUsageNow(NetworkType.ALL)
                val speeds = NetSpeed.calculateSpeed(now.timeTaken, now.downloads, now.uploads)
                val todayM = networkUsage.getUsage(Interval.today, NetworkType.MOBILE)

                binding.dataUsagesTv.text =
                    "مصرف موبایل امروز: " + Util.formatData(todayM.downloads, todayM.uploads)[2]
//                binding.totalSpeedTv.text = speeds[0].speed + "\n" + speeds[0].unit
                binding.downUsagesTv.text = "سرعت دانلود: " + speeds[1].speed + speeds[1].unit
                binding.upUsagesTv.text = "سرعت آپلود: " + speeds[2].speed + speeds[2].unit

                handler.postDelayed(this, 500)
            }
        }

        runnableCode.run()

//        val last30DaysWIFI = networkUsage.getMultiUsage(
//            Interval.lastMonthDaily, NetworkType.WIFI
//        )

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

//        val last7DaysTotalWIFI = networkUsage.getUsage(
//            Interval.last7days, NetworkType.WIFI
//        )

        val last7DaysTotalMobile = networkUsage.getUsage(
            Interval.last7days, NetworkType.MOBILE
        )

//        val last30DaysTotalWIFI = networkUsage.getUsage(
//            Interval.last30days, NetworkType.WIFI
//        )


        usagesDataList.add(
            DataModel(
                Util.formatData(
                    last7DaysTotalMobile.downloads,
                    last7DaysTotalMobile.uploads
                )[2],
                "Last 7 Days"
            )
        )

//        binding.wifiDataThisMonth.text = Util.formatData(
//            last30DaysTotalWIFI.downloads,
//            last30DaysTotalWIFI.uploads
//        )[2]
        val monthRunnable = object : Runnable {
            override fun run() {
                val last30DaysTotalMobile = networkUsage.getUsage(
                    Interval.last30days, NetworkType.MOBILE
                )
                binding.mobileDataThisMonth.text = Util.formatData(
                    last30DaysTotalMobile.downloads,
                    last30DaysTotalMobile.uploads
                )[2]

                handler.postDelayed(this, 1500)
            }
        }
        monthRunnable.run()

        dataUsagesAdapter = DataAdapter(usagesDataList)
        binding.monthlyDataUsagesRv.layoutManager = LinearLayoutManager(this)
        binding.monthlyDataUsagesRv.setHasFixedSize(true)
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
        var mode: Int = appOps.checkOpNoThrow(
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


}