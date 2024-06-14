package com.dosti.scamfolio.ui.view

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.dosti.scamfolio.R
import com.dosti.scamfolio.SharedPrefRepository
import com.dosti.scamfolio.api.model.CoinModelAPI
import com.dosti.scamfolio.ui.chart.Chart
import com.dosti.scamfolio.ui.theme.BackgroundGradient
import com.dosti.scamfolio.ui.theme.custom
import com.dosti.scamfolio.viewModel.CryptoScreenViewModel

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CryptoScreen(viewModel : CryptoScreenViewModel, coinName : String, navigateUp: () -> Unit, sharedPrefRepository: SharedPrefRepository) {

    var addQty by remember { mutableStateOf("0.0") }
    var removeQty by remember { mutableStateOf("0.0") }
    var toastEvent by remember { mutableStateOf(false) }
    var errorEvent by remember { mutableStateOf(false) }
    val coin by viewModel.coin.observeAsState()
    val username=viewModel.username

    LaunchedEffect(Unit) {
        viewModel.fetchCrypto(coinName)
    }

    Scaffold(
    ) { innerPadding ->
        BackgroundGradient()
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {

                    coin?.let { CryptoHeader(it) }
                    Spacer(modifier = Modifier.size(16.dp))
                    coin?.let { Chart(it.sparkline_in_7d.price, ) }
                }
            }

            item {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Spacer(modifier = Modifier.width(10.dp))
                        CustomButton(
                            onClick = {
                                try {
                                    viewModel.addPurchase(coinName, addQty.toDouble(), username, false)
                                    toastEvent = true
                                } catch (e: NumberFormatException) {
                                    errorEvent = true
                                }
                            },
                            text = stringResource(R.string.add_to_transactions)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        CustomTextField1(
                            value = addQty,
                            onValueChange = { addQty = it }
                        )
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Spacer(modifier = Modifier.width(10.dp))
                        CustomButton(
                            onClick = {
                                Log.d("test", removeQty)
                                try {
                                    viewModel.addPurchase(coinName, removeQty.toDouble(), username, true)
                                    toastEvent = true
                                } catch (e: NumberFormatException) {
                                    errorEvent = true
                                }
                            },
                            text = stringResource(R.string.remove_from_balance)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        CustomTextField2(
                            value = removeQty,
                            onValueChange = { removeQty = it }
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(20.dp))
                }

                if (toastEvent) {
                    Toast.makeText(LocalContext.current, "purchasing added!", Toast.LENGTH_SHORT).show()
                    toastEvent = false
                }

                if (errorEvent) {
                    Toast.makeText(LocalContext.current, "Not a number!", Toast.LENGTH_SHORT).show()
                    errorEvent = false
                }

            }
        }
        }
    }

    @Composable
    fun CustomTextField1(
        value: String,
        onValueChange: (String) -> Unit
    ) {
        val icon = @Composable {
            Icon(
                Icons.Default.Add,
                contentDescription = "",
                tint = Color.White
            )
        }

        val keyboardController = LocalSoftwareKeyboardController.current

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, start = 8.dp),
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                autoCorrect = false,
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()},
            ),
            label = { Text(text = "", color = Color.White) },
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
            leadingIcon = icon,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
        )
    }


    @Composable
    fun CustomTextField2(
        value: String,
        onValueChange: (String) -> Unit
    ) {
        val icon = @Composable {
            Icon(
                Icons.Default.Remove,
                contentDescription = "",
                tint = Color.White
            )
        }
        val keyboardController = LocalSoftwareKeyboardController.current

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, start = 8.dp),
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                autoCorrect = false,
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()},
            ),
            label = { Text(text = "", color = Color.White) },
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
            leadingIcon = icon,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
        )
    }

    @Composable
    fun CustomButton(
        onClick: () -> Unit,
        text: String
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
        //    border = BorderStroke(4.dp, Color.White),
            modifier = Modifier

        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = text,
                    color = Color.White
                )
            }

        }
    }


    @Composable
    fun CryptoHeader(coin: CoinModelAPI) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(coin.image),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                // .fillMaxWidth(),
            ) {
                Text(
                    text = coin.name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = coin.symbol,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.LightGray

                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.End
            ) {
                Row( modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = textPriceChange(coin.price_change_percentage_24h),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium,
                        color = changePercentColor(coin.price_change_percentage_24h),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = stringResource(R.string.today),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.W600,
                        color = Color.LightGray,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = coin.current_price + "€",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

fun textPriceChange(change: String): String {
    return if(change.substring(0,1) == "-")
        change.substring(0,5) + "%"
    else
        change.substring(0,4) + "%"
}

fun changePercentColor(change: String): Color {
    return if(change.substring(0,1) == "-")
        Color(0xFFEB4E3D)
    else
        Color(0xFF65C466)
}