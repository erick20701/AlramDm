package ALDM

import ALDM.Sev.AlarmService
import ALDM.daata.Daata
import ALDM.databinding.ThongbaoBinding
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Thongbao : AppCompatActivity() {
    private lateinit var binding: ThongbaoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ThongbaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(applicationContext, AlarmService::class.java)

        initTime()

        // onClick
        binding.btnStop.setOnClickListener { // if stop, stop service => stop Alarm
            //val intent = Intent(applicationContext, AlarmService::class.java)
            applicationContext.stopService(intent)
            finish() // tat activity
        }

    }

    private fun initTime() {
        val alarmHour = intent.getIntExtra("HOUR",0)
        val alarmMinute = intent.getIntExtra("MINUTE",0)
        val alarmTime = "$alarmHour : $alarmMinute"
        binding.txtALarmTime.text = alarmTime


    }
}