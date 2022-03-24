package chess.board

class Piece(var type: PieceType, var color: PieceColor) {
    override fun toString(): String {
        return "Piece-" +
                type +
                "-" +
                color
    }

}

fun getTypeFromString(string: String): PieceType? {

    for (type in PieceType.values()) {
        if (string == type.toString()) {
            return type
        }
    }

    return null
}

fun getColorFromString(string: String): PieceColor? {
    for (color in PieceColor.values()) {
        if (string == color.toString()) {
            return color
        }
    }

    return null
}

fun getPieceFromString(string: String): Piece? {
    if (string == "null") {
        return null
    }
    val list = string.split("-")
    val type = getTypeFromString(list[1])!!
    val color = getColorFromString(list[2])!!
    return Piece(type, color)
}

