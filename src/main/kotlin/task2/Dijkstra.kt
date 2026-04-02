package task2

class Dijkstra {
    data class Vertex(var known: Boolean, var cost: Int, var path: List<Int>)
    data class Result(
        val trace: List<String>,
        val table: MutableList<Vertex>
    )

    fun dijkstraAlgorithm(graph: List<List<Edge>>, v: Int): Result {
        if(graph.size<=v) throw IllegalArgumentException("v must be lower than graph size")
        val trace = mutableListOf<String>()
        val graphStat: MutableList<Vertex> = MutableList(graph.size) { Vertex(false, Int.MAX_VALUE, emptyList()) }
        trace.add("(1)Инициализация стартовой вершины $v")
        graphStat[v] = Vertex(false, 0, listOf(v))

        while (true) {
            var curVertex = -1
            var bestCost = Int.MAX_VALUE

            for (i in graph.indices) {
                if (!graphStat[i].known && graphStat[i].cost < bestCost) {
                    bestCost = graphStat[i].cost
                    curVertex = i
                }
            }

            if (curVertex == -1) {
                trace.add("(5)Завершение алгоритма")
                break
            }
            trace.add("(2)Переход на вершину $curVertex")
            graphStat[curVertex].known = true

            for (edge in graph[curVertex]) {
                val newCost = graphStat[curVertex].cost + edge.weight
                if (newCost < graphStat[edge.to].cost) {
                    trace.add("(3)Найден более короткий путь до вершины ${edge.to}, теперь кратчайший путь до нее не ${graphStat[edge.to].cost}, а $newCost")
                    graphStat[edge.to].cost = newCost
                    graphStat[edge.to].path = graphStat[curVertex].path + edge.to
                }
            }
            trace.add("(4)Все пути из вершины ${curVertex} были рассмотренны")
        }
        return Result(trace, graphStat)
    }
}

fun main() {
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
    val trace = Dijkstra().dijkstraAlgorithm(graph, 5).table
    trace.forEach(::println)
}