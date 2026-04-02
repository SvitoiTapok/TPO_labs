package task3.essences

import task3.enums.Title

data class Person(
    val name: String,
    var title: Title,
    var hasTowel: Boolean = false,
    var relationsCount: Int = 0
) {
    init {
        require(name.isNotBlank()) { "Имя не должно быть пустым" }
    }

    fun isSolidPacked(): Boolean = title == Title.HIPEL || title == Title.FROKT
    fun isVerySolidPacked(): Boolean = title == Title.FROKT

//    fun description(): String {
//        val traits = mutableListOf<String>()
//
//        if (hipel) traits += "хипель"
//        if (frokt) traits += "фрокт"
//        if (alwaysWithTowel) traits += "всегда при полотенце"
//
//        return if (traits.isEmpty()) {
//            name
//        } else {
//            "$name: ${traits.joinToString(", ")}"
//        }
//    }
}