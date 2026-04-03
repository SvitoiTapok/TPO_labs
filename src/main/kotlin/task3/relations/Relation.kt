package task3.relations

import task3.enums.RelationState
import task3.essences.Person
import java.lang.IllegalStateException

open class Relation(
    private val subject: Person,
    private val target: Person,
    private var currentState: RelationState = RelationState.CREATED
) {
    val state: RelationState
        get() = currentState

    fun involves(person: Person): Boolean {
        return subject == person || target == person
    }

    fun isFrom(person: Person): Boolean {
        return subject == person
    }

    fun isTo(person: Person): Boolean {
        return target == person
    }
    fun activate(){
        if (currentState == RelationState.CREATED || currentState == RelationState.TERMINATED) {
            currentState = RelationState.ACTIVE
            subject.relationsCount++
            target.relationsCount++
        }else{
            throw IllegalStateException("отношение уже активно")
        }

    }
    fun terminate(){
        if (currentState == RelationState.ACTIVE) {
            currentState = RelationState.TERMINATED
            subject.relationsCount--
            target.relationsCount--
        }else{
            throw IllegalStateException("отношение уже было завершено")
        }
    }
}