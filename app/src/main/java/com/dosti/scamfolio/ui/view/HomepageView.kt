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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dosti.scamfolio.R
import com.dosti.scamfolio.SharedPrefRepository
import com.dosti.scamfolio.db.entities.Purchasing
import com.dosti.scamfolio.ui.chart.PieChartBalance
import com.dosti.scamfolio.ui.theme.custom
import com.dosti.scamfolio.viewModel.HomepageViewModel


@Composable
fun Welcome(
    viewModel: HomepageViewModel,
) {

    val username=viewModel.username
    val balance = viewModel.balance.collectAsState().value
    val transactions= viewModel.transactions.collectAsState().value
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()

    ) {
        TopLabel(username = username)
        Spacer(modifier = Modifier.height(10.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                PieChartBalance(viewModel)

                BalanceText(balance = viewModel.roundBalance(balance).toString())
            }
        }
        Spacer(modifier=Modifier.height(10.dp))
        Text(
            text = "Transactions: ",
            fontSize = 25.sp,
            fontFamily = custom,
            color = Color.White
        )
        Transactions(transactions)
    }
}

@Composable
fun TopLabel(
    username: String
) {
    Text(
        text = stringResource(R.string.welcome_back),
        fontSize = 50.sp,
        fontFamily = custom,
        color = Color.White,
    )
    Text(
        text = username,
        fontSize = 50.sp,
        fontFamily = custom,
        color = Color.White,
    )
}

@Composable
fun BalanceText(
    balance : String,
) {
    Row {
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Current balance: $balance$",
                fontSize = 30.sp,
                fontFamily = custom,
                color = Color.White,
            )

        }
    }
}

@Composable
fun Transactions(
    transactions: List<Purchasing>
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(
            items = transactions
        ) { purchasing ->
            SingleTransaction(purchasing = purchasing)
        }
    }


}

@Composable
fun SingleTransaction(purchasing: Purchasing) {
    var color = if (purchasing.isNegative) {
        Color.Red
    } else {
        Color(0xFF4BC096)
    }
    var negative = purchasing.isNegative
    var quantity = if (negative) {
        "-" + purchasing.quantity.toString() + "$"
    } else {
        "+" + purchasing.quantity.toString() + "$"
    }



    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = purchasing.coinName,
                fontSize = 30.sp,
                fontFamily = custom,
                color = color
            )
            Spacer(modifier = Modifier.width(160.dp))

            Text(
                text = quantity,
                fontSize = 30.sp,
                fontFamily = custom,
                color = color
            )

            Spacer(modifier = Modifier.width(15.dp))
        }
    }
}