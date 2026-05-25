import com.example.MainPage
import com.example.SearchType
import com.example.WebDriverFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.WebDriver
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SearchTest {

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

    @ParameterizedTest(name = "anime search in {0}")
    @MethodSource("browsers")
    fun testAnimeSearch(browser: WebDriverFactory.Browser) {
        driver = WebDriverFactory.create(browser)

        val mainPage = MainPage(driver)
            .open()
            .search("Chaos Head", SearchType.ANIME)

        assertEquals(SearchType.ANIME.title, mainPage.getSelectedSearchType())
        assertTrue(mainPage.isSearchResultFound("Chaos Head"))
    }

    @ParameterizedTest(name = "track search in {0}")
    @MethodSource("browsers")
    fun testTrackSearch(browser: WebDriverFactory.Browser) {
        driver = WebDriverFactory.create(browser)

        val mainPage = MainPage(driver)
            .open()
            .search("Find the Blue", SearchType.TRACK)

        assertEquals(SearchType.TRACK.title, mainPage.getSelectedSearchType())
        assertTrue(mainPage.isSearchResultFound("Find the Blue"))

    }

    @ParameterizedTest(name = "artist search in {0}")
    @MethodSource("browsers")
    fun testArtistSearch(browser: WebDriverFactory.Browser) {
        driver = WebDriverFactory.create(browser)

        val mainPage = MainPage(driver)
            .open()
            .search("Itou Kanako", SearchType.ARTIST)

        assertEquals(SearchType.ARTIST.title, mainPage.getSelectedSearchType())
        assertTrue(mainPage.isSearchResultFound("Itou Kanako"))
    }
    @ParameterizedTest(name = "artist search in {0}")
    @MethodSource("browsers")
    fun testWrongInput(browser: WebDriverFactory.Browser) {
        driver = WebDriverFactory.create(browser)

        val mainPage = MainPage(driver)
            .open()
            .search("asdfaefe", SearchType.ANIME)

        assertEquals(SearchType.ANIME.title, mainPage.getSelectedSearchType())
        assertTrue(mainPage.isSearchResultFound("Поиск не дал результатов"))
    }
}
