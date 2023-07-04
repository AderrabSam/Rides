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
import com.assignment.rides.LoadingDialog
import com.assignment.rides.Validator
import com.assignment.rides.databinding.FragmentVehicleListBinding
import com.assignment.rides.model.ApiCall
import com.assignment.rides.model.Repository
import com.assignment.rides.model.VehiclesResponse



class VehicleListFragment : Fragment() {
    private var _binding: FragmentVehicleListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: VehicleListViewModel
    private lateinit var dialog: LoadingDialog
    private lateinit var dataList: MutableList<VehiclesResponse>
    private lateinit var adapter: VehicleListViewAdapter
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
        dialog = LoadingDialog(requireContext())
        setupObservers()
        binding.buttonVehicle.setOnClickListener {
            validInputVehicle()

        }

        binding.container.setOnRefreshListener {
            viewModel.getNewVehicle(dataList.size)
        }

    }

    private fun validInputVehicle() {
        val inputValue = binding.inputSizeVehicle.text.toString().toInt()
        if (Validator.validInputVehicleValue(inputValue)) {
            binding.inputLayout.helperText = null
            closeKeyboard()
            viewModel.getVehicle(inputValue)
        } else {
            binding.inputLayout.helperText = "Value must be between 1 and 100"
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
                dataList = it.sortedBy { it.vin }.toMutableList()
                adapter = VehicleListViewAdapter(dataList)
                binding.recycleViewList.also {
                    it.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    it.setHasFixedSize(true)
                    it.adapter = adapter
                }
            }

        }
        viewModel.newListVehicles.observe(viewLifecycleOwner) { it ->
            if (it != null) {
                adapter.clear()
                adapter.addAll(it.sortedBy { it.vin })
                binding.container.isRefreshing = false

            }
        }
        viewModel.showLoading.observe(viewLifecycleOwner) {
            if (it) {
                dialog.startLoadingDialog()
            }
        }
        viewModel.hideLoading.observe(viewLifecycleOwner) {
            if (it) {
                dialog.dismissDialog()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

