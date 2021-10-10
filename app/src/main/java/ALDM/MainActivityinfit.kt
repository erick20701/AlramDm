package ALDM

import ALDM.ViewModel.MainViewModel
import ALDM.daata.Daata
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

import ALDM.databinding.ActivityMainActivityinfitBinding
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class MainActivityinfit : Fragment() {
    private lateinit var binding: ActivityMainActivityinfitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val alarmListViewModel:MainViewModel by viewModels{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T = activity?.application?.let { MainViewModel(it) } as T
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = ActivityMainActivityinfitBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnC.setOnClickListener { //tao 1 bao thuc moi
          createAlarm()
            Navigation.findNavController(view).navigate(R.id.action_mainActivityinfit_to_mainFm2)
        }
    }
    private fun createAlarm(){
        var std = ""
//        if (binding.cbt2.isChecked)  std+=" T2"
//        if (binding.cbt3.isChecked)  std+=" T3"
//        if (binding.cbt4.isChecked)  std+=" T4"
//        if (binding.cbt5.isChecked)  std+=" T5"
//        if (binding.cbt6.isChecked)  std+=" T6"
//        if (binding.cbt7.isChecked)  std+=" T7"
//        if (binding.cbcn.isChecked)  std+=" CN"
//        if (std.length==0) std = "No Day No Ring"
        var alarm = Daata(binding.timepicker.hour,
            binding.timepicker.minute,
            true,
            binding.cbt2.isChecked,
            binding.cbt3.isChecked,
            binding.cbt4.isChecked,
            binding.cbt5.isChecked,
            binding.cbt6.isChecked,
            binding.cbt7.isChecked,
            binding.cbcn.isChecked,
            std,
            true,
            System.currentTimeMillis(),
            System.currentTimeMillis()
        )
        lifecycleScope.launch(Dispatchers.IO) {
            alarmListViewModel.insert(alarm)
        }
        activity?.let { alarm.suabt(it as Context) }

    }
}