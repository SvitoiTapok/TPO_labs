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

    private val wait = WebDriverWait(webDriver, Duration.ofSeconds(10))

    private val playButton = By.xpath("(//button[contains(@class, 'song-play')])[1]")

    private val startIcon = By.xpath(
        ".//*[local-name()='svg' and contains(@class, 'song-play__start')]"
    )

    private val stopIcon = By.xpath(
        ".//*[local-name()='svg' and contains(@class, 'song-play__stop')]"
    )

    fun open(): MainPage {
        webDriver.get("https://anison.fm/")
        wait.until(ExpectedConditions.elementToBeClickable(playButton))
        waitUntilSongStopped()
        return this
    }

    fun pressPlay(): MainPage {
        wait.until(ExpectedConditions.elementToBeClickable(playButton)).click()
        return this
    }

    fun isSongPlaying(): Boolean {
        val button = webDriver.findElement(playButton)

        val startClass = button.findElement(startIcon)
            .getDomAttribute("class")
            .orEmpty()

        val stopClass = button.findElement(stopIcon)
            .getDomAttribute("class")
            .orEmpty()

        return startClass.contains("hide") && !stopClass.contains("hide")
    }

    fun isSongStopped(): Boolean {
        val button = webDriver.findElement(playButton)

        val startClass = button.findElement(startIcon)
            .getDomAttribute("class")
            .orEmpty()

        val stopClass = button.findElement(stopIcon)
            .getDomAttribute("class")
            .orEmpty()

        return !startClass.contains("hide") && stopClass.contains("hide")
    }

    fun waitUntilSongPlaying(): MainPage {
        wait
            .ignoring(StaleElementReferenceException::class.java)
            .until {
                isSongPlaying()
            }

        return this
    }

    fun waitUntilSongStopped(): MainPage {
        wait
            .ignoring(StaleElementReferenceException::class.java)
            .until {
                isSongStopped()
            }

        return this
    }

    fun getNickname(): String? {
        return wait
            .ignoring(StaleElementReferenceException::class.java)
            .until {
                val header = webDriver.findElement(
                    By.xpath("//div[contains(@class, 'header-login')]")
                )

                header
                    .findElements(By.xpath(".//p[contains(@class, 'profile-block__title')]"))
                    .firstOrNull()
                    ?.text
                    ?.trim()
                    ?.takeIf { it.isNotBlank() }
            }
    }
}