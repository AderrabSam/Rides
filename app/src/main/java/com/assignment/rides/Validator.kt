package com.assignment.rides

object Validator {

     fun validInputVehicleValue(inputValue: Int): Boolean {
        return (inputValue in 1..100)
    }

    fun calculateCarbonEmission(kilometrage : Int): String{
        return if(kilometrage <= 5000){
            kilometrage.toString()
        }else{
            (kilometrage * 1.5).toInt().toString()
        }
    }
}