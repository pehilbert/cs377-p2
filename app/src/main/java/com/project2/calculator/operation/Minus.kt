package com.project2.calculator.operation

class Minus : BinaryOperation {
    override fun calculate(a: Double, b: Double): Double {
        return a - b
    }
}