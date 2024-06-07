package com.dosti.scamfolio.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelStoreOwner
import com.dosti.scamfolio.R
import com.dosti.scamfolio.ui.theme.custom
import com.dosti.scamfolio.viewModel.ConverterViewModel

@Composable
fun ConverterScreen(
    viewModel: ConverterViewModel
) {
    var firstField by rememberSaveable { viewModel.firstField }
    var secondField by rememberSaveable { viewModel.secondField }
    var expanded by remember { viewModel.expanded }
    val cryptos = viewModel.cryptos
    var choice by remember { viewModel.choice }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        Text(
            text = "Converter",
            fontSize = 50.sp,
            fontFamily = custom,
            color = Color.White
        )
        
        Spacer(modifier = Modifier.height(150.dp))

        CryptoField(value = firstField, onValueChange = {}, ch = choice, isTop = true)

        Spacer(modifier = Modifier.height(50.dp))

        CryptoField(value = secondField, onValueChange = {}, ch = choice, isTop = false)
        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4BC096)),
            border = BorderStroke(4.dp, Color.White),
            modifier = Modifier
                .size(300.dp, 80.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.calculate),
                    fontSize = 40.sp,
                    fontFamily = custom,
                    color = Color.White
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoField(
    value: String,
    onValueChange: (String) -> Unit,
    ch: String,
    options: List<String> = listOf(),
    isTop: Boolean
) {
    var choice by rememberSaveable { mutableStateOf(ch) }
    var expanded by remember { mutableStateOf(false) }
    val options by remember { mutableStateOf(options) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },

        ) {
            TextField(
                value = choice,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier
                    .menuAnchor()
                    .width(150.dp)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = !expanded },
                modifier = Modifier
                    .width(150.dp)
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Crypto2") },
                    onClick = {
                        choice = "Crypto2"
                        expanded = false
                    }
                )


                DropdownMenuItem(
                    text = { Text(text = "Crypto3") },
                    onClick = {
                        choice = "Crypto3"
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.width(20.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = "Value", color = Color.White) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                unfocusedLabelColor = Color.White
            ),
            singleLine = true,
            readOnly = isTop.not()
        )

        Spacer(modifier = Modifier.width(20.dp))
    }
}