package lab5

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)

fun createGameBoard(width: Int): GameBoard = GameBoardImpl(width)

open class SquareBoardImpl(size: Int) : SquareBoard {

    override val width = size
    private val cells = Array(width) { Array(width) { Cell(0, 0) } }

    init {
        for (i in 0..<width) {
            for (j in 0..<width) {
                cells[i][j] = Cell(i + 1, j + 1)
            }
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return if (i in 1..width && j in 1..width) cells[i - 1][j - 1] else null
    }

    override fun getCell(i: Int, j: Int): Cell {
        return getCellOrNull(i, j) ?: throw IllegalArgumentException("Values out i and j out of range.")
    }

    override fun getAllCells(): Collection<Cell> {
        return cells.flatten()
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val row = mutableListOf<Cell>()
        for (j in jRange) {
            getCellOrNull(i, j)?.let { row.add(it) }
        }
        return row
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val column = mutableListOf<Cell>()
        for (i in iRange) {
            getCellOrNull(i, j)?.let { column.add(it) }
        }
        return column
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

    val info = hashMapOf<Cell, String>()

    override fun get(cell: Cell): String? {
        return info[cell]
    }

    override fun set(cell: Cell, value: String?) {
        if (value == null) {
            info.remove(cell)
        } else {
            info[cell] = value
        }
    }

    override fun filter(predicate: (String?) -> Boolean): Collection<Cell> {
        return getAllCells().filter { predicate(info[it]) }
    }

    override fun find(predicate: (String?) -> Boolean): Cell? {
        return getAllCells().firstOrNull { predicate(info[it]) }
    }

    override fun any(predicate: (String?) -> Boolean): Boolean {
        return find(predicate) != null
    }

    override fun all(predicate: (String?) -> Boolean): Boolean {
        return find { !predicate(it) } == null
    }
}