package lab5

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)

fun createGameBoard(width: Int): GameBoard = GameBoardImpl(width)

open class SquareBoardImpl(override val width: Int) : SquareBoard {
    private val cells: Array<Array<Cell>> = Array(width) {
        i -> Array(width) { j -> Cell(i + 1, j + 1) }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? = if (i in 1..width && j in 1..width) cells[i - 1][j - 1] else null

    override fun getCell(i: Int, j: Int): Cell = getCellOrNull(i, j) ?: throw IllegalArgumentException("Incorrect values ($i, $j), out of bounds")

    override fun getAllCells(): Collection<Cell> = cells.flatten()

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> = jRange.filter { it in 1..width }.map { j -> cells[i - 1][j - 1] }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> = iRange.filter { it in 1..width }.map { i -> cells[i - 1][j - 1] }

    override fun Cell.getNeighbour(direction: Direction): Cell? = when (direction) {
        Direction.UP -> getCellOrNull(i - 1, j)
        Direction.DOWN -> getCellOrNull(i + 1, j)
        Direction.LEFT -> getCellOrNull(i, j - 1)
        Direction.RIGHT -> getCellOrNull(i, j + 1)
    }
}

class GameBoardImpl(width: Int) : SquareBoardImpl(width), GameBoard {
    private val values: MutableMap<Cell, String?> = getAllCells().associateWith { null }.toMutableMap()

    override fun get(cell: Cell): String? = values[cell]

    override fun set(cell: Cell, value: String?) {
        values[cell] = value
    }

    override fun filter(predicate: (String?) -> Boolean): Collection<Cell> = values.keys.filter { predicate(values[it]) }

    override fun find(predicate: (String?) -> Boolean): Cell? = values.keys.find { predicate(values[it]) }

    override fun any(predicate: (String?) -> Boolean): Boolean = values.values.any(predicate)

    override fun all(predicate: (String?) -> Boolean): Boolean = values.values.all(predicate)
}