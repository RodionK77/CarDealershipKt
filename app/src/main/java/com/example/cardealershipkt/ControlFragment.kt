package com.example.cardealershipkt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.cardealershipkt.API.Callbacks
import com.example.cardealershipkt.databinding.FragmentControlBinding
import com.example.cardealershipkt.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.Serializable

class ControlFragment : Fragment(), Serializable {

    private var _binding:FragmentControlBinding? = null
    private val binding get() = _binding!!
    private var mAuth: FirebaseAuth? = null
    private var mDataBase: DatabaseReference? = null
    private var callbacks: Callbacks? = null
    var currentUser: FirebaseUser? = null
    var user: User? = null

    fun getCallbacks(): Callbacks? {
        return callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentControlBinding.inflate(inflater, container, false)
        return binding.root
        //val fr = RegistrationFragment()
        //fragmentManager = parentFragmentManager

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    /*fun userCheck() {
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth!!.currentUser
        if (currentUser != null) {
            mDataBase =
                FirebaseDatabase.getInstance().getReference("Users").child(currentUser!!.uid)
            mDataBase!!.get().addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Toast.makeText(context, "Ошибка доступа", Toast.LENGTH_SHORT).show()
                    val fr = RegistrationFragment()
                    callbacks.controlFragmentSelected(fr)
                    //changeFragmentToRegistration();
                } else {
                    user = task.result.getValue(User::class.java)
                    //Toast.makeText(context,user.getName(), Toast.LENGTH_SHORT).show();
                    val fr = UserFragment()
                    val bundle = Bundle()
                    bundle.putSerializable("1", user)
                    fr.setArguments(bundle)
                    callbacks.controlFragmentSelected(fr)
                    //changeFragmentToUser(user);
                }
            }
            //getDataFromDB();
            //Toast.makeText(context,user.getUid(), Toast.LENGTH_SHORT).show();
            //changeFragmentToUser(user);
        } else {
            val fr = RegistrationFragment()
            callbacks.controlFragmentSelected(fr)
            //changeFragmentToRegistration();
        }
    }*/

    override fun onResume() {
        super.onResume()
        //userCheck()
    }

    /*companion object {
        var fragmentManager: FragmentManager? = null
    }*/
}