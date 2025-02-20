package com.project2.calculator.operation

import kotlin.math.sqrt

class SquareRoot : UnaryOperation {
    override fun calculate(x: Double): Double {
        if (x < 0) {
            throw CalculatorException("Cannot square root a negative number")
        }

        return sqrt(x)
    }
}