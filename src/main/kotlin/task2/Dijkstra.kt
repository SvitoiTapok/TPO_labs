package task2

object Dijkstra {
    data class Vertex(var known: Boolean, var cost: Int, var path: List<Int>)

    fun dijkstraAlgorithm(graph: List<List<Edge>>, v: Int, logger: ((String)->Unit)?=null): MutableList<Vertex> {
        if(graph.size<=v) throw IllegalArgumentException("v must be lower than graph size")
        val graphStat: MutableList<Vertex> = MutableList(graph.size) { Vertex(false, Int.MAX_VALUE, emptyList()) }
        logger?.invoke("(1)Инициализация стартовой вершины $v")
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
                logger?.invoke("(5)Завершение алгоритма")
                break
            }
            logger?.invoke("(2)Переход на вершину $curVertex")
            graphStat[curVertex].known = true

            for (edge in graph[curVertex]) {
                val newCost = graphStat[curVertex].cost + edge.weight
                if (newCost < graphStat[edge.to].cost) {
                    logger?.invoke("(3)Найден более короткий путь до вершины ${edge.to}, теперь кратчайший путь до нее не ${graphStat[edge.to].cost}, а $newCost")
                    graphStat[edge.to].cost = newCost
                    graphStat[edge.to].path = graphStat[curVertex].path + edge.to
                }
            }
            logger?.invoke("(4)Все пути из вершины ${curVertex} были рассмотренны")
        }
        return graphStat
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
    Dijkstra.dijkstraAlgorithm(graph, 5) { m -> println(m) }
}