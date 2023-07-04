package com.assignment.rides.vehicleDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.assignment.rides.R
import com.assignment.rides.Validator
import com.assignment.rides.databinding.FragmentVehicleDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.properties.Delegates

class VehicleDetailsFragment : Fragment() {
    private var _binding: FragmentVehicleDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var vinValue: String
    private lateinit var makeModel: String
    private lateinit var color: String
    private lateinit var carType: String
    private var kilometrage by Delegates.notNull<Int>()
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
            kilometrage = it.getInt("kilometrage")
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
        binding.carbonEmissionsButton.setOnClickListener {
            showBottomSheet()
        }

    }

    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(R.layout.bottom_sheet_dialog)
        val carbonEmissionValue = dialog.findViewById<TextView>(R.id.carbonEmissionsValue)
        carbonEmissionValue?.text = Validator.calculateCarbonEmission(kilometrage)
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}