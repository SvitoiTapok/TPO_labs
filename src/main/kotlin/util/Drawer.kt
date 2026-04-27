package com.example.util

import com.example.func.MatFunction
import org.knowm.xchart.BitmapEncoder
import org.knowm.xchart.XYChartBuilder
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Drawer(
    private val name: String,
    private val csvDir: String = "src/test/resources"
) {
    private val outputDir = Paths.get("plot_output").apply { Files.createDirectories(this) }
    private val timestampFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")

    fun plot(): String {
        val (xData, yData) = readDataFromCsv()
        val chart = XYChartBuilder()
            .width(800)
            .height(600)
            .title(name)
            .xAxisTitle("x")
            .yAxisTitle("y")
            .build()
        chart.addSeries(name, xData, yData)
        chart.styler.xAxisMin = -10.0
        chart.styler.xAxisMax = 10.0
        chart.styler.yAxisMin = -10.0
        chart.styler.yAxisMax = 10.0
        val fileName = "${name}.png"
        val filePath = outputDir.resolve(fileName).toString()
        BitmapEncoder.saveBitmap(chart, filePath, BitmapEncoder.BitmapFormat.PNG)
        return filePath
    }

    private fun readDataFromCsv(): Pair<List<Double>, List<Double>> {
        val csvFile = Paths.get(csvDir, "${name}_data.csv").toFile()
        if (!csvFile.exists()) {
            throw IllegalStateException(
                "CSV file not found: ${csvFile.path}. Generate it with CSVGenerator before plotting."
            )
        }

        val xData = mutableListOf<Double>()
        val yData = mutableListOf<Double>()

        csvFile.bufferedReader().useLines { lines ->
            lines.drop(1).forEach {line ->
                val values = line.split(',')
                xData.add(values[0].toDouble())
                yData.add(values[1].toDouble())
            }
        }

        return xData to yData
    }
}
