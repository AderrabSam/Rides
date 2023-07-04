package com.assignment.rides.vehicleList


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.assignment.rides.databinding.CardViewVehicleBinding
import com.assignment.rides.model.VehiclesResponse


class VehicleListViewAdapter(
    private val items: List<VehiclesResponse>
) :
    RecyclerView.Adapter<VehicleListViewAdapter.ViewHolder>() {

    class ViewHolder(val binding: CardViewVehicleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CardViewVehicleBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                binding.makeModelText.text = this.make_and_model
                binding.vinText.text = this.vin
            }
        }
        val item = items[position]
        holder.binding.layoutCardView.setOnClickListener { view ->
            val action =
                VehicleListFragmentDirections.actionVehicleListFragmentToVehicleDetailsFragment(
                    vin = item.vin,
                    makeAndModel = item.make_and_model,
                    color = item.color,
                    carType = item.car_type
                )
            view.findNavController().navigate(action)
        }


    }

}