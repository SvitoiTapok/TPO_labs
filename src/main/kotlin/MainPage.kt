package com.example

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

enum class SearchType(
    val itemClass: String,
    val title: String,
) {
    ANIME("anime", "\u0410\u043d\u0438\u043c\u0435"),
    TRACK("track", "\u0422\u0440\u0435\u043a"),
    ARTIST("artist", "\u0410\u0440\u0442\u0438\u0441\u0442");
}

class MainPage(
    private val webDriver: WebDriver,
) : Page(webDriver) {

    private val wait = WebDriverWait(webDriver, Duration.ofSeconds(10))

    private val playerWrapper = By.xpath("(//div[contains(@class, 'player-wrapper')])")

    private val playButton = By.xpath("(.//button[contains(@class, 'song-play')])")

    private val startIcon = By.xpath(
        ".//*[local-name()='svg' and contains(@class, 'song-play__start')]"
    )

    private val stopIcon = By.xpath(
        ".//*[local-name()='svg' and contains(@class, 'song-play__stop')]"
    )

    private val favoriteButton = By.xpath("//button[contains(@id, 'ski')]")

    private val favoritesLink = By.xpath("(//a[contains(@href, '/favorites')])")
    private val songTitle = By.xpath("(//a[contains(@class, 'song-box__title anime_link')])")
    private val modalBody = By.xpath("//div[contains(@class, 'modal-body')]")
    private val searchInput = By.xpath(
        "(//div[contains(@class, 'search-wrapper') and not(contains(@class, 'search-wrapper--mobile'))]" +
            "//input[@id='search' and contains(@class, 'search-box__input')])[1]"
    )
    private val searchDropdownButton = By.xpath(
        "(//div[contains(@class, 'search-wrapper') and not(contains(@class, 'search-wrapper--mobile'))]" +
            "//button[contains(@class, 'search-dropdown__title')])[1]"
    )
    private val searchResultTitleXpath = By.xpath("(//div[contains(@class, 'search-wrapper') and not(contains(@class, 'search-wrapper--mobile'))]" + "//p[contains(@class, 'validation-item__title')])")


    fun open(): MainPage {
        webDriver.get("https://anison.fm/")
        wait.until(ExpectedConditions.elementToBeClickable(playButton))
        waitUntilSongStopped()
        return this
    }

    fun pressPlay(): MainPage {
        waitUntilNoVisibleModal()

        wait
            .ignoring(StaleElementReferenceException::class.java)
            .ignoring(ElementClickInterceptedException::class.java)
            .until {
                val button = webDriver.findElement(playerWrapper).findElement(playButton)
                button.click()
                true
            }

        return this
    }

    fun isSongPlaying(): Boolean {
        val button = webDriver.findElement(playButton)

        val startClass = button.findElement(startIcon)
            .getDomAttribute("class")
            .orEmpty()

        val stopClass = button.findElement(stopIcon)
            .getDomAttribute("class")
            .orEmpty()

        return startClass.contains("hide") && !stopClass.contains("hide")
    }

    fun isSongStopped(): Boolean {
        val button = webDriver.findElement(playButton)

        val startClass = button.findElement(startIcon)
            .getDomAttribute("class")
            .orEmpty()

        val stopClass = button.findElement(stopIcon)
            .getDomAttribute("class")
            .orEmpty()

        return !startClass.contains("hide") && stopClass.contains("hide")
    }

    fun waitUntilSongPlaying(): MainPage {
        wait
            .ignoring(StaleElementReferenceException::class.java)
            .until {
                isSongPlaying()
            }

        return this
    }
    private fun waitUntilNoVisibleModal() {
        wait
            .ignoring(StaleElementReferenceException::class.java)
            .until {
                webDriver.findElements(modalBody).none { it.isDisplayed }
            }
    }

    fun waitUntilSongStopped(): MainPage {
        wait
            .ignoring(StaleElementReferenceException::class.java)
            .until {
                isSongStopped()
            }

        return this
    }

    fun getCurrentSongTitle(): String {
        return wait
            .ignoring(StaleElementReferenceException::class.java)
            .until {
                webDriver.findElement(songTitle).text
            }
    }

    fun addCurrentSongToFavorites(): MainPage {
        waitUntilNoVisibleModal()
        wait
            .ignoring(StaleElementReferenceException::class.java)
            .until {
                val button = webDriver.findElements(favoriteButton)
                    .firstOrNull { it.isDisplayed && it.isEnabled }
                    ?: return@until false
                button.click()
                true
            }

        return this
    }

    fun isFavoriteHighlighted(): Boolean {
        return try {
            wait
                .ignoring(StaleElementReferenceException::class.java)
                .until {
                    val button = webDriver.findElement(favoriteButton)

                    val emptyClass = button.findElement(
                        By.xpath(".//*[local-name()='svg' and contains(@class, 'song-like__empty')]")
                    )
                        .getDomAttribute("class")
                        .orEmpty()

                    val fillClass = button.findElement(
                        By.xpath(".//*[local-name()='svg' and contains(@class, 'song-like__fill')]")
                    )
                        .getDomAttribute("class")
                        .orEmpty()
                    val highlighted = emptyClass.contains("hide") && !fillClass.contains("hide")
                    if (highlighted) true else null
                }
            true
        } catch (e: TimeoutException) {
            false
        }
    }


    fun openFavorites(): MainPage {
        wait.until(ExpectedConditions.elementToBeClickable(favoritesLink)).click()
        wait.until(ExpectedConditions.urlContains("/favorites"))
        return this
    }

    fun isSongInFavorites(songTitle: String): Boolean {
        try {
            wait
                .ignoring(StaleElementReferenceException::class.java)
                .until {
                    webDriver.findElements(By.xpath("//p[contains(@class, 'tracks-heading__title')]"))
                        .find { el -> el.text == songTitle }
                }
            return true
        } catch (ex: TimeoutException) {
            return false
        }
    }

    fun search(query: String, type: SearchType = SearchType.ANIME): MainPage {
        selectSearchType(type)

        val input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput))
        input.clear()
        input.sendKeys(query)

        return this
    }

    fun selectSearchType(type: SearchType): MainPage {
        waitUntilNoVisibleModal()
        wait.until(ExpectedConditions.elementToBeClickable(searchDropdownButton)).click()
        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath(
                    "(//div[contains(@class, 'search-wrapper') and not(contains(@class, 'search-wrapper--mobile'))]" +
                        "//li[contains(@class, 'search-item') and contains(@class, '${type.itemClass}')])[1]"
                )
            )
        ).click()

        wait.until {
            getSelectedSearchType() == type.title
        }

        return this
    }


    fun getSelectedSearchType(): String {
        return wait
            .until(ExpectedConditions.visibilityOfElementLocated(searchDropdownButton))
            .text
            .trim()
    }

    fun isSearchResultFound(expectedText: String): Boolean {

        return try {
            wait
                .ignoring(StaleElementReferenceException::class.java)
                .until {
                    webDriver.findElements(searchResultTitleXpath)
                        .any { it.text.contains(expectedText, ignoreCase = true) }
                }
        } catch (e: TimeoutException) {
            false
        }
    }

    fun getNickname(): String? {
        return wait
            .ignoring(StaleElementReferenceException::class.java)
            .until {
                val header = webDriver.findElement(
                    By.xpath("//div[contains(@class, 'header-login')]")
                )

                header
                    .findElements(By.xpath(".//p[contains(@class, 'profile-block__title')]"))
                    .firstOrNull()
                    ?.text
                    ?.trim()
                    ?.takeIf { it.isNotBlank() }
            }
    }
}
