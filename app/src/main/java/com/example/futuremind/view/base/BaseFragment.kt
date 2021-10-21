package com.example.futuremind.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

/**
 * Base fragment to use with ViewBinding
 * Example of usage:
 *
 *          class ExampleFragment : BaseFragment<FragmentExampleBinding>() {
 *
 *              override val viewBinding: FragmentExampleBinding
 *              get() = FragmentExampleBinding.inflate(layoutInflater)
 *
 *              ...
 *              }
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    protected abstract val viewBinding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = viewBinding
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    protected fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    protected fun navigateUp() {
        findNavController().navigateUp()
    }
}