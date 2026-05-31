package lab5

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)

fun createGameBoard(width: Int): GameBoard = GameBoardImpl(width)


open class SquareBoardImpl(override val width: Int) : SquareBoard {

    protected val cells: List<Cell> = (1..width).flatMap { i ->
        (1..width).map { j -> Cell(i, j) }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if (i !in 1..width || j < 1 || j > width) return null
        return cells[(i - 1) * width + (j - 1)]
    }

    override fun getCell(i: Int, j: Int): Cell {
        return getCellOrNull(i,j)?:throw IllegalArgumentException("Incorrect values")
    }

    override fun getAllCells(): Collection<Cell> {
        return cells
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        return jRange.mapNotNull { j -> getCellOrNull(i, j) }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        return iRange.mapNotNull { i -> getCellOrNull(i, j) }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            Direction.UP->getCellOrNull(i-1,j)
            Direction.DOWN->getCellOrNull(i+1,j)
            Direction.LEFT->getCellOrNull(i,j-1)
            Direction.RIGHT->getCellOrNull(i,j+1)
        }
    }

}

class GameBoardImpl(width: Int): SquareBoardImpl(width), GameBoard {

    private val cellValues=mutableMapOf<Cell,String?>()

    override fun get(cell: Cell): String? {
        return cellValues[cell]
    }

    override fun set(cell: Cell, value: String?) {
        cellValues[cell] = value
    }

    override fun filter(predicate: (String?) -> Boolean): Collection<Cell> {
        return cells.filter { cell -> predicate(get(cell)) }
    }

    override fun find(predicate: (String?) -> Boolean): Cell? {
        return cells.find { cell -> predicate(get(cell)) }
    }

    override fun any(predicate: (String?) -> Boolean): Boolean {
        return cells.any { cell -> predicate(get(cell)) }
    }

    override fun all(predicate: (String?) -> Boolean): Boolean {
        return cells.all { cell -> predicate(get(cell)) }
    }

}