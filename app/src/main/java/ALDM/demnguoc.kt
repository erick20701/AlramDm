package ALDM

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

import ALDM.databinding.DemnguocBinding


class demnguoc : Fragment() {
    private lateinit var binding: DemnguocBinding
    private var start: Long = 0
    private var countDown: CountDownTimer? = null
    private var run: Boolean = true
    private var stop: Boolean = false
    private var endTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DemnguocBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.timeCountdown.setIs24HourView(true)

        binding.timeCountdown.hour = 0
        binding.timeCountdown.minute = 5
        binding.btnbt.setOnClickListener(){
            Navigation.findNavController(binding.root).navigate(R.id.action_demnguoc2_to_mainFm)
        }
        binding.btnPlay.setOnClickListener(){
            val h =binding.timeCountdown.hour
            val m = binding.timeCountdown.minute
            try {
                start = ((h*60 + m )*1000).toLong()
                hideMyKeyBoard()
                startTime()
            }
            catch (e: Exception){
            }

        }
        binding.btnPause.setOnClickListener(){
            pauseTime()
        }
        binding.btnResume.setOnClickListener(){
            startTime()
        }
        binding.btnStop.setOnClickListener(){
            stopTime()
        }
    }
    private fun hideMyKeyBoard(){
        if(view!= null){
            val hideMe = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(requireView().windowToken,0)
        }
    }


    private fun startTime() {
        endTime = System.currentTimeMillis() + start
        countDown = object :CountDownTimer(start,1000){
            override fun onTick(millisUntilFinished: Long) {
                start = millisUntilFinished
                updateCountDownText();
            }

            override fun onFinish() {
                stop = true
                run= false
                updateButtons()
            }


        }.start()
        run = true
        updateButtons()

    }

    private fun pauseTime() {
        run = false
        updateButtons()
    }

    private fun stopTime(){
        try{
            stop = true
            run = false
            updateButtons()
            countDown?.cancel()
        }
        catch (e: Exception){

        }
    }

    private fun updateButtons() {
        if (run) {
            binding.btnResume.visibility = View.GONE
            binding.btnPlay.visibility = View.GONE
            binding.timeCountdown.visibility = View.GONE
            binding.btnStop.visibility = View.VISIBLE
            binding.btnPause.visibility = View.VISIBLE
            binding.timeLeft.visibility = View.VISIBLE
        } else {
            if (stop || start < 1000) {
                stop = false
                binding.btnResume.visibility = View.GONE
                binding.btnPlay.visibility = View.VISIBLE
                binding.timeCountdown.visibility = View.VISIBLE
                binding.btnStop.visibility = View.GONE
                binding.btnPause.visibility = View.GONE
                binding.timeLeft.visibility = View.GONE
                binding.timeCountdown.hour = 0
                binding.timeCountdown.minute = 5
            } else {
                countDown?.cancel()
                binding.btnPause.visibility = View.GONE
                binding.btnResume.visibility = View.VISIBLE
            }
        }
    }

    private fun updateCountDownText(){
        val minutes = ((start/1000)/60).toInt()
        val seconds = ((start/1000)%60).toInt()
        val timeFormat = String.format("%02d:%02d", minutes,seconds)
        binding.timeLeft.text = timeFormat
    }

    override fun onStop() {
        super.onStop()
        val prefs = requireContext().getSharedPreferences("prefs", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putLong("millisLeft",start)
        editor.putBoolean("timerRunning",run)
        editor.putLong("endTime",endTime)
        editor.apply()
    }
    override fun onStart() {
        super.onStart()
        val prefs = requireContext().getSharedPreferences("prefs", MODE_PRIVATE)
        start =  prefs.getLong("millisLeft",0)
        run = prefs.getBoolean("timerRunning",false)
        if (run){
            endTime = prefs.getLong("endTime",0)
            start = endTime - System.currentTimeMillis()
            if(start<0){
                start = 0
                run = false
                updateCountDownText()
                updateButtons()
            }
            else {
                startTime()
            }
        }

    }

}