package lab5


fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)


fun createGameBoard(width: Int): GameBoard = GameBoardImpl(width)


open class SquareBoardImpl(override val width: Int) : SquareBoard {
    val cells = mutableListOf<Cell>()

    init {
        for (x in 1..width) {
            for (y in 1..width) {
                cells.add(Cell(x, y))
            }
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if (i !in 1..width || j !in 1..width) return null
        return cells[(i - 1) * width + (j - 1)]
    }

    override fun getCell(i: Int, j: Int): Cell {
        if (i !in 1..width || j !in 1..width) throw IllegalArgumentException("i and j must be between 1 and the ${width}")
        return cells[(i - 1) * width + (j - 1)]
    }

    override fun getAllCells(): Collection<Cell> {
        return cells
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        if (i in 1..width) {
            val result = mutableListOf<Cell>()
            for (j in jRange) {
                if (j in 1..width) {
                    result.add(cells[(i - 1) * width + (j - 1)])
                }
            }
            return result
        }
        return emptyList()
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        if (j in 1..width) {
            val result = mutableListOf<Cell>()
            for (i in iRange) {
                if (i in 1..width) {
                    result.add(cells[(i - 1) * width + (j - 1)])
                }
            }
            return result
        }
        return emptyList()
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        when (direction) {
            Direction.UP -> return if (this.i > 1) cells[(this.i - 2) * width + (this.j - 1)] else null
            Direction.DOWN -> return if (this.i < width) cells[this.i * width + (this.j - 1)] else null
            Direction.LEFT -> return if (this.j > 1) cells[(this.i - 1) * width + (this.j - 2)] else null
            Direction.RIGHT -> return if (this.j < width) cells[(this.i - 1) * width + this.j] else null
        }
    }

}


class GameBoardImpl(size: Int) : SquareBoardImpl(size), GameBoard {
    val values = mutableMapOf<Cell, String?>()

    init {
        for (i in 0 until 2 * width) {
            values[cells[i]] = null
        }
    }

    override fun get(cell: Cell): String? {
        return values[cell]
    }

    override fun set(cell: Cell, value: String?) {
        values[cell] = value
    }

    override fun filter(predicate: (String?) -> Boolean): Collection<Cell> {
        return values.filter { predicate(it.value) }.keys
    }

    override fun find(predicate: (String?) -> Boolean): Cell? {
        return values.entries.firstOrNull { predicate(it.value) }?.key
    }

    override fun any(predicate: (String?) -> Boolean): Boolean {
        return values.any { predicate(it.value) }
    }

    override fun all(predicate: (String?) -> Boolean): Boolean {
        return values.all { predicate(it.value) }
    }

}