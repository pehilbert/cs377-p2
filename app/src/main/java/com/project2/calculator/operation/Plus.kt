package com.project2.calculator.operation

class Plus : BinaryOperation {
    override fun calculate(a: Double, b: Double): Double {
        return a + b
    }
}