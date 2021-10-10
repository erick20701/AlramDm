package ALDM.daata


import ALDM.Receiver.tReceiver
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey

import java.io.Serializable
import java.util.*

@Entity(tableName = "daata")

data class Daata(
    var gio:Int,
    var phut: Int,

    var bat: Boolean,
    var thu2: Boolean,
    var thu3: Boolean,
    var thu4: Boolean,
    var thu5: Boolean,
    var thu6: Boolean,
    var thu7: Boolean,
    var chuN: Boolean,
    var kt: String,
    var isOn: Boolean, // de set trang thai cho switch
    var timeCreated : Long, // dung de lay gio bao thuc, sort trong ds theo gio
    @PrimaryKey var mId: Long

) : Serializable {

    fun suabt(context: Context) {
        // dung alarmManager de gui 1 broadcast tu he thong alarm cua dien thoai
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //khoi tao 1 intent de gui broadcast do den receiver va xu li trong do
        val intent= Intent(context, tReceiver::class.java)
        intent.putExtra("GIO",gio)
        intent.putExtra("PHUT",phut)
        intent.putExtra("BAT",bat)
        intent.putExtra("T2", this.thu2)
        intent.putExtra("T3", this.thu3)
        intent.putExtra("T4", this.thu4)
        intent.putExtra("T5", this.thu5)
        intent.putExtra("T6", this.thu6)
        intent.putExtra("T7", this.thu7)
        intent.putExtra("CN", this.chuN)
        intent.putExtra("DAY",kt)
        intent.putExtra("MID",mId)
        val pendingIntent = mId.let { PendingIntent.getBroadcast(context, it.toInt(), intent, 0) };

        // lay thoi tu time picker
        var calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis();
        calendar.set(Calendar.HOUR_OF_DAY,gio)
        calendar.set(Calendar.MINUTE,phut)
        calendar.set(Calendar.SECOND,0)

        // neu thoi gian hien tai lon hon thoi gian dat bao thuc => tang ngay len 1
        if(calendar.timeInMillis <= System.currentTimeMillis()){
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
        }

        // tao bao thuc tren dien thoai
        if(!bat){
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
        else{
            val daily: Long = 24*60*60*1000
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                daily,
                pendingIntent
            )
        }
        this.isOn = true
    }

    fun huybt(context: Context) {
        var alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, tReceiver::class.java)
        val pendingIntent = mId.let { PendingIntent.getBroadcast(context, it.toInt(),intent,0) }

        alarmManager.cancel(pendingIntent)
        this.isOn = false
    }
    fun getDays():String{
        var satD =""
        if(thu2) satD +="T2 "
        if(thu3) satD +="T3 "
        if(thu4) satD +="T4 "
        if(thu5) satD +="T5 "
        if(thu6) satD +="T6 "
        if(thu7) satD +="T7 "
        if(chuN) satD +="CN "
        if (satD .length==0) {
            isOn = false
            satD  = "No Day No Ring"
        }
        return satD
    }
}