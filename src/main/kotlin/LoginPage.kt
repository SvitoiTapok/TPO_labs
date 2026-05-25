package com.example

import org.openqa.selenium.By
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class LoginPage(
    private val webDriver: WebDriver,
) : Page(webDriver) {
    private val wait = WebDriverWait(webDriver, Duration.ofSeconds(5))

    private val loginButton = By.xpath("//button[contains(@class,'header-login__btn')]")
    private val loginForm = By.xpath("//form[contains(@class,'login-form')]")
    private val loginInput = By.xpath(".//input[contains(@class,'login-nick__input')]")
    private val passwordInput = By.xpath(".//input[contains(@class,'form-password-input')]")
    private val submitButton = By.xpath(".//button[contains(@class,'modal-btn')]")
    private val loginError = By.xpath(".//div[@id='login-error']")

    fun open():LoginPage {
        webDriver.get("https://anison.fm/")
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click()
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginForm))
        return this
    }

    fun login(login: String, password: String): MainPage {
        val form = webDriver.findElement(loginForm)
        val loginField = form.findElement(loginInput)
        val passwordField = form.findElement(passwordInput)
        val sendButton = form.findElement(submitButton)

        loginField.sendKeys(login)
        passwordField.sendKeys(password)
        sendButton.click()

        return MainPage(webDriver)
    }
    fun safeLogin(login: String, password: String): MainPage {
        val form = webDriver.findElement(loginForm)
        val loginField = form.findElement(loginInput)
        val passwordField = form.findElement(passwordInput)
        val sendButton = form.findElement(submitButton)

        loginField.sendKeys(login)
        passwordField.sendKeys(password)
        sendButton.click()
        waitUntilLoggedIn(login)
        return MainPage(webDriver)
    }
    private fun waitUntilLoggedIn(username: String) {
        wait
            .ignoring(StaleElementReferenceException::class.java)
            .until {
                val nickText = webDriver.findElements(By.xpath("//div[contains(@class, 'header-login')]//p[contains(@class, 'profile-block__title')]"))
                    .firstOrNull { it.isDisplayed }
                    ?.text
                    ?.trim()

                nickText == username
            }
    }
    fun getError(): String {
        val form = webDriver.findElement(loginForm)
        val errorDiv = form.findElement(loginError)
        try {
            wait.until {
                !errorDiv.getAttribute("class").contains("d-none")
            }
            val errorField = form.findElement(By.xpath("//p[@class='anime-info__title']"))
            return errorField.text
        } catch (e: TimeoutException) {
            return ""
        }
    }
}