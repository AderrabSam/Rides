package com.assignment.rides

import org.junit.Assert.*
import org.junit.Test


class ValidatorTest {
    @Test
    fun inputValidate() {
        val inputSize = 43
        val result = Validator.validInputVehicleValue(inputSize)
        assertTrue(result)
    }

    @Test
    fun invalidInput() {
        val inputSize = 120
        val result = Validator.validInputVehicleValue(inputSize)
        assertFalse(result)
    }

    @Test
    fun calculateCarbonEmiss() {
        val kilom = 50006
        val result = Validator.calculateCarbonEmission(kilom)
        // assertEquals("first 5000km travelled", "5000",result)
        assertEquals("first 5000km travelled", (kilom * 1.5).toInt().toString(), result)
    }
}