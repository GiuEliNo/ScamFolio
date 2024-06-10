package com.dosti.scamfolio.ui.chart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.compose.component.marker.markerComponent
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.DefaultAlpha
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShader
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry


@Composable
fun Chart() {
    val refreshDataset = remember { mutableIntStateOf(0) }
    val modelProducer = remember { ChartEntryModelProducer() }
    val dataSetForModel = remember { mutableStateListOf(listOf<FloatEntry>()) }
    val datasetLineSpec = remember { arrayListOf<LineChart.LineSpec>() }

    val scrollState = rememberChartScrollState()

    LaunchedEffect(key1 = refreshDataset.intValue) {
        dataSetForModel.clear()
        datasetLineSpec.clear()
        var xPos = 0f
        val dataPoints = arrayListOf<FloatEntry>()
        datasetLineSpec.add(
            LineChart.LineSpec(
                lineColor = Green.toArgb(),
                /*  lineBackgroundShader = DyanamicShaders.fromBrush(
                    brush = Brush.verticalGradient(
                            listOf(
                                Green.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                                Green.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_END)
                            )
                    )
                )*/
            )
        )

        for (i in 1..100) {
            val randomYFloat = (1..1000).random().toFloat()
            dataPoints.add(FloatEntry(x = xPos, y = randomYFloat))
            xPos += 1f
        }

        dataSetForModel.add(dataPoints)

        modelProducer.setEntries(dataSetForModel)
    }


        Column(modifier = Modifier.fillMaxSize()) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {

                if(dataSetForModel.isNotEmpty()){
                    ProvideChartStyle{
                        val marker = rememberMarker()
                        Chart(
                            chart = lineChart(
                                lines = datasetLineSpec
                            ),
                            chartModelProducer = modelProducer,

                            startAxis = rememberStartAxis(
                                title = "Top Values",
                                tickLength = 0.dp,
                                valueFormatter = { value, _ ->
                                    (value.toInt()).toString()
                                },
                                itemPlacer = AxisItemPlacer.Vertical.default(
                                    maxItemCount = 6
                                )
                            ),

                            bottomAxis = rememberBottomAxis(
                                title = "Title",
                                tickLength = 0.dp,
                                valueFormatter = { value, _ ->
                                    ((value.toInt()) + 1).toString()
                                },
                                guideline = null
                            ),
                            marker = marker,

                            chartScrollState = scrollState,
                            isZoomEnabled = true
                        )

                    }
                }
            }
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { refreshDataset.intValue++ }
            )
            {
                Text(text = "Refresh")
            }
        }
    }


