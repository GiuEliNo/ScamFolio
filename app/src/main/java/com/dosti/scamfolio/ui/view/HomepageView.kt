package com.dosti.scamfolio.ui.view

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.dosti.scamfolio.R
import com.dosti.scamfolio.db.entities.Purchasing
import com.dosti.scamfolio.ui.chart.PieChartBalance
import com.dosti.scamfolio.ui.theme.custom
import com.dosti.scamfolio.viewModel.HomepageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun Welcome(
    viewModel: HomepageViewModel
) {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            WelcomePortraitLayout(viewModel = viewModel)
        }
        Configuration.ORIENTATION_LANDSCAPE -> {
            WelcomeLandscapeLayout(viewModel = viewModel)
        }
        Configuration.ORIENTATION_UNDEFINED -> {
            WelcomePortraitLayout(viewModel = viewModel)
        }
    }
}

@Composable
fun WelcomeLandscapeLayout(
    viewModel: HomepageViewModel
) {
    val username=viewModel.username
    val balance = viewModel.balance.collectAsState().value
    val transactions= viewModel.transactions.collectAsState().value
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.4f)
        ) {
            TopLabel(username = username, 25    )
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

                    PieChartBalance(viewModel, 60, 6)

                    BalanceText(balance = viewModel.roundDouble(balance).toString(), 13)
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
        ) {
            Spacer(modifier=Modifier.height(10.dp))
            Text(
                text = "Transactions: ",
                fontSize = 25.sp,
                fontFamily = custom,
                color = Color.White
            )
            Transactions(transactions,viewModel)
        }
    }
}

@Composable
fun WelcomePortraitLayout(
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
        TopLabel(username = username, 50)
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
                PieChartBalance(viewModel, 175, 12)

                BalanceText(balance = viewModel.roundDouble(balance).toString(), 30)
            }
        }
        Spacer(modifier=Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.transactions),
            fontSize = 25.sp,
            fontFamily = custom,
            color = Color.White
        )
        Transactions(transactions,viewModel)
    }
}


@Composable
fun TopLabel(
    username: String,
    size: Int
) {
    Text(
        text = stringResource(R.string.welcome_back),
        fontSize = size.sp,
        fontFamily = custom,
        color = Color.White,
    )
    Text(
        text = username,
        fontSize = size.sp,
        fontFamily = custom,
        color = Color.White,
    )
}

@Composable
fun BalanceText(
    balance : String,
    size: Int
) {
    Row {
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.current_balance),
                fontSize = size.sp,
                fontFamily = custom,
                color = Color.White,
            )

            Card(modifier= Modifier
                .fillMaxWidth()
                .padding(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary)){
                Text(text= "$balance $",
                    fontSize = size.sp,
                    fontFamily = custom,
                    color=Color(0xFF65C466))
            }

        }
    }
}

@Composable
fun Transactions(
    transactions: List<Purchasing>,
    viewModel: HomepageViewModel
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(5.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(
            items = transactions
        ) { purchasing ->
            SingleTransaction(purchasing = purchasing, viewModel)
        }
    }


}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SingleTransaction(purchasing: Purchasing,
                      viewModel: HomepageViewModel) {
    val color = if (purchasing.isNegative) {
        Color(0xFFEB4E3D)
    } else {
        Color(0xFF65C466)
    }
    val negative = purchasing.isNegative
    val quantity = if (negative) {
        "-" + viewModel.roundDouble(purchasing.quantity)
    } else {
        "+" + viewModel.roundDouble(purchasing.quantity)
    }

    val imageUrlState = produceState<String?>(initialValue = null, purchasing.coinName) {
        value = viewModel.getCoinImage(purchasing.coinName)
    }

    val imagePainter = imageUrlState.value?.let { rememberAsyncImagePainter(it) }


    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ){
        Row(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter =  imagePainter ?: painterResource(id = R.drawable.placeholderimage),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )

            Text(text = purchasing.coinName,
                fontSize=20.sp,
                fontFamily = custom,
                color=color,
                modifier =Modifier.padding(10.dp)
            )
            Text(text = quantity,
                fontSize=20.sp,
                fontFamily = custom,
                color=color,
                modifier =Modifier.padding(10.dp)
            )

            purchasing.transactionDate?.let {
                Text(text = it,
                    fontSize=20.sp,
                    fontFamily = custom,
                    color=Color.White,
                    modifier =Modifier.padding(10.dp)
                )
            }
        }

    }


    /*



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

     */
}