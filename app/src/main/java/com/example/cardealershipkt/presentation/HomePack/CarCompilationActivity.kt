package com.example.cardealershipkt.presentation.HomePack

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardealershipkt.presentation.MainViewModel
import com.example.cardealershipkt.R
import com.example.cardealershipkt.presentation.SearchPack.SearchAdapter
import com.example.cardealershipkt.databinding.ActivityCompilationBinding
import java.io.Serializable

class CarCompilationActivity : AppCompatActivity(), Serializable {

    private lateinit var str: String
    private var searchAdapter = SearchAdapter()
    lateinit var binding: ActivityCompilationBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompilationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar2)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        str = intent.getStringExtra("EXTRA_MESSAGE")!!
        binding.toolbar2.title = "${getText(R.string.compilation_title)} \"$str\""
        val layoutManager = LinearLayoutManager(this)
        binding.rvCompilation.layoutManager = layoutManager
        binding.rvCompilation.adapter = searchAdapter

        viewModel.getCarsCompilation(str).observe(this) { cars ->
            if(cars.isNotEmpty()){
                binding.ProgressBar.visibility = View.GONE
            }
            searchAdapter.items = cars
            searchAdapter.notifyDataSetChanged()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}