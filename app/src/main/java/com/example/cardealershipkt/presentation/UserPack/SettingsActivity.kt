package com.example.cardealershipkt.presentation.UserPack

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cardealershipkt.presentation.ViewModel.MainViewModel
import com.example.cardealershipkt.R
import com.example.cardealershipkt.domain.User
import com.example.cardealershipkt.databinding.ActivitySettingsBinding
import com.example.cardealershipkt.presentation.ViewModel.MainViewModelFactory
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import java.io.Serializable

class SettingsActivity : AppCompatActivity(), Serializable {

    var currentUser: FirebaseUser? = null
    private var mDataBase: DatabaseReference? = null
    private lateinit var user: User
    lateinit var binding: ActivitySettingsBinding

    private val viewModel: MainViewModel by viewModels{ MainViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar2)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.btnExit.setOnClickListener {
            viewModel.getAuth().signOut()
            Toast.makeText(applicationContext, getText(R.string.user_exit), Toast.LENGTH_SHORT)
                .show()
            finish()
        }
        currentUser = viewModel.getUser()
        mDataBase = viewModel.getFirebaseDatabase("Users").child(currentUser!!.uid)
        mDataBase!!.get().addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Toast.makeText(applicationContext, getText(R.string.access_denied), Toast.LENGTH_SHORT).show()
            } else {
                user = task.result.getValue(User::class.java)!!
                binding.etSettingsMail.setText(user.email)
                binding.etSettingsName.setText(user.name)
                binding.etSettingsLastname.setText(user.lastname)
                binding.etSettingsPhone.setText(user.phone)
                binding.etSettingsDate.setText(user.birthday)
            }
        }
    }

    fun onPush(view: View?) {
        user.name = binding.etSettingsName.text.toString()
        user.lastname = binding.etSettingsLastname.text.toString()
        user.birthday = binding.etSettingsDate.text.toString()
        user.email = binding.etSettingsMail.text.toString()
        user.phone = binding.etSettingsPhone.text.toString()
        mDataBase!!.setValue(user)
        Toast.makeText(applicationContext, getText(R.string.yes_changes), Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}