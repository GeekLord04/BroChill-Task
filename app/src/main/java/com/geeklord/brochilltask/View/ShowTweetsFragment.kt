package com.geeklord.brochilltask.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.geeklord.brochilltask.R
import com.geeklord.brochilltask.databinding.FragmentLoginBinding
import com.geeklord.brochilltask.databinding.FragmentRegisterBinding
import com.geeklord.brochilltask.databinding.FragmentShowTweetsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowTweetsFragment : Fragment() {

    private var _binding : FragmentShowTweetsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentShowTweetsBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.next.setOnClickListener {
            findNavController().navigate(R.id.action_showTweetsFragment_to_createTweetFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}