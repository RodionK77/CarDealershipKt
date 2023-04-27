package com.example.cardealershipkt

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.cardealershipkt.databinding.FragmentHomeBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var carList: List<CarItem>
    private lateinit var mDataBase: DatabaseReference
    private lateinit var item: CarItem
    private var idx = 1

    private val viewModel: MainViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        mDataBase = viewModel.getFirebaseDatabase("Promo")
        mDataBase.get().addOnCompleteListener(OnCompleteListener<DataSnapshot> { task ->
            if (!task.isSuccessful) {
                Toast.makeText(context, "Ошибка доступа", Toast.LENGTH_SHORT).show()
            } else {
                idx = task.result.getValue(Int::class.java)!!
                Log.d("XXX", idx.toString())
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCar(idx!!).observe(viewLifecycleOwner) { car ->
            item = car
            var name: String = item.name!!
            if (name.contains(" ")) {
                name = name.substring(0, name.indexOf(" "))
            }
            binding.brandCard0.text = item.brand + " " + name
            binding.priceCard0.text = ("${item.price}₽").toString()
            Picasso.get().load(item.image).into(binding.ivCard0)
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshCars()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}