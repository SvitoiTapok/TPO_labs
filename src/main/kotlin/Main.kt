package com.example

import java.io.File

fun main() {
    val driver = WebDriverFactory.create(WebDriverFactory.Browser.CHROME)
//
//
//    val login = LoginPage(driver).open()
//    login.safeLogin("svitoi_tapok1", "000Tt111")
//    driver.get("https://anison.fm/user/profile")
//    val html = driver.pageSource
//
//    File("page_dom3.2.html").writeText(html)
    LoginPage(driver)
        .open()
        .safeLogin("svitoi_tapok1", "000Tt111")
    val profilePage = PersonalProfilePage(driver)

    val timestamp = System.currentTimeMillis()
    val personalData = PersonalProfileData(
        gender = "Кун",
        location = "Autotest City $timestamp",
        birthday = "01-01-2001",
        about = "Autotest profile about $timestamp"
    )

    profilePage
        .openEdit()
        .updatePersonalData(personalData)

//    val chatPage = ChatPage(driver)
//    val loginPage = LoginPage(driver)
//    loginPage.open()
//    loginPage.login("svitoi_tapok1", "000Tt111")
//    chatPage.open()
//    chatPage.typeMessage("dsafa")
//    val main = MainPage(driver)
//    val login = LoginPage(driver)
//    main.open()
//    main.pressPlay()
//    //main.addCurrentSongToFavorites()
//    login.open()

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