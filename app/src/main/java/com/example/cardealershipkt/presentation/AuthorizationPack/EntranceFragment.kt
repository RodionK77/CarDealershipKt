package com.example.cardealershipkt.presentation.AuthorizationPack

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cardealershipkt.presentation.MainViewModel
import com.example.cardealershipkt.R
import com.example.cardealershipkt.domain.User
import com.example.cardealershipkt.presentation.UserPack.UserFragment
import com.example.cardealershipkt.databinding.FragmentEntranceBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

class EntranceFragment : Fragment() {
    //private var callbacks: Callbacks? = null

    private var _binding: FragmentEntranceBinding? = null
    private val binding get() = _binding!!
    private var role = "user"
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDataBase: DatabaseReference
    private var passVisible = false
    private var currentUser: FirebaseUser? = null
    private lateinit var user: User

    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntranceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPasswordVisible(binding.etAdminPasswordEnter)

        mAuth = viewModel.getAuth()

        binding.btnUser.setOnClickListener {
            binding.etAdminCodeEnter.visibility = View.GONE
            role = "user"
        }
        binding.btnAdmin.setOnClickListener {
            binding.etAdminCodeEnter.visibility = View.VISIBLE
            role = "admin"
        }
        binding.btnToRegistration.setOnClickListener {
            val fr = RegistrationFragment()
            parentFragmentManager.beginTransaction().replace(R.id.control_fr, fr).commit()
        }

        binding.btnAdminEnter.setOnClickListener {
            if (binding.etAdminMailEnter.text.isNotEmpty()
                && binding.etAdminPasswordEnter.text.isNotEmpty()
                && ((role == "admin" && binding.etAdminCodeEnter.text.isNotEmpty()) || role == "user")
            ) {
                    if((role == "admin" && binding.etAdminCodeEnter.text.toString() == viewModel.adminCode) || role == "user") {
                        mAuth.signInWithEmailAndPassword(
                            binding.etAdminMailEnter.text.toString(),
                            binding.etAdminPasswordEnter.text.toString()
                        ).addOnCompleteListener(
                            (context as Activity?)!!
                        ) { task ->
                            if (task.isSuccessful) {
                                currentUser = viewModel.getUser()
                                mDataBase = viewModel.getFirebaseDatabase("Users").child(currentUser!!.uid)
                                mDataBase.get().addOnCompleteListener { task ->
                                    if (!task.isSuccessful) {
                                        Toast.makeText(
                                            context,
                                            getText(R.string.access_denied),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val fr = EntranceFragment()
                                        parentFragmentManager.beginTransaction().replace(R.id.control_fr, fr).commit()
                                    } else {
                                        user = task.result.getValue(User::class.java) as User
                                        if (user.role == "user" && role == "user") {
                                            Toast.makeText(
                                                context,
                                                getText(R.string.user_enter),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            val fr = UserFragment()
                                            val bundle = Bundle()
                                            bundle.putSerializable("1", user)
                                            fr.setArguments(bundle)
                                            parentFragmentManager.beginTransaction().replace(R.id.control_fr, fr).commit()
                                        } else if (user.role == "admin" && role == "user") {
                                            Toast.makeText(
                                                context,
                                                getText(R.string.not_user),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            viewModel.getAuth().signOut()
                                            val fr = EntranceFragment()
                                            parentFragmentManager.beginTransaction().replace(R.id.control_fr, fr).commit()
                                        } else if (user.role == "admin" && role == "admin") {
                                            Toast.makeText(
                                                context,
                                                getText(R.string.user_enter),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            val fr = UserFragment()
                                            val bundle = Bundle()
                                            bundle.putSerializable("1", user)
                                            fr.setArguments(bundle)
                                            parentFragmentManager.beginTransaction().replace(R.id.control_fr, fr).commit()
                                        } else if (user.role == "user" && role == "admin"){
                                            Toast.makeText(
                                                context,
                                                getText(R.string.not_admin),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            viewModel.getAuth().signOut()
                                            val fr = EntranceFragment()
                                            parentFragmentManager.beginTransaction().replace(R.id.control_fr, fr).commit()
                                        }
                                    }
                                }
                            } else Toast.makeText(
                                context,
                                getText(R.string.not_enter),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else Toast.makeText(context, getText(R.string.not_code), Toast.LENGTH_LONG).show()
            } else Toast.makeText(context, getText(R.string.not_all_fields), Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun checkPasswordVisible(pass: EditText) {
        pass.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            val right = 2
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (motionEvent.rawX >= pass.right - pass.compoundDrawables[right].bounds.width()) {
                    val selection = pass.selectionEnd
                    if (passVisible) {
                        pass.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_visibility_off,
                            0
                        )
                        pass.transformationMethod = PasswordTransformationMethod.getInstance()
                        pass.inputType = 129
                        passVisible = false
                    } else {
                        pass.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_visibility,
                            0
                        )
                        pass.transformationMethod = PasswordTransformationMethod.getInstance()
                        pass.inputType = 129
                        pass.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                        passVisible = true
                    }
                    pass.setSelection(selection)
                    return@OnTouchListener true
                }
            }
            false
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}