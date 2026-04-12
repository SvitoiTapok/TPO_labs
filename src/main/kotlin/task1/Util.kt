package task1

fun bernully(n:Int):Double {
    if (n == 0) return 1.0
    if(n%2 == 1 && n>1) return 0.0

    var sum = 0.0
    for (i in 0..n-1) {
        sum += combC(n+1, i)* bernully(i)
    }
    return -1.0/(n+1)*sum

}

fun combC(n: Int, m: Int):Long {
    return factorial(n)/ factorial(m)/ factorial(n-m)
}

fun factorial(n: Int): Long {
    if (n == 0) return 1
    return n*factorial(n-1)
}