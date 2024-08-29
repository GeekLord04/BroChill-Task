package com.geeklord.brochilltask.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.geeklord.brochilltask.Model.AuthRequest
import com.geeklord.brochilltask.R
import com.geeklord.brochilltask.Utils.Constants.TAG
import com.geeklord.brochilltask.Utils.NetworkResult
import com.geeklord.brochilltask.Utils.TokenManager
import com.geeklord.brochilltask.Utils.inputValidationHelper
import com.geeklord.brochilltask.ViewModel.AuthViewModel
import com.geeklord.brochilltask.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater,container, false)

        //TODO : Check if user already logged in or not
//        if (tokenManager.getToken() != null){
//            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
//
//        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signupBtn.setOnClickListener {
            val validationResult = userValidation()
            if (validationResult.first){
                val userReq = getUserRequest()
                lifecycleScope.launch {
                    authViewModel.registerUser(userReq)
                }
            }
            else{
                Toast.makeText(this.context, validationResult.second, Toast.LENGTH_SHORT).show()
            }
        }

        binding.txtLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        bindObservers()
    }

    private fun bindObservers() {
        authViewModel.userLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    tokenManager.saveToken(it.data!!.token!!)
                    Log.d(TAG, "Register Success: ${it.data}")
                    findNavController().navigate(R.id.action_registerFragment_to_showTweetsFragment)
                }
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d(TAG, "Register Error: ${it.message}")
                    Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getUserRequest() : AuthRequest{
        val firstname = binding.firstName.text.toString()
        val lastname = binding.lastName.text.toString()
        val emailAdd = binding.email.text.toString()
        val pass = binding.pass.text.toString()
        return AuthRequest(firstname,lastname,emailAdd,pass)
    }

    private fun userValidation(): Pair<Boolean, String> {
        val userRequest = getUserRequest()
        return inputValidationHelper().userInputValidation( userRequest.firstName, userRequest.lastName, userRequest.email!!, userRequest.password!!, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}