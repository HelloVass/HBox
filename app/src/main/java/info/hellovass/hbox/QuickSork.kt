package info.hellovass.hbox

fun <T : Comparable<T>> List<T>.quickSort(): List<T> {
    return when {
        count() <= 1 ->
            this
        else ->
            takeLast(lastIndex)
                .partition { it < first() }
                .run { first.quickSort() + first() + second.quickSort() }
    }
}

fun main() {
    val result = listOf(3, 5, 7, 8, 1, 2).quickSort()
}