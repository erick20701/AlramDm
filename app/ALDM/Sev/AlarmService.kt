package ALDM.Sev

import ALDM.R
import ALDM.Thongbao
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import android.util.Log
import notifications.AppNoti.Companion.CHANNEL_ID


class AlarmService : Service() {
    private lateinit var mediaPlayer: MediaPlayer // nhac bao thuc
    private lateinit var vibrator: Vibrator // rung cho bao thuc


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)
        mediaPlayer.isLooping = true
        vibrator =  getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    }
    // ham nay nhan intent tu receiver khi start 1 service, khi den gio bao thuc thi se chay vao ham nay
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val notiIntent = Intent(this, Thongbao::class.java) // goi den class Thongbao
        val alarmLabel: String? = intent.getStringExtra("DAY") //label of notification
        notiIntent.putExtra("HOUR", intent.getIntExtra("HOUR",0))
        notiIntent.putExtra("MINUTE", intent.getIntExtra("MINUTE",0))
        notiIntent.putExtra("DAY",alarmLabel)
        intent.getStringExtra("LABEL")?.let { Log.e("Label service", it)}
        val pendingIntent = PendingIntent.getActivity(this,0,notiIntent,0) // dung de goi 1 ung dung ben ngoai(thong bao cua dien thoai)
        val notification = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("ALARMRING!!!!!")
            .setSmallIcon(R.drawable.btnbtt1)
            .setContentIntent(pendingIntent)
            .build()
        mediaPlayer.start()
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
        startForeground(1,notification)
        return START_STICKY
    }

    override fun onDestroy() {
        // khi stop service se chay vao ham nay
        super.onDestroy()
        mediaPlayer.stop()
        vibrator.cancel()

    }
}