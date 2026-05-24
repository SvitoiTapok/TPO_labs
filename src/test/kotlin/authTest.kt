import com.example.LoginPage
import com.example.MainPage
import com.example.WebDriverFactory
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class authTest {
    val driver = WebDriverFactory.create(WebDriverFactory.Browser.CHROME)
    @Test
    fun testRightAuth() {
        val loginPage = LoginPage(driver)

        loginPage.open()
        loginPage.login("svitoi_tapok1", "000Tt111")
        val mainPage = MainPage(driver)
        assertEquals("svitoi_tapok1", mainPage.getNickname())
    }
    @Test
    fun testErrorAuth() {
        val loginPage = LoginPage(driver)
        loginPage.open()
        loginPage.login("dsafefefe", "000Tt111")
        val error = loginPage.getError()
        assertEquals("Неверный логин или пароль", error)

    }
}