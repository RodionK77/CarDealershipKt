package com.example.cardealershipkt.presentation.AuthorizationPack

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cardealershipkt.presentation.MainViewModel
import com.example.cardealershipkt.R
import com.example.cardealershipkt.domain.User
import com.example.cardealershipkt.presentation.UserPack.UserFragment
import com.example.cardealershipkt.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private var role = "user"
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDataBase: DatabaseReference
    private var passVisible = false

    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPasswordVisible(binding.etAdminPassword)
        checkPasswordVisible(binding.etAdminPassword2)

        mAuth = viewModel.getAuth()

        binding.btnUser.setOnClickListener {
            binding.etAdminCode.visibility = View.GONE
            role = "user"
        }
        binding.btnAdmin.setOnClickListener {
            binding.etAdminCode.visibility = View.VISIBLE
            role = "admin"
        }
        binding.btnToEntrance.setOnClickListener {
            val fr = EntranceFragment()
            parentFragmentManager.beginTransaction().replace(R.id.control_fr, fr).commit()
        }

        binding.btnAdminRegistration.setOnClickListener {
            if (binding.etAdminMail.text.isNotEmpty()
                && binding.etAdminPassword.text.isNotEmpty()
                && binding.etAdminPassword2.text.isNotEmpty()
                && binding.etAdminName.text.isNotEmpty()
                && binding.etAdminLastname.text.isNotEmpty()
                && binding.etAdminPhone.text.isNotEmpty()
                && binding.etAdminDate.text.isNotEmpty()
                && ((role == "admin" && binding.etAdminCode.text.isNotEmpty()) || role == "user")
            ) {
                if (binding.etAdminPassword.text.toString() == binding.etAdminPassword2.text.toString()) {
                    if((role == "admin" && binding.etAdminCode.text.toString() == viewModel.adminCode) || role == "user") {
                        mAuth.createUserWithEmailAndPassword(
                            binding.etAdminMail.text.toString(),
                            binding.etAdminPassword.text.toString()
                        ).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "$role ${getText(R.string.reg_yes)}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val name: String = binding.etAdminName.text.toString()
                                val lastname: String = binding.etAdminLastname.text.toString()
                                val email: String = binding.etAdminMail.text.toString()
                                val birthday: String = binding.etAdminDate.text.toString()
                                val phone: String = binding.etAdminPhone.text.toString()
                                val currentUser = mAuth.currentUser
                                val newUser = User(
                                    name,
                                    lastname,
                                    email,
                                    birthday,
                                    phone,
                                    role,
                                    currentUser!!.uid,
                                    ""
                                )
                                mDataBase =
                                    viewModel.getFirebaseDatabase("Users").child(currentUser!!.uid)
                                mDataBase.setValue(newUser)
                                val fr = UserFragment()
                                val bundle = Bundle()
                                bundle.putSerializable("1", newUser)
                                fr.setArguments(bundle)
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.control_fr, fr).commit()
                            } else Toast.makeText(
                                context,
                                getText(R.string.not_reg),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else Toast.makeText(context, getText(R.string.not_code), Toast.LENGTH_LONG).show()
                } else Toast.makeText(context, getText(R.string.not_pass), Toast.LENGTH_LONG).show()
            } else Toast.makeText(context, getText(R.string.not_all_fields), Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun checkPasswordVisible(pass: EditText) {
        pass.setOnTouchListener(OnTouchListener { view, motionEvent ->
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