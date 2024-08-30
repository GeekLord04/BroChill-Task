package com.geeklord.brochilltask.View

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import com.geeklord.brochilltask.Utils.HelperFunctions
import com.geeklord.brochilltask.Utils.NetworkResult
import com.geeklord.brochilltask.Utils.TokenManager
import com.geeklord.brochilltask.ViewModel.AuthViewModel
import com.geeklord.brochilltask.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createAcc.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.loginBtn.setOnClickListener {

            if (!HelperFunctions().isInternetAvailable(requireContext())){
                Toast.makeText(this.context, "No Internet Connection", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val validationResult = userValidation()
            if (validationResult.first){
                val userReq = getUserRequest()
                lifecycleScope.launch {
                    authViewModel.loginUser(userReq)
                }
            }
            else{
                Toast.makeText(this.context, validationResult.second, Toast.LENGTH_SHORT).show()
            }
        }

        binding.txtRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        bindObserver()


    }

    private fun bindObserver() {
        authViewModel.userLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    binding.loginProgressBar.visibility = View.GONE
                    tokenManager.saveToken(it.data!!.token!!)
                    Log.d(TAG, "Login Success: ${it.data}")
                    findNavController().navigate(R.id.action_loginFragment_to_showTweetsFragment)
                }
                is NetworkResult.Error -> {
                    binding.loginProgressBar.visibility = View.GONE
                    Log.d(TAG, "Login Error: ${it.data} + ${it.message}")
                    Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.loginProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getUserRequest(): AuthRequest {
        val emailAdd = binding.loginEmail.text?.toString()
        val pass = binding.loginPass.text?.toString()

        // Handle null or empty email and password
        if (emailAdd.isNullOrEmpty() || pass.isNullOrEmpty()) {
            throw IllegalArgumentException("Email or password cannot be empty")
        }

        return AuthRequest(email = emailAdd, password = pass)
    }

    private fun userValidation(): Pair<Boolean, String> {
        return try {
            val userRequest = getUserRequest()
            HelperFunctions().loginUserInputValidation(userRequest.email!!, userRequest.password!!, true)
        } catch (e: IllegalArgumentException) {
            Pair(false, e.message ?: "Invalid input")
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}