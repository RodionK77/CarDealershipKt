package com.example.cardealershipkt

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cardealershipkt.databinding.ActivityCompilationBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable

class CarCompilationActivity : AppCompatActivity(), Serializable {

    private var extra = 0
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
        binding.toolbar2.title = "Подборка \"$str\""
        val layoutManager = LinearLayoutManager(this)
        binding.rvCompilation.layoutManager = layoutManager
        binding.rvCompilation.adapter = searchAdapter

        viewModel.getCarsCompilation(str).observe(this) { cars ->
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