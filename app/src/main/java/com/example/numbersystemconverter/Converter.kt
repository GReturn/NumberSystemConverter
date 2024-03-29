package com.example.numbersystemconverter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.math.BigInteger

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Converter(radixFrom: String , radixTo: String, modifier: Modifier = Modifier) {

    var inputFieldState by remember { mutableStateOf("") }
    var outputFieldState by remember { mutableStateOf("") }

    var textLabelInput = when {
        radixFrom == "BIN" -> "binary"
        radixFrom == "OCT" -> "octal"
        radixFrom == "DEC" -> "decimal"
        radixFrom == "HEX" -> "hexadecimal"
        else -> "Error"
    }

    var isValidNumber by remember { mutableStateOf(false) }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = inputFieldState,
            onValueChange = {
                inputFieldState = it
                isValidNumber = detectNumberType(inputFieldState, radixFrom)
            },
            label = { Text("Input $textLabelInput number") },
            singleLine = true,
            supportingText = {
                if(!isValidNumber) {
                    Text(text = "Enter a valid number")
                }
            },
            isError = !isValidNumber
        )
        Button(
            modifier = Modifier
                .size(250.dp, 120.dp)
                .padding(30.dp),
            contentPadding = PaddingValues(8.dp),
            enabled = isValidNumber,
            onClick = {
                outputFieldState = convertRadix(inputFieldState, radixFrom, radixTo)
            }
        ) {
            Text(text="Convert", fontSize = 20.sp)
        }
        Text("Result: $outputFieldState",
            fontSize = 27.sp,
            maxLines = 5,
            modifier = Modifier.padding(8.dp)
        )
    }
}

fun detectNumberType(input: String, radix: String): Boolean {
    return when {
        radix == "BIN" && input.matches(Regex("[01]+")) -> true             // check BIN
        radix == "OCT" && input.matches(Regex("[0-7]+")) -> true            // OCT
        radix == "DEC" && input.matches(Regex("[0-9]+")) -> true            // DEC
        radix == "HEX" && input.matches(Regex("[0-9A-Fa-f]+")) -> true      // HEX
        else -> false
    }
}

fun convertRadix(number: String, fromRadix: String, toRadix: String): String {
    val parsedNumber = when (fromRadix) {
        "BIN" -> BigInteger(number, 2)
        "OCT" -> BigInteger(number, 8)
        "DEC" -> BigInteger(number)
        "HEX" -> BigInteger(number, 16)
        else -> throw IllegalArgumentException("Invalid fromRadix: $fromRadix")
    }
    val binaryString = parsedNumber.toString(2)

    // Group the binary string into sets of 4 characters with spaces in between
    val groupedBinaryString = groupBinaryString(binaryString)

    return when (toRadix) {
        "BIN" -> groupedBinaryString
        "OCT" -> parsedNumber.toString(8)
        "DEC" -> parsedNumber.toString()
        "HEX" -> parsedNumber.toString(16).uppercase()
        else -> throw IllegalArgumentException("Invalid toRadix: $toRadix")
    }
}
fun groupBinaryString(binaryString: String): String {
    val stringBuilder = StringBuilder()
    var padding = 4 - binaryString.length % 4                                       // Calculate how many leading zeros to add

    if (padding == 4) padding = 0                                                   // If the length is already a multiple of 4, no padding needed

    for (i in 0 until padding) {
        stringBuilder.append('0')                                                   // Add leading zeros
    }
    stringBuilder.append(binaryString)                                              // Append the original binary string
    val result = stringBuilder.toString()

    // Insert spaces every 4 characters
    val spacedResult = StringBuilder(result)
    var index = spacedResult.length - 4

    while (index > 0) {
        spacedResult.insert(index, ' ')
        index -= 4
    }

    return spacedResult.toString()
}