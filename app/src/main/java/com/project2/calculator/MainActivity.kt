package com.project2.calculator

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project2.calculator.operation.BinaryOperation
import com.project2.calculator.operation.CalculatorException
import com.project2.calculator.operation.Divide
import com.project2.calculator.operation.Minus
import com.project2.calculator.operation.Plus
import com.project2.calculator.operation.Square
import com.project2.calculator.operation.SquareRoot
import com.project2.calculator.operation.Times
import com.project2.calculator.operation.UnaryOperation

class MainActivity : AppCompatActivity() {
    private var currentNumberStr : String = "0"
    private var currentBinOperation : BinaryOperation? = null
    private var prevNumberStr : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        currentNumberStr = savedInstanceState?.getString("currentNumber") ?: "0"

        val numberDisplay: TextView = findViewById(R.id.number)
        numberDisplay.text = currentNumberStr

        /* This section of code was written with the assistance of GPT 4o */

        // Get number buttons
        val allButtons : ViewGroup = findViewById(R.id.buttons)
        val numberButtons = (0 until allButtons.childCount)
            .map { allButtons.getChildAt(it) }
            .filterIsInstance<Button>()
            .filter { it.tag == "numInput" }

        // Set button listeners for each number button to update number
        numberButtons.forEach { button ->
            button.setOnClickListener {
                appendNumberStr(button.text[0])
            }
        }

        /* End of AI-assisted code */

        // Invert button (positive or negative)
        val invertButton : Button = findViewById(R.id.invert)
        invertButton.setOnClickListener {
            invertNumberStr()
        }

        // Clear button
        val clearButton : Button = findViewById(R.id.clear)
        clearButton.setOnClickListener {
            clearNumberStr()
        }

        // Equals button
        val equalsButton: Button = findViewById(R.id.equals)
        equalsButton.setOnClickListener {
            if (currentBinOperation != null && prevNumberStr != null) {
                performBinaryOperation()
            }
        }

        /* Unary Operations */

        // Square root button
        val sqrtButton : Button = findViewById(R.id.sqrt)
        sqrtButton.setOnClickListener {
            performUnaryOperation(SquareRoot())
        }

        // Square button
        val squareButton : Button = findViewById(R.id.square)
        squareButton.setOnClickListener {
            performUnaryOperation(Square())
        }

        /* Binary Operations */

        // Plus button
        val plusButton: Button = findViewById(R.id.plus)
        plusButton.setOnClickListener {
            setupBinaryOperation(Plus())
        }

        // Minus button
        val minusButton: Button = findViewById(R.id.minus)
        minusButton.setOnClickListener {
            setupBinaryOperation(Minus())
        }

        // Times button
        val timesButton: Button = findViewById(R.id.times)
        timesButton.setOnClickListener {
            setupBinaryOperation(Times())
        }

        // Divide button
        val divideButton: Button = findViewById(R.id.divide)
        divideButton.setOnClickListener {
            setupBinaryOperation(Divide())
        }
    }

    private fun appendNumberStr(newChar : Char) {
        val numberDisplay: TextView = findViewById(R.id.number)

        if (newChar == '.') {
            // Make sure there is only one decimal point in the number
            if (!currentNumberStr.contains('.')) {
                currentNumberStr += newChar
            }
        } else {
            if (currentNumberStr == "0") {
                currentNumberStr = "${newChar}"
            } else {
                currentNumberStr += newChar
            }
        }

        numberDisplay.text = currentNumberStr
    }

    private fun invertNumberStr() {
        val numberDisplay: TextView = findViewById(R.id.number)

        // Check if number is decimal or not to avoid unwanted decimal points
        try {
            currentNumberStr = if (currentNumberStr.contains('.')) {
                (currentNumberStr.toDouble() * -1).toString()
            } else {
                (currentNumberStr.toInt() * -1).toString()
            }
        } catch (e : Exception) {
            clearNumberStr()
            Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show()
        }

        numberDisplay.text = currentNumberStr
    }

    private fun clearNumberStr() {
        val numberDisplay: TextView = findViewById(R.id.number)
        currentNumberStr = "0"
        numberDisplay.text = currentNumberStr
    }

    private fun performUnaryOperation(operation: UnaryOperation) {
        val numberDisplay: TextView = findViewById(R.id.number)
        val previousCalc: TextView = findViewById(R.id.previous_calc)

        try {
            val previousCalcStr = "${operation.getNotation(currentNumberStr)} ="
            val result : Double = operation.calculate(currentNumberStr.toDouble())

            currentNumberStr = result.toString()

            previousCalc.text = previousCalcStr
            numberDisplay.text = currentNumberStr
        } catch (e : CalculatorException) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        } catch (e : Exception) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBinaryOperation(operation: BinaryOperation) {
        val numberDisplay: TextView = findViewById(R.id.number)
        val previousCalc: TextView = findViewById(R.id.previous_calc)
        val previousCalcStr = "${currentNumberStr} ${operation.getSymbol()}"

        if (prevNumberStr == null && currentBinOperation == null) {
            prevNumberStr = currentNumberStr
            currentBinOperation = operation
            currentNumberStr = "0"

            previousCalc.text = previousCalcStr
            numberDisplay.text = currentNumberStr
        }
    }

    private fun performBinaryOperation() {
        val numberDisplay: TextView = findViewById(R.id.number)
        val previousCalc : TextView = findViewById(R.id.previous_calc)

        try {
            val previousCalcStr = "${prevNumberStr} ${currentBinOperation!!.getSymbol()} ${currentNumberStr} ="
            val result : Double = currentBinOperation!!.calculate(prevNumberStr!!.toDouble(), currentNumberStr.toDouble())

            currentNumberStr = result.toString()
            prevNumberStr = null
            currentBinOperation = null

            previousCalc.text = previousCalcStr
            numberDisplay.text = currentNumberStr
        } catch (e : CalculatorException) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        } catch (e : Exception) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}
