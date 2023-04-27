package com.example.cardealershipkt

import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cardealershipkt.databinding.FragmentSearchBinding
import com.squareup.picasso.Picasso
import java.util.*

class SearchFragment : Fragment() {

    private lateinit var data: List<CarItem>
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var sort = false;
    private val adapter: SearchAdapter = SearchAdapter()

    private val viewModel: MainViewModel by activityViewModels()

    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                Collections.sort(data,
                    Comparator<CarItem> { lhs, rhs -> lhs.price!!.compareTo(rhs.price!!) })
                adapter.items = data
                adapter.notifyDataSetChanged()
            }else {
                Collections.sort(data,
                    Comparator<CarItem> { lhs, rhs -> rhs.price!!.compareTo(lhs.price!!) })
                adapter.items = data
                adapter.notifyDataSetChanged()
            }

        })
        binding.btnSort3.setOnClickListener(View.OnClickListener {
            //val carsComparator = Comparator.comparing<CarItem, Any>(CarItem::brand)
            //Collections.sort(data, carsComparator)
            data = data.sortedBy { it.brand }
            adapter.items = data
            adapter.notifyDataSetChanged()
        })

        viewModel.carListLiveData.observe(viewLifecycleOwner) { cars ->
            Log.d("IT", cars.get(0)!!.brand!!)
            adapter.items = cars
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshCars()
    }
}