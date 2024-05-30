package com.dosti.scamfolio.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dosti.scamfolio.R
import com.dosti.scamfolio.db.entities.Coin
import com.dosti.scamfolio.ui.theme.custom

@Composable
fun SearchedCryptos() {
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

@Composable
fun Content(
    cryptos: List<Coin>
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(200.dp, 300.dp)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(5.dp)
        ) {
            items(cryptos) {
            
            }
        }
    }
}

@Composable
fun Crypto(name: String, modifier: Modifier, price : Float) {
    Text(
        text = name,
        modifier = modifier,
        textAlign = TextAlign.Start,
        fontSize = 10.sp,
        fontStyle = FontStyle.Normal,
        fontFamily = custom
    )
}