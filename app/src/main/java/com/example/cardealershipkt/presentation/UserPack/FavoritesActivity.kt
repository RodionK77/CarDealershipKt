package com.example.cardealershipkt.presentation.UserPack

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardealershipkt.*
import com.example.cardealershipkt.presentation.SearchPack.SearchAdapter
import com.example.cardealershipkt.data.Room.CarItem
import com.example.cardealershipkt.databinding.ActivityFavoritesBinding
import com.example.cardealershipkt.domain.User
import com.example.cardealershipkt.presentation.ViewModel.MainViewModel
import com.example.cardealershipkt.presentation.ViewModel.MainViewModelFactory
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import java.io.Serializable

class FavoritesActivity : AppCompatActivity(), Serializable {

    private var user: User? = null
    private var user2: User? = null
    private var str: String? = null
    private var searchAdapter = SearchAdapter()
    var data: MutableList<CarItem>? = null
    lateinit var strList: List<String>
    var currentUser: FirebaseUser? = null
    private var mDataBase: DatabaseReference? = null
    lateinit var binding: ActivityFavoritesBinding

    private val viewModel: MainViewModel by viewModels{ MainViewModelFactory(this) }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar2)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorites.layoutManager = layoutManager
        binding.rvFavorites.adapter = searchAdapter

        user2 = intent.getSerializableExtra("EXTRA_MESSAGE") as User?
        currentUser = viewModel.getUser()
        mDataBase = viewModel.getFirebaseDatabase("Users").child(currentUser!!.uid)

        viewModel.carListLiveData.observe(this) {
            data = it as MutableList<CarItem>?
        }

    }

    private fun checkFav() {
        mDataBase!!.get().addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Toast.makeText(applicationContext, getText(R.string.access_denied), Toast.LENGTH_SHORT).show()
            } else {
                user = task.result.getValue(User::class.java)
                str = user!!.bookstores
                strList = str!!.split("-").dropLast(1)
                if (str!!.isNotEmpty()) {
                    for(i in strList){
                        data!!.removeAll { !strList.contains(it.id.toString()) }
                    }
                    searchAdapter.setItemsList(data!!)
                    //searchAdapter.items = data!!
                    searchAdapter.notifyDataSetChanged()
                    binding.tvFavWarning.visibility = View.GONE
                    binding.rvFavorites.visibility = View.VISIBLE
                } else {
                    binding.tvFavWarning.visibility = View.VISIBLE
                    binding.rvFavorites.visibility = View.GONE
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        checkFav()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}