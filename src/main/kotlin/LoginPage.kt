package com.example

import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class LoginPage(
    private val webDriver: WebDriver,
) : Page(webDriver) {
    private val wait = WebDriverWait(webDriver, Duration.ofSeconds(3))
    fun open() {
        webDriver.get("https://anison.fm/")
        webDriver.findElement(By.xpath("//button[@class='header-login__btn']")).click()
    }

    fun login(login: String, password: String) {
        val form = webDriver.findElement(By.xpath("//form[@class='login-form']"))
        val loginField = form.findElement(By.xpath(".//input[@class='login-nick__input form-control']"))
        val passwordField = form.findElement(By.xpath(".//input[@class='form-control form-password-input']"))
        val sendButton = form.findElement(By.xpath(".//button[@class='modal-btn']"))

        loginField.sendKeys(login)
        passwordField.sendKeys(password)
        sendButton.click()
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