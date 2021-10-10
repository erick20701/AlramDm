package ALDM.ViewModel

import ALDM.daata.Daata
import ALDM.daata.Daatabase
import ALDM.daata.tRepository
import android.app.Application
import android.provider.SyncStateContract.Helpers.insert
import androidx.lifecycle.LiveData
import android.util.Log
import androidx.lifecycle.*

import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : ViewModel() {
    private val alarmDao = Daatabase.getInstance(app).timeDao()
    private var timeRepository: tRepository = tRepository(alarmDao)
    var listLiveDaata : LiveData<List<Daata>> = timeRepository.getDaata

    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            listLiveDaata.postValue(timeRepository.get())
//        }
    }

    suspend fun insert(entity: Daata) {

        viewModelScope.launch(Dispatchers.IO){
            timeRepository.insert(entity)
        }.join()
    }

    fun update(entity: Daata) {
        viewModelScope.launch(Dispatchers.IO) {
            timeRepository.update(entity)
        }

    }

    fun delete(entity: Daata) {
        viewModelScope.launch(Dispatchers.IO) {
            timeRepository.delete(entity)
        }
    }
}