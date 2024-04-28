package com.dicoding.dixam.ui.follow

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dixam.data.Result
import com.dicoding.dixam.databinding.FragmentFollowBinding
import com.dicoding.dixam.ui.ViewModelFactory
import com.dicoding.dixam.ui.main.UserListAdapter

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter: UserListAdapter
    private lateinit var followViewModel: FollowViewModel
    private lateinit var username: String
    private var position: Int = 0

    companion object {
        var ARG_USERNAME: String = ""
        var ARG_POSITION = "section_number"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: "DefaultUsername"
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowList.layoutManager = layoutManager

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        followViewModel = ViewModelProvider(this, factory)[FollowViewModel::class.java]

        followViewModel.followerList.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                }

                is Result.Success -> {
                    adapter = UserListAdapter(it.data)
                    binding.rvFollowList.adapter = adapter
                }
            }
        }

        followViewModel.followingList.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                }

                is Result.Success -> {
                    adapter = UserListAdapter(it.data)
                    binding.rvFollowList.adapter = adapter
                }
            }
        }

        if (isAdded && !isDetached) {
            if (position == 1) {
                followViewModel.getFollowerList(username)
            } else {
                followViewModel.getFollowingList(username)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.rvFollowList.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}