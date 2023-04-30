package com.example.cardealershipkt.presentation.HomePack

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cardealershipkt.presentation.MainViewModel
import com.example.cardealershipkt.R
import com.example.cardealershipkt.data.Room.CarItem
import com.example.cardealershipkt.databinding.FragmentHomeBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mDataBase: DatabaseReference
    private var item: CarItem? = null
    private lateinit var name: String
    private var idx = 1

    private val viewModel: MainViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardView0.setOnClickListener(View.OnClickListener {
            if(item!=null) {
                val intent = Intent(context, CarInfoActivity::class.java)
                intent.putExtra("EXTRA_MESSAGE", item)
                context?.startActivity(intent)
            }
        })
        binding.cardView1.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, CarCompilationActivity::class.java)
            intent.putExtra("EXTRA_MESSAGE", getText(R.string.off_road))
            context?.startActivity(intent)
        })
        binding.cardView2.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, CarCompilationActivity::class.java)
            intent.putExtra("EXTRA_MESSAGE", getText(R.string.sedan))
            context?.startActivity(intent)
        })
        binding.cardView3.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, CarCompilationActivity::class.java)
            intent.putExtra("EXTRA_MESSAGE", getText(R.string.sport_classic))
            context?.startActivity(intent)
        })
        binding.cardView4.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, CarCompilationActivity::class.java)
            intent.putExtra("EXTRA_MESSAGE", getText(R.string.convertible))
            context?.startActivity(intent)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshCars()

        mDataBase = viewModel.getFirebaseDatabase("Promo")
        mDataBase.get().addOnCompleteListener(OnCompleteListener<DataSnapshot> { task ->
            if (!task.isSuccessful) {
                Toast.makeText(context, getText(R.string.access_denied), Toast.LENGTH_SHORT).show()
            } else {

                idx = task.result.getValue(Int::class.java)!!
                GlobalScope.launch(Dispatchers.IO) {
                    item = viewModel.getCar(idx)
                    if(item!=null){
                        name = item!!.name!!
                        if (name.contains(" ")) {
                            name = name.substring(0, name.indexOf(" "))
                        }
                        withContext(Dispatchers.Main){
                            binding.brandCard0.text = item!!.brand + " " + name
                            binding.priceCard0.text = ("${item!!.price}₽").toString()
                            Picasso.get().load(item!!.image).into(binding.ivCard0)
                        }
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}