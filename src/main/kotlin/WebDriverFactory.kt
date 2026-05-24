package com.example

import org.openqa.selenium.PageLoadStrategy
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import java.time.Duration

object WebDriverFactory {

    enum class Browser {
        CHROME,
        FIREFOX;
    }

    fun create(browser: Browser): WebDriver {
        return when (browser) {
            Browser.CHROME -> createChromeDriver()
            Browser.FIREFOX -> createFirefoxDriver()
        }
    }

    private fun createChromeDriver(): WebDriver {
        val options = ChromeOptions().apply {
            setPageLoadStrategy(PageLoadStrategy.EAGER)
            addArguments("--window-size=1920,1080")
        }

        return setupTimeouts(ChromeDriver(options))
    }

    private fun createFirefoxDriver(): WebDriver {
        val options = FirefoxOptions().apply {
            setPageLoadStrategy(PageLoadStrategy.EAGER)
            addArguments("--width=1920")
            addArguments("--height=1080")
            addPreference("network.protocol-handler.external.tg", false)
            addPreference("network.protocol-handler.warn-external.tg", false)
            addPreference("network.protocol-handler.expose.tg", false)
            addPreference("dom.webnotifications.enabled", false)
            addPreference("permissions.default.desktop-notification", 2)
        }

        return setupTimeouts(FirefoxDriver(options))
    }

    private fun setupTimeouts(driver: WebDriver): WebDriver {
        return driver.also {
            driver.manage().timeouts().apply {
                pageLoadTimeout(Duration.ofSeconds(20))
                scriptTimeout(Duration.ofSeconds(20))
                implicitlyWait(Duration.ofSeconds(20))
            }
        }
    }
}