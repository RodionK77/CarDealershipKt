package com.example.cardealershipkt.presentation.SearchPack

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardealershipkt.presentation.ViewModel.MainViewModel
import com.example.cardealershipkt.data.Room.CarItem
import com.example.cardealershipkt.databinding.FragmentSearchBinding
import com.example.cardealershipkt.presentation.ViewModel.MainViewModelFactory

class SearchFragment : Fragment() {

    private lateinit var data: List<CarItem>
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var sort = false
    private var sortA = false
    private val adapter: SearchAdapter = SearchAdapter()

    private val viewModel: MainViewModel by activityViewModels{ MainViewModelFactory(requireContext()) }

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
                adapter.setItemsList(adapter.items.sortedBy { it.price })
                //adapter.items =  adapter.items.sortedBy { it.price }
                adapter.notifyDataSetChanged()
                sort = true
            }else {
                adapter.setItemsList(adapter.items.sortedBy { it.price }.reversed())
                //adapter.items =  adapter.items.sortedBy { it.price }.reversed()
                adapter.notifyDataSetChanged()
                sort = false
            }

        })
        binding.btnSort3.setOnClickListener(View.OnClickListener {
            if(!sortA){
                adapter.setItemsList(adapter.items.sortedBy { it.brand })
                //adapter.items =  adapter.items.sortedBy { it.brand }
                adapter.notifyDataSetChanged()
                sortA = true
            }else {
                adapter.setItemsList(adapter.items.sortedBy { it.brand }.reversed())
                //adapter.items =  adapter.items.sortedBy { it.brand }.reversed()
                adapter.notifyDataSetChanged()
                sortA = false
            }
        })

        binding.btnSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                adapter.filter(s.toString())
            }
        })

        viewModel.carListLiveData.observe(viewLifecycleOwner) { cars ->
            if(cars.isNotEmpty()){
                binding.ProgressBar.visibility = View.GONE
            }
            adapter.setItemsList(cars)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshCars()
    }
}