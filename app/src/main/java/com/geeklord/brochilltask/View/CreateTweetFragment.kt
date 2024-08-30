package com.geeklord.brochilltask.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.geeklord.brochilltask.Model.AuthRequest
import com.geeklord.brochilltask.Model.PostTweetRequest
import com.geeklord.brochilltask.R
import com.geeklord.brochilltask.Utils.Constants.TAG
import com.geeklord.brochilltask.Utils.NetworkResult
import com.geeklord.brochilltask.ViewModel.AuthViewModel
import com.geeklord.brochilltask.ViewModel.TweetsViewModel
import com.geeklord.brochilltask.databinding.FragmentCreateTweetBinding
import com.geeklord.brochilltask.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateTweetFragment : Fragment() {

    private var _binding : FragmentCreateTweetBinding? = null
    private val binding get() = _binding!!

    private val tweetViewModel by viewModels<TweetsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateTweetBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmit.setOnClickListener {
            val tweetReq = getTweetRequest()
            lifecycleScope.launch {
                tweetViewModel.postTweet(tweetReq)
            }
        }
        bindObserver()
    }

    private fun bindObserver() {
        tweetViewModel.postTweetLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    binding.createTweetProgress.visibility = View.GONE
                    findNavController().navigate(R.id.action_createTweetFragment_to_showTweetsFragment)
                }
                is NetworkResult.Error -> {
                    binding.createTweetProgress.visibility = View.GONE
                    Log.d(TAG, "Create Tweet: ${it.data}")
                    Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.createTweetProgress.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getTweetRequest(): PostTweetRequest {
        val tweet = binding.tweetDescription.text?.toString()
        // Handle null or empty email and password
        if (tweet.isNullOrEmpty()) {
            throw IllegalArgumentException("Tweet is empty")
        }

        return PostTweetRequest(tweet)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}