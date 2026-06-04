package lab5

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)

fun createGameBoard(width: Int): GameBoard = GameBoardImpl(width)

class SquareBoardImpl(size: Int) : SquareBoard {
    override val width: Int = size

    private val cells =
        (1..width).flatMap { i ->
            (1..width).map { j ->
                Cell(i, j)
            }
        }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if (i !in 1..width)
            return null
        if (j !in 1..width)
            return null

        return cells.first{ it.i == i && it.j == j}
    }

    override fun getCell(i: Int, j: Int): Cell {
        require(i in 1..width)
        require(j in 1..width)

        return cells.first{ it.i == i && it.j == j}
    }

    override fun getAllCells(): Collection<Cell> {
        return cells
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        require(i in 1..width)

        val row = (i - 1) * width

        return jRange.filter { j -> j in 1..width }
            .map { j -> cells[row + (j - 1)] }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        require(j in 1..width)

        return iRange.filter { i -> i in 1..width }
            .map { i -> cells[(i - 1) * width + (j - 1)] }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            Direction.LEFT  -> getCellOrNull(i, j - 1)
            Direction.RIGHT -> getCellOrNull(i, j + 1)
            Direction.UP    -> getCellOrNull(i - 1, j)
            Direction.DOWN  -> getCellOrNull(i + 1, j)
        }
    }

}

class GameBoardImpl(size: Int) : GameBoard, SquareBoard by SquareBoardImpl(size) {
    private val values = mutableMapOf<Cell, String?>()

    override fun get(cell: Cell): String? = values[cell]

    override fun set(cell: Cell, value: String?) {
        values[cell] = value
    }

    override fun filter(predicate: (String?) -> Boolean): Collection<Cell> {
        return getAllCells().filter { predicate(values[it]) }
    }

    override fun find(predicate: (String?) -> Boolean): Cell? {
        return getAllCells().find { predicate(values[it]) }
    }

    override fun any(predicate: (String?) -> Boolean): Boolean {
        return getAllCells().any { predicate(values[it]) }
    }

    override fun all(predicate: (String?) -> Boolean): Boolean {
        return getAllCells().all { predicate(values[it]) }
    }


}