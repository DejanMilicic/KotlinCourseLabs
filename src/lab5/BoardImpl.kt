package lab5

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)

fun createGameBoard(width: Int): GameBoard = GameBoardImpl(width)

open class SquareBoardImpl(
    override val width: Int
) : SquareBoard {

    private val cells: List<Cell> =
        (1..width).flatMap { i ->
            (1..width).map { j ->
                Cell(i, j)
            }
        }

    override fun getCellOrNull(i: Int, j: Int): Cell? =
        cells.find { it.i == i && it.j == j }

    override fun getCell(i: Int, j: Int): Cell =
        getCellOrNull(i, j)
            ?: throw IllegalArgumentException("Cell ($i, $j) doesn't exist")

    override fun getAllCells(): Collection<Cell> = cells

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> =
        jRange.mapNotNull { j ->
            getCellOrNull(i, j)
        }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> =
        iRange.mapNotNull { i ->
            getCellOrNull(i, j)
        }

    override fun Cell.getNeighbour(direction: Direction): Cell? =
        when (direction) {
            Direction.UP -> getCellOrNull(i - 1, j)
            Direction.DOWN -> getCellOrNull(i + 1, j)
            Direction.LEFT -> getCellOrNull(i, j - 1)
            Direction.RIGHT -> getCellOrNull(i, j + 1)
        }
}

class GameBoardImpl(size: Int) : SquareBoardImpl(size), GameBoard {
    private val values = getAllCells()
        .associateWith { null as String? }
        .toMutableMap()

    override fun get(cell: Cell): String? = values[cell]

    override fun set(cell: Cell, value: String?) {
        values[cell] = value
    }

    override fun filter(predicate: (String?) -> Boolean): Collection<Cell> =
        values.filter { predicate(it.value) }
            .keys

    override fun find(predicate: (String?) -> Boolean): Cell? =
        values.entries
            .find { predicate(it.value) }
            ?.key

    override fun any(predicate: (String?) -> Boolean): Boolean =
        values.values.any(predicate)

    override fun all(predicate: (String?) -> Boolean): Boolean =
        values.values.all(predicate)
}