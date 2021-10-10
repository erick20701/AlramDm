package ALDM.daata

import androidx.lifecycle.LiveData



class tRepository (private var alarmDao: tDao) {


    // suspend fun getData() : LiveData<List<Alarm>> = alarmDao.getAlarms()

    var getDaata: LiveData<List<Daata>> = alarmDao.getDa()
    //suspend fun get() = tDao.get()
    suspend fun insert(daata: Daata){
        alarmDao.insert(daata)
    }
    suspend fun update(daata: Daata){
        alarmDao.update(daata)
    }
    suspend fun delete(daata: Daata){
        alarmDao.delete(daata)
    }

}