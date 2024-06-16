package com.dosti.scamfolio.ui.chart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import java.time.LocalDate
import java.time.LocalDateTime


@Composable
fun Chart(listPrice: List<Double>){
    val pointsData: MutableList<Point> = mutableListOf()

    var count = 0
    var hours = 0
    for(price in listPrice){

        pointsData.add(Point(hours.toFloat(), price.toFloat()))
        hours++

        count += 1
    }
    val steps = 2

    val date = LocalDateTime.now().minusWeeks(1)

    val xAxisData = AxisData.Builder()
        .axisStepSize(3.dp)
        .backgroundColor(Color.Transparent)
        .axisLabelColor(Color.LightGray)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(20.dp)
        .axisLabelColor(Color.LightGray)
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        lineType = LineType.Straight(isDotted = false)

                    ),
                    IntersectionPoint(
                        radius = 0.dp,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    SelectionHighlightPoint(color = MaterialTheme.colorScheme.primary),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.inversePrimary,
                                Color.Transparent
                            )
                        )
                    ),
                    selectionHighlightPopUp = SelectionHighlightPopUp(popUpLabel = { x, y ->
                        val xLabel = "${(date.plusHours(x.toLong())).toString().substring(5, 13).replace("T", " ").replace("-", "/").plus("h")} "
                        val yLabel = "${String.format("%.2f", y)}"
                        "$xLabel \n $yLabel"
                    })
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = MaterialTheme.colorScheme.primary
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        lineChartData = lineChartData
    )


}