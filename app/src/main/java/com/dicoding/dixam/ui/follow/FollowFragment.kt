package com.dicoding.dixam.ui.follow

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dixam.databinding.FragmentFollowBinding
import com.dicoding.dixam.ui.main.UserListAdapter

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter: UserListAdapter
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

        if (isAdded && !isDetached) {
            val userFollowViewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[FollowViewModel::class.java]

            userFollowViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            userFollowViewModel.followList.observe(viewLifecycleOwner) { followList ->
                if (followList != null) {
                    adapter = UserListAdapter(followList)
                    binding.rvFollowList.adapter = adapter
                }
            }

            if (position == 1){
                userFollowViewModel.getFollowList(username, "follower")
            } else {
                userFollowViewModel.getFollowList(username, "following")
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.rvFollowList.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}