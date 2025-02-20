package com.project2.calculator.operation

class Divide : BinaryOperation {
    override fun calculate(a: Double, b: Double): Double {
        if (b == 0.0) {
            throw CalculatorException("Cannot divide by 0")
        }

        return a / b
    }

    override fun getSymbol(): String {
        return "÷"
    }
}