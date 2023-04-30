package com.example.cardealershipkt.presentation.SearchPack

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardealershipkt.presentation.MainViewModel
import com.example.cardealershipkt.data.Room.CarItem
import com.example.cardealershipkt.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var data: List<CarItem>
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var sort = false
    private var sortA = false
    private val adapter: SearchAdapter = SearchAdapter()

    private val viewModel: MainViewModel by activityViewModels()

    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = adapter.items
        val layoutManager = LinearLayoutManager(context)
        binding.rvCars.layoutManager = layoutManager
        binding.rvCars.setHasFixedSize(true)
        binding.rvCars.adapter = adapter
        adapter.notifyDataSetChanged()

        binding.btnSort1.setOnClickListener(View.OnClickListener {
            if(!sort){
                adapter.items =  adapter.items.sortedBy { it.price }
                adapter.notifyDataSetChanged()
                sort = true
            }else {
                adapter.items =  adapter.items.sortedBy { it.price }.reversed()
                adapter.notifyDataSetChanged()
                sort = false
            }

        })
        binding.btnSort3.setOnClickListener(View.OnClickListener {
            if(!sortA){
                adapter.items =  adapter.items.sortedBy { it.brand }
                adapter.notifyDataSetChanged()
                sortA = true
            }else {
                adapter.items =  adapter.items.sortedBy { it.brand }.reversed()
                adapter.notifyDataSetChanged()
                sortA = false
            }
        })

        viewModel.carListLiveData.observe(viewLifecycleOwner) { cars ->
            if(cars.isNotEmpty()){
                binding.ProgressBar.visibility = View.GONE
            }
            adapter.items = cars
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshCars()
    }
}