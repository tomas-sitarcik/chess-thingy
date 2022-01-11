package chess.board

fun main(args: Array<String>) {

    var things = getHorizontalDistances(intArrayOf(5, 6))
    var stuffs = getDiagonalDistances(things)

    var board = initBoard()
    println(boardString(board))
    board[2][1] = null
    println(boardString(board))

    for (number in things) {
        print(number)
    }

    print("\n")

    for (stuff in stuffs) {
        print(stuff)
    }
}