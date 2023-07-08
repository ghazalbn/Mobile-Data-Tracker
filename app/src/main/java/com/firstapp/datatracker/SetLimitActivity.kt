package com.firstapp.datatracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.firstapp.datatracker.databinding.ActivitySetLimitBinding


class SetLimitActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetLimitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_limit)

        binding.currentLimit.text = "مقدار فعلی محدودیت: " + (ProfilePreferences.getLimit()/1024/1024).toString() + "مگابایت"

        binding.submitButton.setOnClickListener {
            if (!binding.editText.text.toString().isNullOrEmpty()){
                ProfilePreferences.setLimit(binding.editText.text.toString().toLong() * 1024 * 1024)
                ProfilePreferences.setNotification(false)
                finish()
            }
            else{
                binding.editText.error = "این فیلد نباید خالی باشد"
            }
        }

    }
}