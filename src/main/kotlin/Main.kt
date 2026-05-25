package com.example

import java.io.File

fun main() {
    val driver = WebDriverFactory.create(WebDriverFactory.Browser.CHROME)

    val mainPage = MainPage(driver)
        .open()
        .search("Chaos Head", SearchType.ANIME)
    println(mainPage.isSearchResultFound("Chaos Head"))
//    val main = MainPage(driver)
//    val login = LoginPage(driver)
//    main.open()
//    main.pressPlay()
//    //main.addCurrentSongToFavorites()
//    login.open()
//    login.login("svitoi_tapok1", "000Tt111").addCurrentSongToFavorites()
//    println( main.isFavoriteHighlighted())
//    println( main.getCurrentSongTitle())
    //driver.close()
//    val registrationPage = RegistrationPage(driver)
//    registrationPage.open()
//    registrationPage.register("svitoi_tapok", "CXw4md", "d")
//    println(registrationPage.isNicknameAvailable())
//    registrationPage.putRegistrationLogin("dafsefesfae")
//    println(registrationPage.isNicknameAvailable())
//    val enterPage = LoginPage(driver)
//    enterPage.open()
//    enterPage.login("dfa", "fdaf")
//    println(enterPage.getError())
}