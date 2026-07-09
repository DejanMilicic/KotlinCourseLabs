package lab5

// TODO Instantiate SquareBoard
fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)

// TODO Instantiate GameBoard
fun createGameBoard(width: Int): GameBoard = GameBoardImpl(width)

// TODO Implement SquareBoard Interface
open class SquareBoardImpl(override val width: Int): SquareBoard {
    private val cells: List<Cell> = (1..width).flatMap {
        i -> (1..width).map { j -> Cell(i, j) }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? =
        cells.find { it.i == i && it.j == j }

    override fun getCell(i: Int, j: Int): Cell =
        getCellOrNull(i,j) ?: throw IllegalArgumentException("No getCell at index $i, $j")

    override fun getAllCells(): Collection<Cell> = cells

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        return jRange.mapNotNull { j-> getCellOrNull(i,j) }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        return iRange.mapNotNull { i-> getCellOrNull(i,j) }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? =
        when (direction) {
            Direction.UP -> getCellOrNull(i-1, j)
            Direction.DOWN -> getCellOrNull(i+1, j)
            Direction.LEFT -> getCellOrNull(i, j-1)
            Direction.RIGHT -> getCellOrNull(i, j+1)
        }
}

// TODO extend SquareBoardImpl and implement GameBoard interface
class GameBoardImpl(size: Int): SquareBoardImpl(size), GameBoard {
    private val values = mutableMapOf<Cell, String?>()

    override fun get(cell: Cell): String? =
        values[cell]

    override fun set(cell: Cell, value: String?) {
        values[cell] = value
    }

    override fun filter(predicate: (String?)->Boolean): Collection<Cell> =
        getAllCells().filter { predicate(values[it])}

    override fun find(predicate: (String?)->Boolean): Cell?=
        getAllCells().find { predicate(values[it])}

    override fun any(predicate: (String?)->Boolean): Boolean =
        getAllCells().any { predicate(values[it])}

    override fun all(predicate: (String?)->Boolean): Boolean =
        getAllCells().all { predicate(values[it])}
}