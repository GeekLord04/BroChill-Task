package com.geeklord.brochilltask.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geeklord.brochilltask.R
import com.geeklord.brochilltask.Utils.NetworkResult
import com.geeklord.brochilltask.Utils.TokenManager
import com.geeklord.brochilltask.ViewModel.TweetsViewModel
import com.geeklord.brochilltask.databinding.FragmentLoginBinding
import com.geeklord.brochilltask.databinding.FragmentRegisterBinding
import com.geeklord.brochilltask.databinding.FragmentShowTweetsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ShowTweetsFragment : Fragment() {

    private var _binding : FragmentShowTweetsBinding? = null
    private val binding get() = _binding!!

    private val tweetViewModel by viewModels<TweetsViewModel>()

    private lateinit var adapter : TweetAdapter;

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentShowTweetsBinding.inflate(inflater,container, false)
        adapter = TweetAdapter(mutableListOf())
        binding.tweetList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addTweetBtn.setOnClickListener {
            findNavController().navigate(R.id.action_showTweetsFragment_to_createTweetFragment)
        }

        binding.logoutBtn.setOnClickListener {
            tokenManager.deleteToken()
            findNavController().navigate(R.id.action_showTweetsFragment_to_registerFragment)
        }

        binding.tweetList.layoutManager = LinearLayoutManager(requireContext())

        bindObserver()
        lifecycleScope.launch {
            tweetViewModel.getTweets()
        }
    }

    private fun bindObserver() {
        tweetViewModel.getTweetsLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    adapter.submitList(it.data)
                    binding.showTweetProgress.visibility = View.GONE
                }
                is NetworkResult.Error -> {
                    binding.showTweetProgress.visibility = View.GONE
                    Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.showTweetProgress.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}