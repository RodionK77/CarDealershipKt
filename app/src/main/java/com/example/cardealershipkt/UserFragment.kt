package com.example.cardealershipkt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cardealershipkt.databinding.FragmentUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.Serializable

class UserFragment : Fragment(), Serializable {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    var user: User? = null
    var currentUser: FirebaseUser? = null
    private var mAuth: FirebaseAuth? = null
    private var mDataBase: DatabaseReference? = null

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        user = bundle!!.getSerializable("1") as User?
        mAuth = FirebaseAuth.getInstance()
        checkUser()
        //currentUser = mAuth!!.currentUser
        mDataBase = viewModel.getFirebaseDatabase("Users").child(currentUser!!.uid)
        mDataBase!!.get().addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Toast.makeText(context, "Ошибка доступа", Toast.LENGTH_SHORT).show()
            } else {
                user = task.result.getValue(User::class.java)
            }
        }
        if (user!!.role == "admin") {
            binding.userAdmin.visibility = View.VISIBLE
        }
        binding.tvUserCardName.text = user!!.name + " " + user!!.lastname

       binding.cvBtn3.setOnClickListener {
            val intent = Intent(context, SettingsActivity::class.java)
            context?.startActivity(intent)
        }
        binding.cvBtn1.setOnClickListener {
            val intent = Intent(context, FavoritesActivity::class.java)
            intent.putExtra("EXTRA_MESSAGE", user)
            context?.startActivity(intent)
        }
        binding.cvBtn4.setOnClickListener {
            val intent = Intent(context, UsersListActivity::class.java)
            context?.startActivity(intent)
        }
        binding.btnAdminOk.setOnClickListener(View.OnClickListener {
            val value = binding.etAdminId.text.toString()
            val id = value.toInt()
            if (!(id > 18 || id < 1)) {
                mDataBase = viewModel.getFirebaseDatabase("Promo")
                mDataBase!!.get().addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(context, "Ошибка доступа", Toast.LENGTH_SHORT).show()
                    } else {
                        mDataBase!!.setValue(id)
                    }
                }
            } else Toast.makeText(context, "Неверный ID", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onStart() {
        super.onStart()
        checkUser()
    }

    private fun checkUser(){
        currentUser = viewModel.getUser()
        Log.d("TTT", currentUser.toString())
        if (currentUser != null) {
            mDataBase = viewModel.getFirebaseDatabase("Users").child(currentUser!!.uid)
            mDataBase!!.get().addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Toast.makeText(context, "Ошибка доступа", Toast.LENGTH_SHORT).show()
                } else {
                    user = task.result.getValue(User::class.java)
                }
            }
            binding.tvUserCardName.text = user!!.name + " " + user!!.lastname
        }else {
            val fr = RegistrationFragment()
            parentFragmentManager.beginTransaction().replace(R.id.control_fr, fr).commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}