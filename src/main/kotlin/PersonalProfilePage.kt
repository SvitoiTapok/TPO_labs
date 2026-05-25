package com.example

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

data class PersonalProfileData(
    val gender: String,
    val location: String,
    val birthday: String,
    val about: String,
)

class PersonalProfilePage(
    private val webDriver: WebDriver,
) : Page(webDriver) {

    private val wait = WebDriverWait(webDriver, Duration.ofSeconds(10))

    private val editForm = By.xpath("//form[@id='user_profile_form']")
    private val locationInput = By.xpath("//input[@id='location']")
    private val birthdayInput = By.xpath("//input[@id='birthday']")
//    private val maleGenderRadio = By.xpath("//label[contains(@class, 'form-check-label') and contains(text(), 'Кун')]")
//    private val femaleGenderRadio = By.xpath("//label[contains(@class, 'form-check-label') and contains(text(), 'Тян')]")

    private val maleGenderRadio = By.xpath("//input[@id='gender_m']")
    private val femaleGenderRadio = By.xpath("//input[@id='gender_f']")
    private val aboutTextarea = By.xpath("//textarea[@id='profile_about']")
    private val saveButton = By.xpath("//button[@id='user_profile_button']")
    private val successInfo = By.xpath("//div[contains(@class, 'profile-info') and not(contains(@style, 'display: none'))]")

    private val publicProfileTitle = By.xpath("//p[contains(@class, 'profile-box__title')]")
    private val publicAboutItems = By.xpath("//div[contains(@class, 'about-item')]")
    private val publicAboutText = By.xpath("//div[contains(@class, 'about-flex')]//p[contains(@class, 'about-item__text')]")

    fun openEdit(): PersonalProfilePage {
        webDriver.get("https://anison.fm/user/profile")
        wait.until(ExpectedConditions.visibilityOfElementLocated(editForm))
        return this
    }

    fun openPublic(): PersonalProfilePage {
        webDriver.get("https://anison.fm/user/101056")
        wait.until(ExpectedConditions.visibilityOfElementLocated(publicProfileTitle))
        return this
    }

    fun updatePersonalData(data: PersonalProfileData): PersonalProfilePage {
        setGender(data.gender)
        setLocation(data.location)
        setBirthday(data.birthday)
        setAbout(data.about)
        save()
        return this
    }

    fun setGender(gender: String): PersonalProfilePage {
        val genderRadio = when (gender) {
            "Кун", "M" -> maleGenderRadio
            "Тян", "F" -> femaleGenderRadio
            else -> error("Unsupported gender value: $gender")
        }
        val input = wait.until(ExpectedConditions.presenceOfElementLocated(genderRadio))

        (webDriver as JavascriptExecutor).executeScript(
            """
        arguments[0].checked = true;
        arguments[0].dispatchEvent(new Event('input', { bubbles: true }));
        arguments[0].dispatchEvent(new Event('change', { bubbles: true }));
        """.trimIndent(),
            input
        )

        return this
    }

    fun setLocation(location: String): PersonalProfilePage {
        fill(locationInput, location)
        return this
    }

    fun setBirthday(birthday: String): PersonalProfilePage {
        fill(birthdayInput, birthday)
        return this
    }

    fun setAbout(about: String): PersonalProfilePage {
        fill(aboutTextarea, about)
        return this
    }

    fun save(): PersonalProfilePage {
        val button = wait.until(ExpectedConditions.presenceOfElementLocated(saveButton))

        (webDriver as JavascriptExecutor).executeScript(
            "arguments[0].click();",
            button
        )

        wait.until(ExpectedConditions.visibilityOfElementLocated(successInfo))
        return this
    }

    fun isEditLocatorsConfigured(): Boolean {
        return true
    }

    fun getDisplayedGender(): String {
        return getPublicAboutValueByPosition(0)
    }

    fun getDisplayedLocation(): String {
        return getPublicAboutValueByPosition(1)
    }

    fun getDisplayedBirthday(): String {
        return getPublicAboutValueByPosition(2)
    }

    fun getDisplayedAbout(): String {
        return wait
            .ignoring(StaleElementReferenceException::class.java)
            .until {
                webDriver.findElement(publicAboutText).text.trim().takeIf { it.isNotBlank() }
            }.orEmpty()
    }

    fun isPersonalDataDisplayed(data: PersonalProfileData): Boolean {
        println(PersonalProfileData(getDisplayedGender(), getDisplayedLocation(), normalizeDate(getDisplayedBirthday()),getDisplayedAbout() ))
        println(getDisplayedGender()==data.gender)
        println(getDisplayedLocation()==data.location)
        println(normalizeDate(getDisplayedBirthday()) == normalizeDate(data.birthday))
        println(getDisplayedAbout() == data.about)
        return getDisplayedGender() == data.gender &&
            getDisplayedLocation() == data.location &&
            getDisplayedBirthday() == expectedBirthdayForPublicProfile(data.birthday) &&
            getDisplayedAbout() == data.about
    }

    private fun fill(locator: By, value: String) {
        val field = wait.until(ExpectedConditions.elementToBeClickable(locator))
        field.clear()
        field.sendKeys(value)
        if (field.getDomAttribute("value") != value) {
            (webDriver as JavascriptExecutor).executeScript(
                "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
                field,
                value
            )
        }
    }

    private fun getPublicAboutValueByPosition(index: Int): String {
        return wait
            .ignoring(StaleElementReferenceException::class.java)
            .until {
                webDriver.findElements(publicAboutItems)
                    .getOrNull(index)
                    ?.findElement(By.xpath(".//p[contains(@class, 'about-item__text')]"))
                    ?.text
                    ?.trim()
                    ?.takeIf { it.isNotBlank() }
            }.orEmpty()
    }

    private fun normalizeDate(value: String): String {
        return value.replace(".", "-")
    }
    private fun expectedBirthdayForPublicProfile(value: String): String {
        val parts = value.split("-")
        val day = parts[0].toInt()
        val month = parts[1].toInt()

        val monthName = when (month) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "августа"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            12 -> "декабря"
            else -> error("Unsupported month: $month")
        }

        return "$day $monthName"
    }
}
