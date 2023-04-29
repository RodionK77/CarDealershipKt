package com.example.cardealershipkt.presentation.AuthorizationPack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cardealershipkt.presentation.MainViewModel
import com.example.cardealershipkt.R
import com.example.cardealershipkt.domain.User
import com.example.cardealershipkt.presentation.UserPack.UserFragment
import com.example.cardealershipkt.databinding.FragmentControlBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import java.io.Serializable

class ControlFragment : Fragment(), Serializable {

    private var _binding:FragmentControlBinding? = null
    private val binding get() = _binding!!
    private lateinit var mDataBase: DatabaseReference
    var currentUser: FirebaseUser? = null
    var user: User? = null

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentControlBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun userCheck() {
        currentUser = viewModel.getUser()
        if (currentUser != null) {
            mDataBase = viewModel.getFirebaseDatabase("Users").child(currentUser!!.uid)
            mDataBase!!.get().addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Toast.makeText(context, getText(R.string.access_denied), Toast.LENGTH_SHORT).show()
                    val fr = RegistrationFragment()
                    parentFragmentManager.beginTransaction().replace(R.id.control_fr, fr).commit()
                } else {
                    user = task.result.getValue(User::class.java)
                    val fr = UserFragment()
                    val bundle = Bundle()
                    bundle.putSerializable("1", user)
                    fr.setArguments(bundle)
                    parentFragmentManager.beginTransaction().replace(R.id.control_fr, fr).commit()
                }
            }
        } else {
            val fr = RegistrationFragment()
            parentFragmentManager.beginTransaction().replace(R.id.control_fr, fr).commit()
        }
    }

    override fun onResume() {
        super.onResume()
        userCheck()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}