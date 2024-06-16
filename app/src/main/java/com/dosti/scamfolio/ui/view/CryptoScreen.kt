package com.dosti.scamfolio.ui.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.dosti.scamfolio.R
import com.dosti.scamfolio.api.model.CoinModelAPI
import com.dosti.scamfolio.ui.chart.Chart
import com.dosti.scamfolio.ui.theme.BackgroundGradient
import com.dosti.scamfolio.viewModel.CryptoScreenViewModel
import androidx.compose.runtime.State


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CryptoScreen(viewModel : CryptoScreenViewModel, coinName : String, navigateUp: () -> Unit) {

    val coin by viewModel.coin.observeAsState()

    val showDialog = remember { mutableStateOf(false) }

    val username=viewModel.username

    if (showDialog.value) {
        DialogOpenPosition(username, viewModel, coinName, showDialog)
    }

    LaunchedEffect(Unit) {

        viewModel.fetchCrypto(coinName)
        viewModel.getWalletCoin(coinName)
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {showDialog.value = true},
                icon = { Icon(Icons.Filled.Wallet, "Add position") },
                text = { Text(text = stringResource(R.string.addposition)) },
                contentColor = Color.White,
                containerColor = MaterialTheme.colorScheme.secondary
            )
        }

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
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                    ){

                        coin?.let { CryptoHeader(it) }
                        coin?.let { Chart(it.sparkline_in_7d.price, ) }
                        Text(
                            text = stringResource(R.string.lastupdate) + " : " + coin?.let {
                                viewModel.getLastUpdate(
                                    it
                                )
                            },
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.W600,
                            color = Color.LightGray,
                            textAlign = TextAlign.Center)
                    }

                }
            }
            item {
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = stringResource(R.string.titleportfoliocoindetial),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )
            }
             item {
                 Card(
                     colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                     modifier = Modifier
                         .fillMaxSize()
                         .padding(end = 10.dp, start = 10.dp)
                 ) {
                     Column(
                         modifier = Modifier
                             .padding(horizontal = 8.dp)
                     ) {
                         coin?.let {
                            WalletInfo(coin!!, viewModel) }

                     }
                 }
             }

             item {
                 Text(
                     modifier = Modifier.padding(start = 20.dp),
                     text = stringResource(R.string.marketdata),
                     textAlign = TextAlign.Start,
                     fontWeight = FontWeight.Medium,
                     color = Color.White,
                     style = MaterialTheme.typography.titleLarge
                 )
             }

            item {
                Card(
                 colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 10.dp, start = 10.dp)
                )
                { Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                ) {
                    coin?.let {
                        InfoMarketData(
                            name = stringResource(R.string.marketcap),
                            value = coin!!.market_cap + " $",
                            false
                        )

                        InfoMarketData(
                            name = stringResource(R.string.high24h),
                            value = coin!!.high_24h + " $",
                            false)

                        InfoMarketData(
                            name = stringResource(R.string.low_24h),
                            value = coin!!.low_24h + " $",
                            false)

                        InfoMarketData(
                            name = stringResource(R.string.circulating_supply),
                            value = coin!!.circulating_supply,
                            false)

                        InfoMarketData(
                            name = stringResource(R.string.total_supply),
                            value = coin!!.total_supply,
                            true)
                    }
                }

                }

            }
        }
    }
}

@Composable
fun WalletInfo(coin : CoinModelAPI, viewModel : CryptoScreenViewModel) {

    val walletCoin by viewModel.walletCoin.observeAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = coin.name,
            color = Color.LightGray,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.size(8.dp))

        (if(walletCoin.isNullOrBlank()) "0" else walletCoin)?.let {
            Text(
                text = it,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}

@Composable
fun InfoMarketData(name: String, value: String, lastValue: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            color = Color.LightGray,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = value,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }

    if (!lastValue) {
        Divider(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .alpha(.2f),
            color = Color.LightGray
        )
    }

}

@Composable
fun DialogOpenPosition(
    username: String,
    viewModel: CryptoScreenViewModel,
    coinName: String,
    showDialog: MutableState<Boolean>
) {

    val checkIfCanRemove = {
        viewModel.checkIfCanRemove(coinName)
    }

    var toastEvent by remember { mutableStateOf(false) }
    var errorEvent by remember { mutableStateOf(false) }

    val currentValue = viewModel.value.collectAsStateWithLifecycle(lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current)


    val enabled by remember(currentValue.value) {
        mutableStateOf(viewModel.checkIfCanRemove(coinName))
    }



    Dialog(onDismissRequest = { showDialog.value = false } ){
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary,),
            modifier = Modifier
                .padding(end = 10.dp, start = 10.dp)
        ) {
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
                    AddQuantityTextField(
                        value = currentValue,
                        callback = checkIfCanRemove,
                        viewModel = viewModel
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                    ) {
                    CustomButton(
                        onClick = {
                            try {
                                viewModel.addPurchase(coinName, username, false)
                                toastEvent = true
                            } catch (e: NumberFormatException) {
                                errorEvent = true
                            }
                        },
                        text = stringResource(R.string.add_to_transactions),
                        enabled = true
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    CustomButton(
                        onClick = {
                            try {
                                viewModel.addPurchase(coinName, username, true)
                                toastEvent = true
                            } catch (e: NumberFormatException) {
                                errorEvent = true
                            }
                        },
                        text = stringResource(R.string.remove_from_balance),
                        enabled = enabled
                    )
                }
            }

            if (toastEvent) {
                Toast.makeText(LocalContext.current, "purchasing added!", Toast.LENGTH_SHORT).show()
                toastEvent = false
                showDialog.value = false

            }

            if (errorEvent) {
                Toast.makeText(LocalContext.current, "Not a number!", Toast.LENGTH_SHORT).show()
                errorEvent = false
                showDialog.value = false
            }

        }
    }
}

@Composable
    fun AddQuantityTextField(
    value: State<String>,
    callback: () -> Any,
    viewModel: CryptoScreenViewModel,

    ) {
        val keyboardController = LocalSoftwareKeyboardController.current

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, start = 8.dp),
            value = value.value,
            placeholder = {
                Text(text = stringResource(R.string.insertValue))},
            onValueChange = { input ->
                var dotCount = 0
                val filtered = input.filter {
                    if (it.isDigit()) {
                        true
                    } else if (it == '.' && dotCount < 1) {
                        dotCount++
                        true
                    } else {
                        false
                    }
                }
                viewModel.setValue(filtered)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                autoCorrect = false,
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onAny = {callback()}
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
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
        )
    }

    @Composable
    fun CustomButton(
        onClick: () -> Unit,
        text: String,
        enabled: Boolean
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            modifier = Modifier,
            enabled = enabled

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
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row( modifier = Modifier
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
                        style = MaterialTheme.typography.headlineMedium,
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
            }
            Row(
                modifier = Modifier
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