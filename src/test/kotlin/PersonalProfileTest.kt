import com.example.LoginPage
import com.example.PersonalProfileData
import com.example.PersonalProfilePage
import com.example.WebDriverFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.WebDriver
import kotlin.test.assertTrue

class PersonalProfileTest {

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
        //driver.quit()
    }

    @ParameterizedTest(name = "public personal data is readable in {0}")
    @MethodSource("browsers")
    fun testPublicPersonalDataIsReadable(browser: WebDriverFactory.Browser) {
        driver = WebDriverFactory.create(browser)

        val profilePage = PersonalProfilePage(driver).openPublic()

        assertTrue(profilePage.getDisplayedGender().isNotBlank())
        assertTrue(profilePage.getDisplayedLocation().isNotBlank())
        assertTrue(profilePage.getDisplayedBirthday().isNotBlank())
        assertTrue(profilePage.getDisplayedAbout().isNotBlank())
    }

    @ParameterizedTest(name = "personal data changes are displayed in {0}")
    @MethodSource("browsers")
    fun testPersonalDataChangesAreDisplayed(browser: WebDriverFactory.Browser) {
        driver = WebDriverFactory.create(browser)

        LoginPage(driver)
            .open()
            .safeLogin("svitoi_tapok1", "000Tt111")

        val profilePage = PersonalProfilePage(driver)

        val timestamp = System.currentTimeMillis()
        val personalData = PersonalProfileData(
            gender = "Кун",
            location = "Autotest City",
            birthday = "01-01-2001",
            about = "Autotest profile about $timestamp"
        )

        profilePage
            .openEdit()
            .updatePersonalData(personalData)
            .openPublic()

        println(personalData)
        assertTrue(profilePage.isPersonalDataDisplayed(personalData))
    }
}
