package chess.board

fun main(args: Array<String>) {

    var things = getHorizontalDistances(intArrayOf(5,2))
    var stuffs = getDiagonalDistances(things)

    for (number in things) {
        print(number)
    }

    print("\n")

    for (stuff in stuffs) {
        print(stuff)
    }





}