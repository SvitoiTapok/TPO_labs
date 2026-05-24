package com.example

import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class RegistrationPage(
    private val webDriver: WebDriver,
) : Page(webDriver) {
    private val wait = WebDriverWait(webDriver, Duration.ofSeconds(3))
    fun open() {
        webDriver.get("https://anison.fm/")
        webDriver.findElement(By.xpath("//button[@class='header-login__btn']")).click()
    }

    fun register(login: String, password: String, email: String) {
        val regButton = webDriver.findElement(By.xpath("//a[@class='modal-link local_link']"))
        val form = webDriver.findElement(By.xpath("//form[@class='login-form']"))
        val loginField = form.findElement(By.xpath("//input[@id='login']"))
        val passwordField = form.findElement(By.xpath("//input[@id='password']"))
        val emailField = form.findElement(By.xpath("//input[@id='email']"))
//        val sendButton = form.findElement(By.xpath("//button[@type='submit']"))

        regButton.click()
        loginField.sendKeys(login)
        passwordField.sendKeys(password)
        emailField.sendKeys(email)
//        sendButton.click()
    }

    fun putRegistrationLogin(login: String) {
        val form = webDriver.findElement(By.xpath("//form[@class='login-form']"))
        val loginField = form.findElement(By.xpath("//input[@id='login']"))
        loginField.clear()
        loginField.sendKeys(login)
    }

    fun isNicknameAvailable(): Boolean {

        val form = webDriver.findElement(By.xpath("//form[@class='login-form']"))
        val valid = form.findElement(By.xpath("//p[@id='suggest_valid']"))
//        val invalid = form.findElement(By.xpath("//p[@id='suggest_invalid']"))
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