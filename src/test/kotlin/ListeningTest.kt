import com.example.MainPage
import com.example.WebDriverFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.WebDriver
import kotlin.test.assertTrue

class ListeningTest {

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

    @ParameterizedTest(name = "song starts and end playing in {0}")
    @MethodSource("browsers")
    fun testSongStartsPlaying(browser: WebDriverFactory.Browser) {
        driver = WebDriverFactory.create(browser)

        val mainPage = MainPage(driver).open()

        mainPage.waitUntilSongStopped()
        assertTrue(mainPage.isSongStopped())

        mainPage.pressPlay()
        mainPage.waitUntilSongPlaying()
        assertTrue(mainPage.isSongPlaying())

        mainPage.pressPlay()
        mainPage.waitUntilSongStopped()
        assertTrue(mainPage.isSongStopped())
    }

}
