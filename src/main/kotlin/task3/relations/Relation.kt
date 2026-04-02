package task3.relations

import task3.essences.Person

open class Relation(
    private val subject: Person,
    private val target: Person
) {
    init {
        require(subject != target) {
            "Субъект и объект отношения не должны совпадать"
        }
        subject.relationsCount++
        target.relationsCount++
    }

    fun involves(person: Person): Boolean {
        return subject == person || target == person
    }

    fun isFrom(person: Person): Boolean {
        return subject == person
    }

    fun isTo(person: Person): Boolean {
        return target == person
    }
}