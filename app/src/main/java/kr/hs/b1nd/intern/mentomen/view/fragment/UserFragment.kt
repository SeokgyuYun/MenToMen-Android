package kr.hs.b1nd.intern.mentomen.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kr.hs.b1nd.intern.mentomen.App
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentUserBinding
import kr.hs.b1nd.intern.mentomen.view.activity.LoginActivity
import kr.hs.b1nd.intern.mentomen.view.activity.MainActivity
import kr.hs.b1nd.intern.mentomen.view.adapter.HomeAdapter
import kr.hs.b1nd.intern.mentomen.viewmodel.UserViewModel

class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding
    private lateinit var userViewModel : UserViewModel
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user,
            container,
            false)
        (activity as MainActivity).hasBottomBar(true)

        performViewModel()
        initHomeAdapter()
        observeViewModel()

        with(userViewModel) {
            callUser()
            callPost()
        }

        binding.btnLogout.setOnClickListener {
            App.prefs.logout()
            App.prefs.deleteToken()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }

        return binding.root

    }

    private fun performViewModel() {
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        binding.vm = userViewModel
        binding.lifecycleOwner = this
    }

    private fun initHomeAdapter() {
        homeAdapter = HomeAdapter {
            val action = UserFragmentDirections.actionUserFragmentToDetailFragment(it.postId)
            findNavController().navigate(action)
        }
        binding.rvMyPage.adapter = homeAdapter
    }

    private fun observeViewModel() {
        with(userViewModel) {
            itemList.observe(viewLifecycleOwner) {
                homeAdapter.submitList(it)
            }
        }
    }
}