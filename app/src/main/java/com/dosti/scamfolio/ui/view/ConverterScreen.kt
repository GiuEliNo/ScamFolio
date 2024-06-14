package com.dosti.scamfolio.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dosti.scamfolio.R
import com.dosti.scamfolio.db.entities.CoinModelAPIDB
import com.dosti.scamfolio.ui.theme.custom
import com.dosti.scamfolio.viewModel.ConverterViewModel

@Composable
fun ConverterScreen(
    viewModel: ConverterViewModel
) {
    var firstField by rememberSaveable { viewModel.firstField }
    var secondField by rememberSaveable { viewModel.secondField }
    val cryptos = viewModel.coinList.collectAsState().value
    val choice1 by remember { viewModel.choice1 }
    val choice2 by remember {viewModel.choice2}

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    )  {
        Text(
            text = "Converter",
            fontSize = 50.sp,
            fontFamily = custom,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),

            modifier = Modifier
                .padding(10.dp)

        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                CryptoField(value = firstField,
                    onValueChange = { firstField = it },
                    viewModel,
                    ch = choice1,
                    cryptos,
                    isTop = true,
                    onChoiceChange = { viewModel.setChoice1(it) })

                Spacer(modifier = Modifier.height(25.dp))

                CryptoField(value = secondField,
                    onValueChange = { secondField = it },
                    viewModel,
                    ch = choice2,
                    cryptos,
                    isTop = false,
                    onChoiceChange = { viewModel.setChoice2(it) })
                Button(

                    onClick = {viewModel.calculate()},
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    //  border = BorderStroke(4.dp, Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)

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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoField(
    value: String,
    onValueChange: (String) -> Unit,
    viewModel: ConverterViewModel,
    ch: String,
    options: List<CoinModelAPIDB>?,
    isTop: Boolean,
    onChoiceChange: (String) -> Unit
) {
    var choice by rememberSaveable { mutableStateOf(ch) }
    var expanded by remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom,
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
                onValueChange = onValueChange,
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = MaterialTheme.colorScheme.inversePrimary,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                    unfocusedLabelColor = Color.Transparent,
                    focusedContainerColor = Color.DarkGray,
                    unfocusedContainerColor = Color.DarkGray
                ),
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
                LazyColumn(
                    modifier= Modifier
                        .width(500.dp)
                        .height(200.dp)
                ) {
                    if (options != null) {
                        items(options.size){index ->
                            val myCrypto= options[index]
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = myCrypto.name,
                                        color = Color.White
                                    )},
                                onClick = {
                                    choice=myCrypto.name
                                    expanded= false
                                    onChoiceChange(myCrypto.name)
                                }
                                )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(20.dp))

        val keyboardController = LocalSoftwareKeyboardController.current

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, start = 8.dp),
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = "Value", color = Color.White) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = MaterialTheme.colorScheme.inversePrimary,
                focusedLeadingIconColor = Color.White,
                unfocusedLeadingIconColor = Color.White,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = Color.Transparent,
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray
            ),
            singleLine = true,
            readOnly = isTop.not(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                autoCorrect = false,
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()},
            ),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
        )

        Spacer(modifier = Modifier.width(20.dp))
    }
}