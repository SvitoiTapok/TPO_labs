package task3

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import task3.enums.Title
import task3.essences.Person

class PersonTest {

        @Test
        fun creatingPersonWithEmptyNameTest() {
            assertThrows<IllegalArgumentException> {
                Person("")
            }
        }

        @Test
        fun defaultStateOfPersonTest() {
            val person = Person("Форд Префект")
            assertEquals(Title.DEFAULT, person.title)
            assertFalse(person.hasTowel)
            assertEquals(0, person.relationsCount)
        }
        @Test
        fun becomesHipelTest() {
            val person = Person("Форд Префект")

            person.pack()

            assertEquals(Title.HIPEL, person.title)
            assertTrue(person.isSolidPacked())
            assertFalse(person.isVerySolidPacked())

        }

        @Test
        fun becomesFroktFromHipelTest() {
            val person = Person("Форд Префект")

            person.pack()
            person.solidPack()

            assertEquals(Title.FROKT, person.title)
            assertTrue(person.isSolidPacked())
            assertTrue(person.isVerySolidPacked())
        }

        @Test
        fun becomesFroktFromFrocktOrDefaultTest() {
            val person = Person("Форд Префект")

            assertThrows<IllegalStateException> {
                person.solidPack()
            }
            person.pack()
            person.solidPack()
            assertEquals(Title.FROKT, person.title)
            assertThrows<IllegalStateException> {person.solidPack()}
        }

        @Test
        fun becomesHipelFromFroktOrHipelTest() {
            val person = Person("Форд Префект")
            person.pack()

            assertThrows<IllegalStateException> {
                person.pack()
            }
            person.solidPack()
            assertThrows<IllegalStateException> {
                person.pack()
            }
        }

        @Test
        fun towelTest() {
            val person = Person("Форд Префект")
            person.takeTowel()
            assertTrue(person.hasTowel)
            person.loseTowel()
            assertFalse(person.hasTowel)
            assertThrows<IllegalStateException>(){person.loseTowel()}
            person.takeTowel()
            assertThrows<IllegalStateException>(){person.takeTowel()}
        }
}