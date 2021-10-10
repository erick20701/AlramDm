package ALDM.Receiver

import ALDM.Sev.AlarmService
import ALDM.Sev.tService

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import java.util.*

class tReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            //khi tat nguon dt, tat ca service se bi mat, vi vay can check khi nao dt dc bat tro lai, khi do broadcast ACTION_BOOT_COMPLETED se duoc gui vao receiver,
            //ta co the dung broadcast do de bat lai cac bao thuc truoc do tu Room
            if(Intent.ACTION_BOOT_COMPLETED == intent.action){
                Toast.makeText(context,"Alarm is rescheduled", Toast.LENGTH_LONG).show()
                start(context)
            }
            else startAlarmService(context,intent)
        }

    private fun start(context: Context){
        var intentService = Intent(context, tService::class.java)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            context.startForegroundService(intentService)
        }
        else{
            context.startService(intentService)
        }
    }

    private fun startAlarmService(context: Context, intent: Intent){

        var intentService = Intent(context, AlarmService::class.java)
       intentService.putExtra("HOUR",intent.getIntExtra("HOUR",0))

        intentService.putExtra("MINUTE",intent.getIntExtra("MINUTE",0))

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            context.startForegroundService(intentService)
        }
        else{
            context.startService(intentService)
        }
    }

}