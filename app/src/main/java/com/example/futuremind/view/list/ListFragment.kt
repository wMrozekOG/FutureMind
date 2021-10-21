package com.example.futuremind.view.list

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.example.futuremind.R
import com.example.futuremind.databinding.FragmentItemListBinding
import com.example.futuremind.view.base.BaseFragment
import com.example.futuremind.view.error.ErrorConfiguration
import com.example.futuremind.view.error.ErrorState
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class ListFragment: BaseFragment<FragmentItemListBinding>() {

    private val listViewModel: ListViewModel by stateViewModel()

    override val viewBinding: FragmentItemListBinding
        get() = FragmentItemListBinding.inflate(layoutInflater)

    private val onItemClick: (String) -> Unit = { url ->
        val itemDetailFragmentContainer: View? = view?.findViewById(R.id.details_nav_container)
        itemDetailFragmentContainer?.run {
            itemDetailFragmentContainer.findNavController().navigate(R.id.item_detail_fragment_tablet, Bundle().apply { putString("url", url) })
        } ?: run {
            navigate(ListFragmentDirections.showItemDetail(url))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            listViewModel.refresh()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = ItemAdapter(
            onItemClick
        )
        binding.swipeRefresh.setOnRefreshListener { listViewModel.refresh() }
        setupObservers()
    }

    private fun setupObservers() {
        listViewModel.items.observe(viewLifecycleOwner) {
            (binding.recyclerView.adapter as ItemAdapter).submitList(it)
        }
        listViewModel.snackBarErrorEvent.observe(viewLifecycleOwner) { errorState ->
            when (errorState) {
                ErrorState.NETWORK_ERROR -> binding.errorView.showSnackBar(ErrorConfiguration.NETWORK, listViewModel::refresh)
                ErrorState.DATA_ERROR -> binding.errorView.showSnackBar(ErrorConfiguration.BROKEN_DATA, listViewModel::refresh)
                else -> {
                    //nothing to do
                }
            }
        }
        listViewModel.fullScreenErrorState.observe(viewLifecycleOwner) { errorState ->
            binding.errorView.setErrorState(errorState)
        }
        listViewModel.showProgress.observe(viewLifecycleOwner) { isRefreshing ->
            binding.swipeRefresh.isRefreshing = isRefreshing
        }
    }
}