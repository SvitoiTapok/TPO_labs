package task3.relations

import task3.enums.ZuchitMeaning
import task3.essences.Person

class ZuchitRelation(
    subject: Person,
    target: Person,
    private val meanings: Set<ZuchitMeaning>
) : Relation(subject, target) {

    init {
        require(meanings.isNotEmpty()) {
            "Отношение 'зючить' должно иметь хотя бы одно значение"
        }
    }

    fun hasMeaning(meaning: ZuchitMeaning): Boolean {
        return meaning in meanings
    }
}