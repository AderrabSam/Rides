package com.assignment.rides.vehicleDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.assignment.rides.databinding.FragmentVehicleDetailsBinding

class VehicleDetailsFragment : Fragment() {
    private var _binding: FragmentVehicleDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var vinValue: String
    private lateinit var makeModel: String
    private lateinit var color: String
    private lateinit var carType: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        arguments?.let {
            vinValue = it.getString("vin").toString()
            makeModel = it.getString("makeAndModel").toString()
            color = it.getString("color").toString()
            carType = it.getString("carType").toString()
        }
        _binding = FragmentVehicleDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vinText.text = vinValue
        binding.makeAndModelText.text = makeModel
        binding.ColorText.text = color
        binding.carTypeText.text = carType
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}