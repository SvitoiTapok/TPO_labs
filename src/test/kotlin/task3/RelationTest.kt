package task3

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import task3.enums.RelationState
import task3.enums.ZuchitMeaning
import task3.essences.Person
import task3.relations.Relation
import task3.relations.ZuchitRelation

class RelationTest {




    @Test
    fun createdStateTest() {
        val person = Person("No Name")
        val ford = Person("Форд Перфект")

        val relation = Relation(person, ford)

        assertEquals(RelationState.CREATED, relation.state)
    }

    @Test
    fun repeatableActivationTest() {
        val person = Person("No Name")
        val ford = Person("Форд Перфект")

        val relation = Relation(person, ford)
        relation.activate()

        assertThrows<IllegalStateException> {
            relation.activate()
        }
    }

    @Test
    fun terminateRelationTest() {
        val person = Person("No Name")
        val ford = Person("Форд")

        val relation = Relation(person, ford)
        relation.activate()
        relation.terminate()

        assertEquals(RelationState.TERMINATED, relation.state)
    }

    @Test
    fun terminateFromWrongStateTest() {
        val person = Person("No Name")
        val ford = Person("Форд")

        val relation = Relation(person, ford)
        assertThrows<IllegalStateException> {
            relation.terminate()
        }
        relation.activate()
        relation.terminate()
        assertThrows<IllegalStateException> {
            relation.terminate()
        }

    }

    @Test
    fun relationCounterTest() {
        val person = Person("No Name")
        val person2 = Person("No Name 2")
        val ford = Person("Форд Перфект")

        val r1 = Relation(person, ford)
        r1.activate()

        assertEquals(1, person.relationsCount)
        assertEquals(1, ford.relationsCount)

        val r2 = Relation(person2, ford)
        r2.activate()
        assertEquals(2, ford.relationsCount)
        r1.terminate()
        assertEquals(0, person.relationsCount)
        assertEquals(1, ford.relationsCount)

    }


    @Test
    fun participantCheckTest() {
        val person = Person("No Name")
        val person2 = Person("No Name 2")
        val ford = Person("Форд Перфект")

        val relation = Relation(person, ford)

        assertTrue(relation.involves(person))
        assertTrue(relation.involves(ford))
        assertFalse(relation.involves(person2))

        assertTrue(relation.isFrom(person))
        assertTrue(relation.isTo(ford))
        assertFalse(relation.isFrom(ford))
        assertFalse(relation.isTo(person))
    }
    @Test
    fun zuchitHimselfTest() {
        val person = Person("No Name")

        assertThrows<IllegalArgumentException> {
            ZuchitRelation(person, person, emptySet())
        }
    }

    @Test
    fun zuchitRelationEmptyStateTest() {
        val person = Person("No Name")
        val ford = Person("Форд Перфект")

        assertThrows<IllegalArgumentException> {
            ZuchitRelation(person, ford, emptySet())
        }
    }

    @Test
    fun zuchitRelationUnemptyStateTest() {
        val person = Person("No Name")
        val ford = Person("Форд Перфект")

        val relation = ZuchitRelation(
            person,
            ford,
            setOf(ZuchitMeaning.KNOW, ZuchitMeaning.HEARD_OF)
        )
        assertTrue(relation.hasMeaning(ZuchitMeaning.KNOW))
        assertTrue(relation.hasMeaning(ZuchitMeaning.HEARD_OF))
        assertFalse(relation.hasMeaning(ZuchitMeaning.MET))
    }


    @Test
    fun zuchitRelationLifeCircleTest() {
        val person = Person("No Name")
        val ford = Person("Форд Перфект")

        val relation = ZuchitRelation(
            person,
            ford,
            setOf(ZuchitMeaning.KNOW, ZuchitMeaning.MET)
        )
        assertEquals(RelationState.CREATED, relation.state)

        relation.activate()
        assertEquals(RelationState.ACTIVE, relation.state)

        relation.terminate()
        assertEquals(RelationState.TERMINATED, relation.state)
    }
}