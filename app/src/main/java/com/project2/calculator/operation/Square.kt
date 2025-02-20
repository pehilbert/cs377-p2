package com.project2.calculator.operation

class Square : UnaryOperation {
    override fun calculate(x: Double): Double {
        return x * x
    }

    override fun getNotation(numberStr: String): String {
        return "$numberStr²"
    }
}