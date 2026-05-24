package com.example

import org.openqa.selenium.By
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class MainPage(
    private val webDriver: WebDriver,
) : Page(webDriver) {
    val wait = WebDriverWait(webDriver, Duration.ofSeconds(3))
    fun open() {
        webDriver.get("https://anison.fm/")
    }
    fun pressPlay() {
        val element = webDriver.findElement(By.xpath("//button[@class='song-play']"))
        element.click()
    }
    fun getNickname(): String? {
        return wait
            .ignoring(StaleElementReferenceException::class.java)
            .until {
                val header = webDriver.findElement(By.xpath("//div[@class='header-login']"))

                val nickname = header

                    .findElements(By.xpath(".//p[@class='profile-block__title']"))
                    .firstOrNull()
                    ?.text
                    ?.trim()
                nickname?.takeIf { it.isNotBlank() }
            }
    }
}