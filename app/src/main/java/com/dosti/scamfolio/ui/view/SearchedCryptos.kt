package com.dosti.scamfolio.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import coil.compose.rememberAsyncImagePainter
import com.dosti.scamfolio.R
import com.dosti.scamfolio.api.model.CoinModelAPI
import com.dosti.scamfolio.viewModel.SearchedCryptosViewModel
import com.dosti.scamfolio.viewModel.ViewModelFactory
import kotlinx.coroutines.delay


@Composable
fun SearchedCryptos(
    viewModelStoreOwner: ViewModelStoreOwner,
    factory: ViewModelFactory
) {
    val viewModel = ViewModelProvider(viewModelStoreOwner, factory)[SearchedCryptosViewModel::class.java]

    val coinList by viewModel.coinList.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAllCryptos()
    }

    var search by rememberSaveable() { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        TopBar(value = search, onChange = { search = it }, onClick = { /* TODO */ })
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Spacer(modifier = Modifier.size(8.dp))
            }

            coinList?.forEach {
                item {
                    Card(colors = CardDefaults.cardColors(
                        containerColor = Color.Black,
                    ),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {

                        CoinItem(it)
                        Spacer(modifier = Modifier.size(16.dp))
                    }

                }
            }

            item {
                Spacer(modifier = Modifier.size(32.dp))
            }
        }
    }
}
@Composable
fun CoinItem(it: CoinModelAPI) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(it.image),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = it.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Card(
                    shape = MaterialTheme.shapes.extraSmall,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                }
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = it.symbol,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }

        Column(
            modifier = Modifier.width(IntrinsicSize.Max),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {

            Text(
                modifier = Modifier
                    .width(IntrinsicSize.Max),
                text = it.current_price + "€",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                maxLines = 1
            )

            Spacer(modifier = Modifier.padding(1.dp))

            Card(
                modifier = Modifier
                    .requiredWidth(72.dp),
                shape = MaterialTheme.shapes.small,
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = it.price_change_percentage_24h + "%",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 1.dp)
                        .align(Alignment.End),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    maxLines = 1
                )
            }

        }

    }

}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    value: String,
    onChange: (String) -> Unit,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.width(50.dp))
        TextField(
            value = value,
            onValueChange = onChange,
            label = { Text(text = stringResource(R.string.search), color = Color.White) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedLabelColor = Color.White
            )
        )
        
        Spacer(modifier = Modifier.width(20.dp))
        
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                tint = Color.White
            )
        }
    }
}
/*
@Composable
fun Content(coinList : MutableLiveData<MutableList<CoinModelAPI>>) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        LazyColumn(
            contentPadding = PaddingValues(5.dp)
        ) {
            items(coinList){

            }
        }
    }
}


}


}

@Composable
fun Crypto(coin: CoinModelAPI, modifier: Modifier, price : Float) {
    Text(
        text = coin.name,
        modifier = modifier,
        textAlign = TextAlign.Start,
        fontSize = 10.sp,
        fontStyle = FontStyle.Normal,
        fontFamily = custom
    )
}*/