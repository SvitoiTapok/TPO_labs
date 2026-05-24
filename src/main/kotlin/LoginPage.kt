package com.example

import org.openqa.selenium.By
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
    private val loginError = By.xpath(".//[contains(@id,'login-error') or contains(@class,'login-error')]")

    fun open():LoginPage {
        webDriver.get("https://anison.fm/")
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click()
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginForm))
        return this
    }

    fun login(login: String, password: String): MainPage {
        val form = webDriver.findElement(By.xpath("//form[@class='login-form']"))
        val loginField = form.findElement(By.xpath(".//input[@class='login-nick__input form-control']"))
        val passwordField = form.findElement(By.xpath(".//input[@class='form-control form-password-input']"))
        val sendButton = form.findElement(By.xpath(".//button[@class='modal-btn']"))

        loginField.sendKeys(login)
        passwordField.sendKeys(password)
        sendButton.click()
        return MainPage(webDriver)
    }
    fun getError(): String {
        val form = webDriver.findElement(By.xpath("//form[@class='login-form']"))
        val errorDiv = form.findElement(By.xpath(".//div[@id='login-error']"))
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