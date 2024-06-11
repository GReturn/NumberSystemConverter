package com.example.numbersystemconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numbersystemconverter.ui.theme.NumberSystemConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberSystemConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NumberConverter()
                }
            }
        }
    }
}
@Composable
fun NumberConverter(modifier: Modifier = Modifier) {
    val radix = listOf("BIN", "OCT", "DEC", "HEX")

    var selectedRadixFrom by remember { mutableStateOf(radix[0]) }
    var selectedRadixTo by remember { mutableStateOf(radix[2]) }

    val fontSize = 20.sp

    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .padding(top = 32.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text="Number Conversion System", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(text="Made with ❤ by Lindrew", fontSize = fontSize)
        }
        Row (Modifier.padding(10.dp)) {
            Dropdown(
                radix = radix
            ) { radixFrom, radixTo ->
                selectedRadixFrom = radixFrom
                selectedRadixTo = radixTo
            }
        }
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Converter(radixFrom = selectedRadixFrom, radixTo = selectedRadixTo)
            Text(text="From $selectedRadixFrom to $selectedRadixTo", fontSize = 18.sp)
        }
    }
}

// TODO: Issue:When change in dropdown (radixFROM ) OutlinedTextField does not change

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    NumberSystemConverterTheme {
        NumberConverter()
    }
}
