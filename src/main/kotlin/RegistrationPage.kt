package com.example

import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class RegistrationPage(
    private val webDriver: WebDriver,
) : Page(webDriver) {
    private val wait = WebDriverWait(webDriver, Duration.ofSeconds(5))

    private val loginButton = By.xpath("//button[contains(@class,'header-login__btn')]")
    private val registrationLink = By.xpath("//a[contains(@class,'modal-link') and contains(@class,'local_link')]")
    private val registrationForm = By.xpath("//form[contains(@class,'login-form')]")
    private val registrationLoginInput = By.xpath("//input[@id='login']")
    private val registrationPasswordInput = By.xpath("//input[@id='password']")
    private val registrationEmailInput = By.xpath("//input[@id='email']")
    private val availableNicknameMessage = By.xpath("//p[@id='suggest_valid']")
    private val unavailableNicknameMessage = By.xpath("//p[@id='suggest_invalid']")

    fun open(): RegistrationPage {
        webDriver.get("https://anison.fm/")
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click()
        wait.until(ExpectedConditions.elementToBeClickable(registrationLink)).click()
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationLoginInput))
        return this
    }

    fun register(login: String, password: String, email: String) {
        val form = webDriver.findElement(registrationForm)
        val loginField = form.findElement(registrationLoginInput)
        val passwordField = form.findElement(registrationPasswordInput)
        val emailField = form.findElement(registrationEmailInput)

        loginField.sendKeys(login)
        passwordField.sendKeys(password)
        emailField.sendKeys(email)
    }

    fun putRegistrationLogin(login: String) {
        val form = webDriver.findElement(registrationForm)
        val loginField = form.findElement(registrationLoginInput)
        loginField.clear()
        loginField.sendKeys(login)
    }

    fun isNicknameAvailable(): Boolean {
        val form = webDriver.findElement(registrationForm)
        val valid = form.findElement(availableNicknameMessage)
        try {
            wait.until {
                !valid.getAttribute("class").contains("d-none")
            }
            return true
        } catch (e: TimeoutException) {
            return false
        }
    }
}
