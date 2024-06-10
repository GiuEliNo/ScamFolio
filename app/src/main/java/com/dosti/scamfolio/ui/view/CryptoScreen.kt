package com.dosti.scamfolio.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.dosti.scamfolio.api.model.CoinModelAPI
import com.dosti.scamfolio.ui.chart.Chart
import com.dosti.scamfolio.viewModel.CryptoScreenViewModel

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CryptoScreen(viewModel : CryptoScreenViewModel, coinName : String, navigateUp: () -> Unit) {

    val coin by viewModel.coin.observeAsState()
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
                    coin?.let { Chart(it.sparkline_in_7d.price) }
                }
            }
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
            Text(
                text = coin.price_change_percentage_24h,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                color = Color.Green,
                textAlign = TextAlign.Center
            )
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