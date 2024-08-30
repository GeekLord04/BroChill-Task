package com.geeklord.brochilltask.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.geeklord.brochilltask.R
import com.geeklord.brochilltask.Utils.NetworkResult
import com.geeklord.brochilltask.ViewModel.TweetsViewModel
import com.geeklord.brochilltask.databinding.FragmentRegisterBinding
import com.geeklord.brochilltask.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    private var _binding : FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    private val tweetViewModel by viewModels<TweetsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWelcomeBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindObserver()

        lifecycleScope.launch {
            tweetViewModel.welcome()
        }

        binding.startBtn.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_showTweetsFragment)
        }

        binding.imageView.load(R.drawable.brochill){
            transformations(CircleCropTransformation())
        }



    }

    private fun bindObserver() {
        tweetViewModel.welcomeLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    binding.welcomeMsg.text = it.data?.message
                }
                is NetworkResult.Error -> {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.welcomeMsg.text = "..."
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}