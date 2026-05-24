import com.example.LoginPage
import com.example.WebDriverFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.WebDriver
import kotlin.test.assertTrue

class FavoriteTest {

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

    @ParameterizedTest(name = "song is added to favorites in {0}")
    @MethodSource("browsers")
    fun testSongAddedToFavorites(browser: WebDriverFactory.Browser) {
        driver = WebDriverFactory.create(browser)

        val mainPage = LoginPage(driver)
            .open()
            .login("svitoi_tapok1", "000Tt111")

        val songTitle = mainPage.getCurrentSongTitle()

        mainPage.addCurrentSongToFavorites()

        assertTrue(mainPage.isFavoriteHighlighted())

        mainPage.openFavorites()

        assertTrue(mainPage.isSongInFavorites(songTitle))
    }
}
