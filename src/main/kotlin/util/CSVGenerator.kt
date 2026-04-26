package com.example.util

import com.example.func.MatFunction
import org.knowm.xchart.BitmapEncoder
import org.knowm.xchart.XYChartBuilder
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime

class CSVGenerator(
    private val function: MatFunction,
    private val x1: Double,
    private val x2: Double,
    private val steps: Int,
    private val accuracy: Double
) {
    private val outputDir = Paths.get("src/test/resources").apply { Files.createDirectories(this) }

    fun csv(): File {
        val fileName = "${function.functionName}_data.csv"
        val file = outputDir.resolve(fileName).toFile()
        file.bufferedWriter().use { writer ->
            writer.appendLine("x,res_${function.functionName}")
            if (steps < 2) throw IllegalArgumentException("steps must be at least 2")
            val dx = (x2 - x1) / (steps - 1)
            var x = x1
            for (i in 0 until steps) {
                val y = function.invoke(x, accuracy)
                writer.appendLine("${x},${y}")
                x += dx
            }
        }
        return file
    }
 }