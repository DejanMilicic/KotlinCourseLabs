package lab5

import javax.management.Query.value


fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)

open class SquareBoardImpl(override val width: Int) : SquareBoard {

	val cells: Array<Array<Cell>> = Array(width) { i ->
		Array(width) { j ->
			Cell(i + 1, j + 1)
		}
	}

	override fun getCellOrNull(i: Int, j: Int): Cell? {
		if(i in 1.. width && j in 1.. width) {
			return cells[i-1][j-1]
		}
		else
			return null
	}

	override fun getCell(i: Int, j: Int): Cell {
		if(i in 1.. width && j in 1.. width) {
			return cells[i-1][j-1]
		}
		else
			throw IllegalArgumentException("Indicies of i and j must be between 1 and ${width}")
	}

	override fun getAllCells(): Collection<Cell> {
		return cells.flatten()
	}

	override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
		return jRange.filter { it in 1..width }.map { j -> cells[i-1][j-1] }
	}

	override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
		return iRange.filter { it in 1.. width}.map { i -> cells[i-1][j-1] }
	}

	override fun Cell.getNeighbour(direction: Direction): Cell? {
		return when(direction) {
			Direction.UP -> getCellOrNull(i-1,j)
			Direction.DOWN -> getCellOrNull(i+1,j)
			Direction.LEFT -> getCellOrNull(i,j-1)
			Direction.RIGHT -> getCellOrNull(i,j+1)
		}
	}

}


fun createGameBoard(width: Int): GameBoard = GameBoardImpl(width)

class GameBoardImpl(width: Int): SquareBoardImpl(width) , GameBoard {

	val values=mutableMapOf<Cell, String?>()

	override fun get(cell: Cell): String? {
		return values[cell]
	}

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