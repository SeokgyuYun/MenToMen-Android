package kr.hs.b1nd.intern.mentomen.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentHomeBinding
import kr.hs.b1nd.intern.mentomen.network.model.Post
import kr.hs.b1nd.intern.mentomen.util.TagState
import kr.hs.b1nd.intern.mentomen.view.activity.MainActivity
import kr.hs.b1nd.intern.mentomen.view.adapter.HomeAdapter
import kr.hs.b1nd.intern.mentomen.viewmodel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        performViewModel()
        homeViewModel.callPost()
        observeViewModel()

        (activity as MainActivity).hasTopBar()
        (activity as MainActivity).hasBottomBar()

        binding.refreshLayout.setOnRefreshListener {
            homeViewModel.tagState.value = TagState(isDesignChecked = true, isWebChecked = true, isAndroidChecked = true, isServerChecked = true, isiOSChecked = true)
            homeViewModel.callPost()
            observeViewModel()
            binding.refreshLayout.isRefreshing = false
        }


        return binding.root
    }


    private fun observeViewModel() {
        with(homeViewModel) {
            itemList.observe(viewLifecycleOwner) {
                initHomeAdapter(it)
            }
        }
    }


    private fun initHomeAdapter(items: List<Post>) {
        homeAdapter = HomeAdapter(items) {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.postId)
            findNavController().navigate(action)
        }
        binding.rvHome.adapter = homeAdapter
        homeAdapter.notifyDataSetChanged()
    }


    private fun performViewModel() {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.vm = homeViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

}
