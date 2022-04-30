package com.ravy.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ravy.common_ui.adapter.RepositoryAdapter
import com.ravy.common_ui.adapter.RepositoryAdapterViewModel
import com.ravy.common_ui.autoCleared
import com.ravy.common_ui.viewModel.MainViewModel
import com.ravy.search.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val parentViewModel: MainViewModel by activityViewModels()

    private val viewModel: SearchViewModel by viewModels()

    private var binding: FragmentSearchBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@SearchFragment.viewLifecycleOwner
            viewModel = this@SearchFragment.viewModel
            setupUi(this)
        }
        return binding.root
    }

    private fun setupUi(binding: FragmentSearchBinding) {
        val adapterViewModel = RepositoryAdapterViewModel()
        val adapter = RepositoryAdapter(adapterViewModel)
        binding.listRepository.adapter = adapter

        viewModel.showToast.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewModel.retry.observe(viewLifecycleOwner) {
            adapter.retry()
        }

        viewModel.requestSearchEvent.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) binding.listRepository.scrollToPosition(0)
        }

        adapterViewModel.clickRepository.observe(viewLifecycleOwner) {
            parentViewModel.navigateUrl(it.htmlUrl)
        }

        adapterViewModel.clickOwner.observe(viewLifecycleOwner) {
            parentViewModel.navigateUrl(it.htmlUrl)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                viewModel.changeLoadStates(loadStates)
            }
        }
    }

}