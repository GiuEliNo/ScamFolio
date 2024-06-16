package com.dosti.scamfolio.ui.chart

import android.app.slice.Slice
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.dosti.scamfolio.R
import com.dosti.scamfolio.api.model.CoinBalance
import com.dosti.scamfolio.api.model.Wallet
import com.dosti.scamfolio.viewModel.HomepageViewModel
import kotlin.random.Random


@Composable
fun PieChartBalance(viewModel: HomepageViewModel, size: Int, textSize: Int){

        val wallet= viewModel.myWallet.collectAsState().value

        val slicesPieChart: MutableList<PieChartData.Slice> = mutableListOf()
        var counter=0
        if (wallet.isNotEmpty()) {
            for (wal in wallet) {
                if (counter % 4 == 0) {
                    val slice = PieChartData.Slice(
                        wal.coinName,
                        wal.balance.toFloat(),
                        Color(0xCA6D068D)
                    )
                    slicesPieChart.add(slice)
                    counter++
                } else if (counter % 4 == 1) {
                    val slice = PieChartData.Slice(
                        wal.coinName,
                        wal.balance.toFloat(),
                        Color(0xFF1A297E)
                    )
                    slicesPieChart.add(slice)
                    counter++

                } else if (counter % 4 == 2) {
                    val slice = PieChartData.Slice(
                        wal.coinName,
                        wal.balance.toFloat(),
                        Color(0xE90D470F)
                    )
                    slicesPieChart.add(slice)
                    counter++

                } else {
                    val slice = PieChartData.Slice(
                        wal.coinName,
                        wal.balance.toFloat(),
                        Color(0xCA6D1636)
                    )
                    slicesPieChart.add(slice)
                    counter++

                }
            }
        }
    else{
            val slice = PieChartData.Slice(
                stringResource(R.string.empty),
                0.0f,
                Color(0xFF1A297E))
            slicesPieChart.add(slice)
    }
    val pieChartData= PieChartData(slicesPieChart, plotType = PlotType.Pie)
    val pieChartConfig= PieChartConfig(
        backgroundColor = Color.Black,
        isAnimationEnable = true,
        showSliceLabels = true,
        animationDuration = 1500,
        isEllipsizeEnabled = true,
        isClickOnSliceEnabled = false,
        sliceLabelTextSize = textSize.sp
    )


    PieChart(modifier = Modifier
        .width(size.dp)
        .height(size.dp),
        pieChartData,
        pieChartConfig)
}