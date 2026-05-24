package com.example

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class MainPage(
    private val webDriver: WebDriver,
) : Page(webDriver) {
    fun open() {
        webDriver.get("https://anison.fm/")
    }
    fun pressPlay() {
        val element = webDriver.findElement(By.xpath("//button[@class='song-play']"))
        element.click()
    }


}