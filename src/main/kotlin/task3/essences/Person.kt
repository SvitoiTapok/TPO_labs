package task3.essences

import task3.enums.Title

data class Person(
    val name: String,
    private var currentTitle: Title = Title.DEFAULT,
    private var towel: Boolean = false,
    var relationsCount: Int = 0
) {
    init {
        require(name.isNotBlank()) { "Имя не должно быть пустым" }
    }
    val title: Title
        get() = currentTitle
    val hasTowel: Boolean
        get() = towel


    fun isSolidPacked(): Boolean {
        return currentTitle == Title.HIPEL || currentTitle == Title.FROKT
    }

    fun isVerySolidPacked(): Boolean {
        return currentTitle == Title.FROKT
    }

    fun pack() {
        check(currentTitle == Title.DEFAULT) {
            "Стать хипелем можно только из состояния по умолчанию"
        }
        currentTitle = Title.HIPEL
    }

    fun solidPack() {
        check(currentTitle == Title.HIPEL) {
            "Стать фроктом можно только из хипеля"
        }
        currentTitle = Title.FROKT
    }

    fun takeTowel() {
        check(!towel) { "У $name уже есть полотенце" }
        towel = true
    }
    fun loseTowel() {
        check(towel) { "У $name уже нет полотенца" }
        towel = false
    }
}