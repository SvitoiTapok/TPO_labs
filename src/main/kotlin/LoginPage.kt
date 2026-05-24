package com.example

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class LoginPage(
    private val webDriver: WebDriver,
) : Page(webDriver) {
    fun open() {
        webDriver.get("https://anison.fm/")
        webDriver.findElement(By.xpath("//button[@class='header-login__btn']")).click()
    }

    fun login(login: String, password: String) {
        val form = webDriver.findElement(By.xpath("//form[@class='login-form']"))
        val loginField = form.findElement(By.xpath("//input[@class='login-nick__input form-control']"))
        val passwordField = form.findElement(By.xpath("//input[@class='form-control form-password-input']"))
        val sendButton = form.findElement(By.xpath("//button[@class='modal-btn']"))

        loginField.sendKeys(login)
        passwordField.sendKeys(password)
        sendButton.click()
    }
}