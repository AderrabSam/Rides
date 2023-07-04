package com.assignment.rides.vehicleList

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.rides.databinding.FragmentVehicleListBinding
import com.assignment.rides.model.ApiCall
import com.assignment.rides.model.Repository


class VehicleListFragment : Fragment() {
    private var _binding: FragmentVehicleListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: VehicleListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentVehicleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiCall = ApiCall.getInstance()
        val repository = Repository(apiCall)
        viewModel = ViewModelProvider(
            this,
            VehicleListViewModelFactory(repository)
        )[VehicleListViewModel::class.java]

        setupObservers()
        binding.buttonVehicle.setOnClickListener {
            val inputValue = binding.inputSizeVehicle.text
            viewModel.getVehicle(inputValue.toString().toInt())
            closeKeyboard()
        }

    }

    private fun closeKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusView = requireActivity().currentFocus
        if (currentFocusView != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocusView.windowToken, 0)
        }
    }

    private fun setupObservers() {
        viewModel.listVehicles.observe(viewLifecycleOwner) { it ->
            if (it != null) {
                val adapter = VehicleListViewAdapter(it.sortedBy { it.vin })
                binding.recycleViewList.also {
                    it.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    it.setHasFixedSize(true)
                    it.adapter = adapter
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

