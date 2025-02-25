package com.project2.calculator.operation

// Interface for various binary operations (operations involving two numbers)
interface BinaryOperation {
    // calculates the operation given the two numbers
    fun calculate(a : Double, b : Double) : Double

    // provides the symbol of the operation (i.e. '+')
    fun getSymbol() : String
}