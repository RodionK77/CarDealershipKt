package com.example.cardealershipkt.presentation.UserPack

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardealershipkt.presentation.ViewModel.MainViewModel
import com.example.cardealershipkt.R
import com.example.cardealershipkt.domain.User
import com.example.cardealershipkt.databinding.ActivityUsersListBinding
import com.example.cardealershipkt.presentation.ViewModel.MainViewModelFactory
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.io.Serializable

class UsersListActivity : AppCompatActivity(), Serializable {

    private var usersAdapter: UsersAdapter = UsersAdapter()
    var data: MutableList<User> = mutableListOf()
    var currentUser: FirebaseUser? = null
    private var mDataBase: DatabaseReference? = null
    lateinit var binding: ActivityUsersListBinding

    private val viewModel: MainViewModel by viewModels {MainViewModelFactory(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar2)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        currentUser = viewModel.getUser()
        mDataBase = viewModel.getFirebaseDatabase("Users")

        val layoutManager = LinearLayoutManager(applicationContext)
        registerForContextMenu(binding.rvUsers);
        binding.rvUsers.layoutManager = layoutManager
        binding.rvUsers.adapter = usersAdapter

        getAllData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    private fun getAllData(){
        val valueEventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (data.size > 0) {
                    data.clear()
                }
                for (ds in snapshot.children) {
                    val user = ds.getValue(User::class.java)!!
                    data.add(user)
                    System.out.println(user.name)
                }
                usersAdapter.items = data
                usersAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        }
        mDataBase!!.addValueEventListener(valueEventListener)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val user = data[item.itemId]
        if (currentUser!!.uid != user.uid) {
            if (user.role == "admin") {
                user.role = "user"
            } else if (user.role == "user") {
                user.role = "admin"
            }
            mDataBase = viewModel.getFirebaseDatabase("Users").child(user.uid)
            mDataBase!!.setValue(user)
        } else Toast.makeText(
            applicationContext,
            getText(R.string.not_change_role),
            Toast.LENGTH_SHORT
        ).show()
        return super.onContextItemSelected(item)
    }
}