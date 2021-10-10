package ALDM.Sev

import android.app.Service
import android.app.Service.START_STICKY
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.launch


internal class tService : Service(), LifecycleOwner {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }
    override fun getLifecycle(): Lifecycle {
        TODO("Not yet implemented")
    }

}