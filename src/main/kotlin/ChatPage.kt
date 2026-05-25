package com.example

import org.openqa.selenium.By
import org.openqa.selenium.ElementClickInterceptedException
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class ChatPage(
    private val webDriver: WebDriver,
) : Page(webDriver) {

    private val wait = WebDriverWait(webDriver, Duration.ofSeconds(10))

    private val chatContainer = By.xpath("//div[@id='chat-embed-container']")
    private val chatList = By.xpath("//div[@id='chatList']")
    private val messageInput = By.xpath("//textarea[@id='inputField']")
    private val submitButton = By.xpath("//button[@id='submitButton']")
    private val messageLengthCounter = By.xpath("//span[@id='messageLengthCounter']")

    fun open(): ChatPage {
        webDriver.get("https://anison.fm/chat/")
        wait.until(ExpectedConditions.visibilityOfElementLocated(chatContainer))
        wait.until(ExpectedConditions.visibilityOfElementLocated(chatList))
        wait.until(ExpectedConditions.elementToBeClickable(messageInput))
        return this
    }

    fun typeMessage(message: String): ChatPage {
        val input = wait.until(ExpectedConditions.elementToBeClickable(messageInput))
        input.clear()
        input.sendKeys(message)
        return this
    }

    fun sendMessage(message: String): ChatPage {
        typeMessage(message)
        clickSend()
        return this
    }

    fun clickSend(): ChatPage {
        wait
            .ignoring(StaleElementReferenceException::class.java)
            .ignoring(ElementClickInterceptedException::class.java)
            .until {
                webDriver.findElement(submitButton).click()
                true
            }
        return this
    }


    fun getMessageLengthCounter(): String {

        wait.until {
            webDriver.findElement(messageLengthCounter).text.trim() != "0/1040"
        }
        return webDriver.findElement(messageLengthCounter).text.trim()
    }


    fun getChatMessages(): List<String> {
        return webDriver.findElements(By.xpath("//div[contains(@class, 'chat__text')]")).map { x -> x.text.trim() }
    }

    fun isMessageInChat(message: String): Boolean {
        return getChatMessages().contains(message)
    }

    fun waitUntilMessageAppears(message: String): ChatPage {
        wait
            .ignoring(StaleElementReferenceException::class.java)
            .until {
                isMessageInChat(message)
            }
        return this
    }
}
