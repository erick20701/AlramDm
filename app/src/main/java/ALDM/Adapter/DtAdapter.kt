package ALDM.Adapter

import ALDM.R
import ALDM.ViewModel.MainViewModel
import ALDM.daata.Daata
import ALDM.databinding.ItemfrBinding
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper


class dtAdapter(val onToggle:(Daata)->Unit, var viewModel: MainViewModel, var context: Context): RecyclerView.Adapter<dtAdapter.AlarmViewHolder>() {
    var listAlarm: List<Daata> = mutableListOf()
    private var viewBinderHelper = ViewBinderHelper()

    inner class AlarmViewHolder(private var binding: ItemfrBinding): RecyclerView.ViewHolder(binding.root) {
        val itemLayout = binding.item
        val layoutDelete = binding.layoutDelete
        @SuppressLint("SetTextI18n")
        fun bind(alarm: Daata){
            with(binding){
                if(alarm.phut < 10) textGio.text = "${alarm.gio}:${"0"+ alarm.phut}"
                else textGio.text = "${alarm.gio}:${alarm.phut}"
                textDay.text = alarm.getDays()
                binding.btsw.isChecked = alarm.isOn

                binding.btsw.setOnCheckedChangeListener { _: CompoundButton, bool: Boolean ->
                    onToggle(alarm)
                    //alarm.isOn = bool
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        var binding =ItemfrBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AlarmViewHolder(binding)
    }

    override fun onBindViewHolder( holder: dtAdapter.AlarmViewHolder, position: Int) {
        val alarm = listAlarm[position]
        holder.apply {
            bind(alarm)

        }
        viewBinderHelper.bind(holder.itemLayout , alarm.mId.toString())
        holder.layoutDelete.setOnClickListener {
            alarm.huybt(context)
            viewModel.delete(alarm)
        }
    }

    override fun getItemCount(): Int {
        return listAlarm.size
    }

}