package task2


import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class DijkstraTest {
    @Test
    fun emptyGraph() {
        val graph = emptyList<List<Edge>>()
        assertThrows<IllegalArgumentException>(){Dijkstra().dijkstraAlgorithm(graph, 0)}
    }
    @Test
    fun vGreaterThanGraphSize() {
        val graph = listOf(
            listOf(Edge(1, 2)),
            listOf(Edge(2, 3)),
            listOf(Edge(3, 4)),
            listOf(Edge(4, 5)),
            listOf()
        )
        assertThrows<IllegalArgumentException>(){Dijkstra().dijkstraAlgorithm(graph, 10)}
    }
    @Test
    fun linearGraphTest() {
        val graph = listOf(
            listOf(Edge(1, 2)),
            listOf(Edge(2, 3)),
            listOf(Edge(3, 4)),
            listOf(Edge(4, 5)),
            listOf()
        )

        val expectedTrace = listOf(
            "(1)Инициализация стартовой вершины 0",
            "(2)Переход на вершину 0",
            "(3)Найден более короткий путь до вершины 1, теперь кратчайший путь до нее не 2147483647, а 2",
            "(4)Все пути из вершины 0 были рассмотренны",

            "(2)Переход на вершину 1",
            "(3)Найден более короткий путь до вершины 2, теперь кратчайший путь до нее не 2147483647, а 5",
            "(4)Все пути из вершины 1 были рассмотренны",

            "(2)Переход на вершину 2",
            "(3)Найден более короткий путь до вершины 3, теперь кратчайший путь до нее не 2147483647, а 9",
            "(4)Все пути из вершины 2 были рассмотренны",

            "(2)Переход на вершину 3",
            "(3)Найден более короткий путь до вершины 4, теперь кратчайший путь до нее не 2147483647, а 14",
            "(4)Все пути из вершины 3 были рассмотренны",

            "(2)Переход на вершину 4",
            "(4)Все пути из вершины 4 были рассмотренны",

            "(5)Завершение алгоритма"
        )

        val expectedTable = listOf(
            Dijkstra.Vertex(true, 0, listOf(0)),
            Dijkstra.Vertex(true, 2, listOf(0, 1)),
            Dijkstra.Vertex(true, 5, listOf(0, 1, 2)),
            Dijkstra.Vertex(true, 9, listOf(0, 1, 2, 3)),
            Dijkstra.Vertex(true, 14, listOf(0, 1, 2, 3, 4))
        )

        val result: Dijkstra.Result = Dijkstra().dijkstraAlgorithm(graph, 0)

        assertEquals(expectedTrace, result.trace)
        assertEquals(expectedTable, result.table)
    }

    @Test
    fun graphFromSite0() {
        val graph = listOf(
            listOf(Edge(4, 5)),
            listOf(Edge(2, 5)),
            listOf(Edge(1, 8), Edge(0, 8), Edge(4, 4)),
            listOf(Edge(7, 7)),
            listOf(Edge(7, 4)),
            listOf(Edge(3, 6), Edge(1, 9), Edge(6, 5), Edge(7, 5)),
            listOf(Edge(2, 4), Edge(4, 7)),
            listOf(),
        )
        val expectedTrace = listOf(
            "(1)Инициализация стартовой вершины 0",
            "(2)Переход на вершину 0",
            "(3)Найден более короткий путь до вершины 4, теперь кратчайший путь до нее не 2147483647, а 5",
            "(4)Все пути из вершины 0 были рассмотренны",
            "(2)Переход на вершину 4",
            "(3)Найден более короткий путь до вершины 7, теперь кратчайший путь до нее не 2147483647, а 9",
            "(4)Все пути из вершины 4 были рассмотренны",
            "(2)Переход на вершину 7",
            "(4)Все пути из вершины 7 были рассмотренны",
            "(5)Завершение алгоритма"
        )
        val expectedTable = listOf(
            Dijkstra.Vertex(known = true, cost = 0, path = listOf(0)),
            Dijkstra.Vertex(known = false, cost = 2147483647, path = listOf()),
            Dijkstra.Vertex(known = false, cost = 2147483647, path = listOf()),
            Dijkstra.Vertex(known = false, cost = 2147483647, path = listOf()),
            Dijkstra.Vertex(known = true, cost = 5, path = listOf(0, 4)),
            Dijkstra.Vertex(known = false, cost = 2147483647, path = listOf()),
            Dijkstra.Vertex(known = false, cost = 2147483647, path = listOf()),
            Dijkstra.Vertex(known = true, cost = 9, path = listOf(0, 4, 7))
        )
        val x: Dijkstra.Result = Dijkstra().dijkstraAlgorithm(graph, 0)
        assertEquals(expectedTrace, x.trace)
        assertEquals(expectedTable, x.table)
    }

    @Test
    fun graphFromSite5() {
        val graph = listOf(
            listOf(Edge(4, 5)),
            listOf(Edge(2, 5)),
            listOf(Edge(1, 8), Edge(0, 8), Edge(4, 4)),
            listOf(Edge(7, 7)),
            listOf(Edge(7, 4)),
            listOf(Edge(3, 6), Edge(1, 9), Edge(6, 5), Edge(7, 5)),
            listOf(Edge(2, 4), Edge(4, 7)),
            listOf(),
        )

        val expectedTrace = listOf(
            "(1)Инициализация стартовой вершины 5",
            "(2)Переход на вершину 5",
            "(3)Найден более короткий путь до вершины 3, теперь кратчайший путь до нее не 2147483647, а 6",
            "(3)Найден более короткий путь до вершины 1, теперь кратчайший путь до нее не 2147483647, а 9",
            "(3)Найден более короткий путь до вершины 6, теперь кратчайший путь до нее не 2147483647, а 5",
            "(3)Найден более короткий путь до вершины 7, теперь кратчайший путь до нее не 2147483647, а 5",
            "(4)Все пути из вершины 5 были рассмотренны",
            "(2)Переход на вершину 6",
            "(3)Найден более короткий путь до вершины 2, теперь кратчайший путь до нее не 2147483647, а 9",
            "(3)Найден более короткий путь до вершины 4, теперь кратчайший путь до нее не 2147483647, а 12",
            "(4)Все пути из вершины 6 были рассмотренны",
            "(2)Переход на вершину 7",
            "(4)Все пути из вершины 7 были рассмотренны",
            "(2)Переход на вершину 3",
            "(4)Все пути из вершины 3 были рассмотренны",
            "(2)Переход на вершину 1",
            "(4)Все пути из вершины 1 были рассмотренны",
            "(2)Переход на вершину 2",
            "(3)Найден более короткий путь до вершины 0, теперь кратчайший путь до нее не 2147483647, а 17",
            "(4)Все пути из вершины 2 были рассмотренны",
            "(2)Переход на вершину 4",
            "(4)Все пути из вершины 4 были рассмотренны",
            "(2)Переход на вершину 0",
            "(4)Все пути из вершины 0 были рассмотренны",
            "(5)Завершение алгоритма"
        )

        val expectedTable = listOf(
            Dijkstra.Vertex(known = true, cost = 17, path = listOf(5, 6, 2, 0)),
            Dijkstra.Vertex(known = true, cost = 9, path = listOf(5, 1)),
            Dijkstra.Vertex(known = true, cost = 9, path = listOf(5, 6, 2)),
            Dijkstra.Vertex(known = true, cost = 6, path = listOf(5, 3)),
            Dijkstra.Vertex(known = true, cost = 12, path = listOf(5, 6, 4)),
            Dijkstra.Vertex(known = true, cost = 0, path = listOf(5)),
            Dijkstra.Vertex(known = true, cost = 5, path = listOf(5, 6)),
            Dijkstra.Vertex(known = true, cost = 5, path = listOf(5, 7))
        )

        val result: Dijkstra.Result = Dijkstra().dijkstraAlgorithm(graph, 5)

        assertEquals(expectedTrace, result.trace)
        assertEquals(expectedTable, result.table)
    }
}