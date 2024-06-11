package com.dosti.scamfolio.ui.chart

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


@Composable
fun Chart(listPrice: List<Double>){
    val pointsData: MutableList<Point> = mutableListOf()

    var count = 24
    var days = 0
    for(price in listPrice){
        if(count == 24) {
            pointsData.add(Point(days.toFloat(), price.toFloat()))
            count = 0
            days++
        }
        count += 1
    }
    val steps = 5

    val date = LocalDate.now().minusWeeks(1).plusDays(1)

    val xAxisData = AxisData.Builder()
        .axisStepSize(50.dp)
        .backgroundColor(Color.Transparent)
        .steps(pointsData.size - 1)
        .labelData { i -> date.plusDays(i.toLong()).toString().substring(5) }
        .labelAndAxisLinePadding(15.dp)
        .axisLabelColor(Color.White)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(20.dp)
        .axisLabelColor(Color.White)
        .labelData {  i ->
            val yMin = pointsData.minOf { it.y }
            val yMax = pointsData.maxOf { it.y }
            val yScale = (yMax - yMin) / steps
            ((i * yScale) + yMin).toString()
        }
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = MaterialTheme.colorScheme.tertiary,
                        lineType = LineType.Straight(isDotted = false)

                    ),
                    IntersectionPoint(
                        color = MaterialTheme.colorScheme.tertiary
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
                        val yLabel = String.format("%.2f", y)
                        yLabel
                    })
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        //gridLines = GridLines(color = MaterialTheme.colorScheme.outlineVariant),
        backgroundColor = MaterialTheme.colorScheme.surface
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        lineChartData = lineChartData
    )


}