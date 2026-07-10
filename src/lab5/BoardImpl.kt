package lab5

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)

fun createGameBoard(width: Int): GameBoard = GameBoardImpl(width)

open class SquareBoardImpl(size: Int) : SquareBoard {
    override val width: Int = size

    private val cells: List<Cell> = (1..width).flatMap { i -> (1..width).map { j -> Cell(i, j) } }
    private val cellsByCoordinates: Map<Pair<Int, Int>, Cell> = cells.associateBy { it.i to it.j }

    override fun getCellOrNull(i: Int, j: Int): Cell? = cellsByCoordinates[i to j]

    override fun getCell(i: Int, j: Int): Cell =
        getCellOrNull(i, j) ?: throw IllegalArgumentException("Cell ($i, $j) does not exist on a board of width $width")

    override fun getAllCells(): Collection<Cell> = cells

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> =
        jRange.mapNotNull { j -> getCellOrNull(i, j) }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> =
        iRange.mapNotNull { i -> getCellOrNull(i, j) }

    override fun Cell.getNeighbour(direction: Direction): Cell? = when (direction) {
        Direction.UP -> getCellOrNull(i - 1, j)
        Direction.DOWN -> getCellOrNull(i + 1, j)
        Direction.RIGHT -> getCellOrNull(i, j + 1)
        Direction.LEFT -> getCellOrNull(i, j - 1)
    }
}

class GameBoardImpl(size: Int) : SquareBoardImpl(size), GameBoard {
    private val values: MutableMap<Cell, String?> = mutableMapOf()

    override fun get(cell: Cell): String? = values[cell]

    override fun set(cell: Cell, value: String?) {
        values[cell] = value
    }

    override fun filter(predicate: (String?) -> Boolean): Collection<Cell> =
        getAllCells().filter { predicate(values[it]) }

    override fun find(predicate: (String?) -> Boolean): Cell? =
        getAllCells().find { predicate(values[it]) }

    override fun any(predicate: (String?) -> Boolean): Boolean =
        getAllCells().any { predicate(values[it]) }

    override fun all(predicate: (String?) -> Boolean): Boolean =
        getAllCells().all { predicate(values[it]) }
}