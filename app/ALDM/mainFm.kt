package ALDM

import ALDM.Adapter.dtAdapter
import ALDM.ViewModel.MainViewModel
import ALDM.daata.Daata
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.app.Application
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.fragment.app.viewModels

import androidx.lifecycle.ViewModel

import ALDM.databinding.FragmentMainBinding
import android.annotation.SuppressLint
import android.app.Activity
import android.provider.SyncStateContract.Helpers.update
import androidx.recyclerview.widget.DividerItemDecoration


class mainFm : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var alarmAdapter: dtAdapter
    private val viewModel: MainViewModel by viewModels{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T = activity?.application?.let { MainViewModel(it) } as T
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
        }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdd.setOnClickListener() {

            Navigation.findNavController(binding.root)
                .navigate(R.id.action_mainFm_to_mainActivityinfit)
        }
        binding.btndn.setOnClickListener() {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_mainFm_to_demnguoc)
        }
        alarmAdapter = dtAdapter ({
            //callback o day de co the lay dc context truyen vao ham huybt va ham suabt
            if (it.isOn) {
                activity?.let { it1 -> it.huybt(it1 as Context) }

                viewModel.update(entity = it)
            } else {
                activity?.let { it1 -> it.suabt(it1 as Context) }

                viewModel.update(entity = it)
            }},viewModel, activity!!)

        viewModel.listLiveDaata.observe(viewLifecycleOwner, {
            alarmAdapter.listAlarm = it
            alarmAdapter.notifyDataSetChanged()


        })

        //set up recycler view
        var itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        //itemDecoration.setDrawable(context.getDrawable(R.drawable.line_divider))
        val recyclerView:RecyclerView = binding.dataList
        recyclerView.apply {
            adapter = alarmAdapter
            layoutManager = LinearLayoutManager(context)
            this.addItemDecoration(itemDecoration)
        }
    }
}