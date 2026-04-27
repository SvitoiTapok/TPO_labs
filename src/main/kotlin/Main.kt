package com.example

import com.example.func.*
import com.example.util.CSVGenerator
import com.example.util.Drawer
import kotlin.math.E

fun main() {
    val steps = 100000
    CSVGenerator(Ln(), -10.0, 10.0, steps, 1e-6).csv()
    CSVGenerator(Cos(Sin()), -10.0, 10.0, steps, 1e-6).csv()
    CSVGenerator(Csc(Sin()), -10.0, 10.0, steps, 1e-6).csv()
    CSVGenerator(Sin(), -10.0, 10.0, steps, 1e-6).csv()
    CSVGenerator(Ln(), -10.0, 10.0, steps, 1e-6).csv()
    CSVGenerator(Log3(Ln()), -10.0, 10.0, steps, 1e-6).csv()
    CSVGenerator(Log5(Ln()), -10.0, 10.0, steps, 1e-6).csv()
    CSVGenerator(Log10(Ln()), -10.0, 10.0, steps, 1e-6).csv()
    CSVGenerator(Tan(Sin(), Cos(Sin())), -10.0, 10.0, steps, 1e-6).csv()
    CSVGenerator(TrigonometricSystemFunction(Sin(), Csc(Sin()), Tan(Sin(), Cos(Sin()))), -10.0, 10.0, 1000, 1e-6).csv()
    CSVGenerator(LogarithmicSystemFunction(Ln(), Log3(Ln()), Log5(Ln()), Log10(Ln())), -10.0, 10.0, 1000, 1e-6).csv()
    CSVGenerator(SystemFunction(TrigonometricSystemFunction(Sin(), Csc(Sin()), Tan(Sin(), Cos(Sin()))), LogarithmicSystemFunction(Ln(), Log3(Ln()), Log5(Ln()), Log10(Ln()))), -10.0, 10.0, 1000, 1e-6).csv()
    Drawer("Cos").plot()
    Drawer("Csc").plot()
    Drawer("Ln").plot()
    Drawer("Log3").plot()
    Drawer("Log5").plot()
    Drawer("Log10").plot()
    Drawer("Tan").plot()
    Drawer("LogarithmicSystemFunction").plot()
    Drawer("TrigonometricSystemFunction").plot()
    Drawer("SystemFunction").plot()
    //println(Ln().invoke(E, 1e-6))
}