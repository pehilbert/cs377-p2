package com.project2.calculator.operation

// interface for unary operations (operations with only one number)
interface UnaryOperation {
    // calculates the operation given the number
    fun calculate (x : Double) : Double

    // gets string representing the operation that was done (i.e. "sqrt(5)")
    fun getNotation(numberStr : String) : String
}