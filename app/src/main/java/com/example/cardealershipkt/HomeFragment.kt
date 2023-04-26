package com.example.cardealershipkt

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cardealershipkt.databinding.FragmentHomeBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private lateinit var context: Context
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        context = activity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDataBase = FirebaseDatabase.getInstance().reference.child("Promo")
        mDataBase.get().addOnCompleteListener(OnCompleteListener<DataSnapshot> { task ->
            if (!task.isSuccessful) {
                Toast.makeText(context, "Ошибка доступа", Toast.LENGTH_SHORT).show()
            } else {
                //user = (User)task.getResult().getValue(User.class);
                //Toast.makeText(context,user.getName(), Toast.LENGTH_SHORT).show();
                var idx = task.result.getValue(Int::class.java)
                //val manager = DBmanager(context)
                val item: CarItem = manager.getOneItem(0, idx)
                var name: String = item.name
                if (name.contains(" ")) {
                    name = name.substring(0, name.indexOf(" "))
                }
                binding.brandCard0.text = item.brand + " " + name
                binding.priceCard0.text = ("${item.price}₽").toString()
                Picasso.get().load(item.image).into(binding.ivCard0)
            }
        })


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}