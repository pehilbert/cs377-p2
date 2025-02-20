package com.project2.calculator.operation

interface BinaryOperation {
    fun calculate(a : Double, b : Double) : Double
    fun getSymbol() : String
}