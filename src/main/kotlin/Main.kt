package com.example

fun main() {
    val driver = WebDriverFactory.create(WebDriverFactory.Browser.CHROME)
//    val main = MainPage(driver)
//    main.open()
//    main.pressPlay()
    val registrationPage = RegistrationPage(driver)
    registrationPage.open()
    registrationPage.register("svitoi_tapok", "CXw4md", "d")
    println(registrationPage.isNicknameAvailable())
    registrationPage.putRegistrationLogin("dafsefesfae")
    println(registrationPage.isNicknameAvailable())
//    val enterPage = LoginPage(driver)
//    enterPage.open()
//    enterPage.login("dfa", "fdaf")
//    println(enterPage.getError())
}