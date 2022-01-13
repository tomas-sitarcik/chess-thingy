package chess.board

fun main(args: Array<String>) {

    var things = getHorizontalDistances(intArrayOf(5, 6))
    var stuffs = getDiagonalDistances(things)

    //var board = initBoard()
    var board = Array(8) { Array<Piece?>(8) { i -> null } }
    println(boardString(board))

    var moves = getMovesFromHorizontalDistances(intArrayOf(5, 4), board)
    if (moves != null) {
        for (move in moves) {
           print(move[0])
            print(", ")
           println(move[1])
        }
    }

    println("diagonals ahead:")
    var movesA = getMovesFromDiagonalDistances(intArrayOf(5, 4), board)
    if (movesA != null) {
        for (move in movesA) {
            print(move[0])
            print(", ")
            println(move[1])
        }
    }



    for (thing in things) {
        print(thing)
    }

    print("\n")

    for (stuff in stuffs) {
        print(stuff)
    }
}