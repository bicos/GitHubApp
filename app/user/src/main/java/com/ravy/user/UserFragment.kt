package com.ravy.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ravy.common_ui.adapter.RepositoryAdapter
import com.ravy.common_ui.adapter.RepositoryAdapterViewModel
import com.ravy.common_ui.autoCleared
import com.ravy.common_ui.viewModel.MainViewModel
import com.ravy.user.databinding.FragmentUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment: Fragment() {

    private val parentViewModel: MainViewModel by activityViewModels()

    private val viewModel: UserViewModel by viewModels()

    private var binding: FragmentUserBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@UserFragment.viewLifecycleOwner
            viewModel = this@UserFragment.viewModel
            setupUi(this)
        }
        return binding.root
    }

    private fun setupUi(fragmentUserBinding: FragmentUserBinding) {

        viewModel.navigateUrl.observe(viewLifecycleOwner) {
            parentViewModel.navigateUrl(it)
        }

        val adapterViewModel = RepositoryAdapterViewModel()
        val adapter = RepositoryAdapter(adapterViewModel)
        fragmentUserBinding.listRepo.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        viewModel.retry.observe(viewLifecycleOwner) {
            adapter.retry()
        }
        viewModel.showToast.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        adapter.addLoadStateListener {
            viewModel.changeLoadStates(it)
        }

        adapterViewModel.clickRepository.observe(viewLifecycleOwner) {
            parentViewModel.navigateUrl(it.htmlUrl)
        }
    }
}