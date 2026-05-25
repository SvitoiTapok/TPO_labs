import com.example.ChatPage
import com.example.LoginPage
import com.example.WebDriverFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.WebDriver
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ChatTest {

    private lateinit var driver: WebDriver

    companion object {
        @JvmStatic
        fun browsers(): List<WebDriverFactory.Browser> {
            return when (System.getProperty("browser")?.lowercase()) {
                "chrome" -> listOf(WebDriverFactory.Browser.CHROME)
                "firefox" -> listOf(WebDriverFactory.Browser.FIREFOX)
                "all", null -> listOf(
                    WebDriverFactory.Browser.CHROME,
                    WebDriverFactory.Browser.FIREFOX
                )
                else -> error("Use -Dbrowser=chrome, -Dbrowser=firefox or -Dbrowser=all")
            }
        }
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }

    @ParameterizedTest(name = "chat message is typed in {0}")
    @MethodSource("browsers")
    fun testMessageTyping(browser: WebDriverFactory.Browser) {
        driver = WebDriverFactory.create(browser)
        LoginPage(driver)
            .open()
            .safeLogin("svitoi_tapok1", "000Tt111")
        val message = "chat typing test"
        val chatPage = ChatPage(driver)
            .open()
            .typeMessage(message)
        assertEquals("${message.length}/1040", chatPage.getMessageLengthCounter())
    }

//    @ParameterizedTest(name = "chat message is sent and visible in {0}")
//    @MethodSource("browsers")
//    fun testMessageSendingAndReading(browser: WebDriverFactory.Browser) {
//        driver = WebDriverFactory.create(browser)
//
//        LoginPage(driver)
//            .open()
//            .login("svitoi_tapok1", "000Tt111")
//
//        val message = "Всем привет!"
//        val chatPage = ChatPage(driver).open()
//
//        chatPage
//            .sendMessage(message)
//            .waitUntilMessageAppears(message)
//
//        assertTrue(chatPage.isMessageInChat(message))
//    }
    @ParameterizedTest(name = "chat message searching in {0}")
    @MethodSource("browsers")
    fun searchChat(browser: WebDriverFactory.Browser) {
        driver = WebDriverFactory.create(browser)

        LoginPage(driver)
            .open()
            .safeLogin("svitoi_tapok1", "000Tt111")

        val message = "от Наруто*"
        val chatPage = ChatPage(driver).open()

        assertTrue(chatPage.isMessageInChat(message))
    }
}
