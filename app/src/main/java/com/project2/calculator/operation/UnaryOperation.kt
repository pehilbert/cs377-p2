package com.project2.calculator.operation

interface UnaryOperation {
    fun calculate (x : Double) : Double
    fun getNotation(numberStr : String) : String
}