package com.dosti.scamfolio.ui.view

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter
import com.dosti.scamfolio.R
import com.dosti.scamfolio.api.model.CoinModelAPI
import com.dosti.scamfolio.ui.theme.custom
import com.dosti.scamfolio.viewModel.CryptoScreenViewModel


val data = """
    <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TradingView Widget</title>
</head>
<body style="margin:0;padding:0;overflow:hidden;">
    <!-- TradingView Widget BEGIN -->
    <div class="tradingview-widget-container" style="height:100%;width:100%">
        <div class="tradingview-widget-container__widget" style="height:calc(100% - 32px);width:100%"></div>
        <div class="tradingview-widget-copyright">
            <a href="https://www.tradingview.com/" rel="noopener nofollow" target="_blank">
            </a>
        </div>
        <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-advanced-chart.js" async>
        {
            "autosize": true,
            "symbol": "BITSTAMP:BTCUSD",
            "interval": "D",
            "timezone": "Etc/UTC",
            "theme": "dark",
            "style": "3",
            "locale": "en",
            "hide_top_toolbar": true,
            "hide_legend": true,
            "allow_symbol_change": false,
            "save_image": false,
            "calendar": false,
            "hide_volume": true,
            "support_host": "https://www.tradingview.com"
        }
        </script>
    </div>
    <!-- TradingView Widget END -->
</body>
</html>

""".trimIndent()


@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoScreen(viewModel : CryptoScreenViewModel, coinName : String, navigateUp: () -> Unit) {

    Log.d("porcodio", coinName)

    val coin by viewModel.coin.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchCrypto(coinName)
    }
    var addQty by remember { mutableStateOf(0.0) }
    var removeQty by remember { mutableStateOf(0.0) }

    Scaffold(
    ) { innerPadding ->
        BackgroundGradient()
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Card(colors = CardDefaults.cardColors(
                    containerColor = Color.Black,
                ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {

                    coin?.let { CryptoHeader(it) }
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }

            item {
                Card(colors = CardDefaults.cardColors(
                    containerColor = Color.Black,
                ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    AndroidView(factory = {
                        WebView(it).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            setWebChromeClient(WebChromeClient())
                            webViewClient = WebViewClient()
                            settings.defaultTextEncodingName = "utf-8"
                            settings.javaScriptEnabled = true
                            settings.loadWithOverviewMode = true
                            settings.useWideViewPort = true
                            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                            settings.javaScriptCanOpenWindowsAutomatically = true
                            settings.cacheMode = WebSettings.LOAD_NO_CACHE
                            loadDataWithBaseURL(null, data, "text/html", "utf-8", null)
                        }
                    }

                    )
                }
            }

            item {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(50.dp))
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.width(10.dp))
                        CustomButton(onClick = { /*TODO*/ }, text = stringResource(R.string.add_to_transactions))
                        Spacer(modifier = Modifier.width(20.dp))
                        CustomTextField1(value = (addQty.toString()))
                    }

                    Spacer(modifier = Modifier.height(50.dp))

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.width(10.dp))
                        CustomButton(onClick = { /*TODO*/ }, text = stringResource(R.string.remove_from_balance))
                        Spacer(modifier = Modifier.width(20.dp))
                        CustomTextField2(value = (removeQty.toString()))
                    }
                }
            }
        }



    }



}

@Composable
fun CustomTextField1(
    value: String,
) {
    val icon = @Composable {
        Icon(
            Icons.Default.Add,
            contentDescription = "",
            tint = Color.White
        )
    }

    var _value by remember { mutableStateOf(value) }

    TextField(
        value = _value,
        onValueChange = { _value = it },
        label = { Text(text = "", color = Color.White) },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            unfocusedLabelColor = Color.White
        ),
        singleLine = true,
        leadingIcon = icon
    )
}


@Composable
fun CustomTextField2(
    value: String
) {
    var _value by remember{ mutableStateOf(value) }
    val icon = @Composable {
        Icon(
            Icons.Default.Remove,
            contentDescription = "",
            tint = Color.White
        )
    }

    TextField(
        value = _value,
        onValueChange = { _value = it },
        label = { Text(text = "", color = Color.White) },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            unfocusedLabelColor = Color.White
        ),
        singleLine = true,
        leadingIcon = icon
    )
}
@Composable
fun CustomButton(
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4BC096)),
        border = BorderStroke(4.dp, Color.White),
        modifier = Modifier
            .size(100.dp, 50.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                fontSize = 10.sp,
                fontFamily = custom,
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
                color = Color.White

            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            coin.price_change_percentage_24h?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.Green,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = coin.current_price + "€",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}