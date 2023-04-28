package com.example.cardealershipkt

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.cardealershipkt.databinding.ActivityCarinfoBinding
import com.example.cardealershipkt.databinding.ActivitySettingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.Serializable

class SettingsActivity : AppCompatActivity(), Serializable {

    var currentUser: FirebaseUser? = null
    private var mDataBase: DatabaseReference? = null
    private lateinit var user: User
    lateinit var binding: ActivitySettingsBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar2)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.btnExit.setOnClickListener {
            viewModel.getAuth().signOut()
            Toast.makeText(applicationContext, "Пользователь вышел из аккаунта", Toast.LENGTH_SHORT)
                .show()
            finish()
            //ControlFragment.changeFragmentToEntrance();
        }
        currentUser = viewModel.getUser()
        mDataBase = viewModel.getFirebaseDatabase("Users").child(currentUser!!.uid)
        mDataBase!!.get().addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Toast.makeText(applicationContext, "Ошибка доступа", Toast.LENGTH_SHORT).show()
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
        Toast.makeText(applicationContext, "Изменения внесены", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}