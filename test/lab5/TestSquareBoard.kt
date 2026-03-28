package lab5

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.MethodOrderer

@TestMethodOrder(MethodOrderer.MethodName::class)
class TestSquareBoard {
    @Test
    fun test00AllCells() {
        val board = createSquareBoard(2)
        val cells = board.getAllCells().sortedWith(compareBy<Cell> { it.i }.thenBy { it.j })
        Assertions.assertEquals("[(1, 1), (1, 2), (2, 1), (2, 2)]", cells.toString())
    }

    @Test
    fun test01Cell() {
        val board = createSquareBoard(2)
        val cell = board.getCellOrNull(1, 2)
        Assertions.assertEquals(1, cell?.i)
        Assertions.assertEquals(2, cell?.j)
    }

    @Test
    fun test02NoCell() {
        val board = createSquareBoard(2)
        val cell = board.getCellOrNull(3, 3)
        Assertions.assertEquals(null, cell)
    }

    @Test
    fun test03Row() {
        val board = createSquareBoard(2)
        val row = board.getRow(1, 1..2)
        Assertions.assertEquals("[(1, 1), (1, 2)]", row.toString())
    }

    @Test
    fun test04RowReversed() {
        val board = createSquareBoard(2)
        val row = board.getRow(1, 2 downTo 1)
        Assertions.assertEquals("[(1, 2), (1, 1)]", row.toString())
    }

    @Test
    fun test05RowWrongRange() {
        val board = createSquareBoard(2)
        val row = board.getRow(1, 1..10)
        Assertions.assertEquals("[(1, 1), (1, 2)]", row.toString())
    }

    @Test
    fun test06Neighbour() {
        val board = createSquareBoard(2)
        with(board) {
            val cell = getCellOrNull(1, 1)
            Assertions.assertNotNull(cell)
            Assertions.assertEquals(null, cell!!.getNeighbour(Direction.UP))
            Assertions.assertEquals(null, cell.getNeighbour(Direction.LEFT))
            Assertions.assertEquals("(2, 1)", cell.getNeighbour(Direction.DOWN).toString())
            Assertions.assertEquals("(1, 2)", cell.getNeighbour(Direction.RIGHT).toString())
        }
    }

    @Test
    fun test07AllCells() {
        val board = createSquareBoard(3)
        val cells = board.getAllCells().sortedWith(compareBy<Cell> { it.i }.thenBy { it.j })
        Assertions.assertEquals(
            "[(1, 1), (1, 2), (1, 3), (2, 1), (2, 2), (2, 3), (3, 1), (3, 2), (3, 3)]",
            cells.toString(),
            "Wrong result for 'getAllCells()' for the board of width 3.")
    }

    @Test
    fun test08Cell() {
        val board = createSquareBoard(4)
        val cell = board.getCellOrNull(2, 3)
        Assertions.assertEquals(
            "(2, 3)", cell.toString(),
            "The board of width 4 should contain the cell (2, 3).")
    }

    @Test
    fun test09NoCell() {
        val board = createSquareBoard(4)
        val cell = board.getCellOrNull(10, 10)
        Assertions.assertEquals(
            null, cell,
            "The board of width 4 should contain the cell (10, 10).")
    }

    @Test
    fun test10Row() {
        val row = createSquareBoard(4).getRow(1, 1..2)
        Assertions.assertEquals(
            "[(1, 1), (1, 2)]", row.toString(),
            "Wrong row for 'createSquareBoard(4).getRow(1, 1..2)'.")
    }

    @Test
    fun test11Column() {
        val row = createSquareBoard(4).getColumn(1..2, 3)
        Assertions.assertEquals(
            "[(1, 3), (2, 3)]", row.toString(),
            "Wrong column for 'createSquareBoard(4).getColumn(1..2, 3)'.")
    }

    @Test
    fun test12RowReversedRange() {
        val row = createSquareBoard(4).getRow(1, 4 downTo 1)
        Assertions.assertEquals(
            "[(1, 4), (1, 3), (1, 2), (1, 1)]", row.toString(),
            "Wrong column for 'createSquareBoard(4).getRow(1, 4 downTo 1)'.")
    }

    @Test
    fun test13ColumnReversedRange() {
        val row = createSquareBoard(4).getColumn(2 downTo 1, 3)
        Assertions.assertEquals(
            "[(2, 3), (1, 3)]", row.toString(),
            "Wrong column for 'createSquareBoard(4).getColumn(2 downTo 1, 3)'.")
    }

    @Test
    fun test14ColumnWrongRange() {
        val row = createSquareBoard(4).getColumn(3..6, 2)
        Assertions.assertEquals(
            "[(3, 2), (4, 2)]", row.toString(),
            "Wrong column for 'createSquareBoard(4).getColumn(3..6, 2)'.")
    }

    private fun neighbourMessage(cell: Cell, direction: Direction) =
        "Wrong neighbour for the cell $cell in a direction $direction."

    @Test
    fun test15Neighbour() {
        with(createSquareBoard(4)) {
            val cell = getCellOrNull(2, 3)
            Assertions.assertNotNull(cell, "The board of width 4 should contain the cell (2, 3).")
            Assertions.assertEquals("(1, 3)", cell!!.getNeighbour(Direction.UP).toString(), neighbourMessage(cell, Direction.UP))
            Assertions.assertEquals("(3, 3)", cell.getNeighbour(Direction.DOWN).toString(), neighbourMessage(cell, Direction.DOWN))
            Assertions.assertEquals("(2, 2)", cell.getNeighbour(Direction.LEFT).toString(), neighbourMessage(cell, Direction.LEFT))
            Assertions.assertEquals("(2, 4)", cell.getNeighbour(Direction.RIGHT).toString(), neighbourMessage(cell, Direction.RIGHT))
        }
    }

    @Test
    fun test16NullableNeighbour() {
        with(createSquareBoard(4)) {
            val cell = getCellOrNull(4, 4)
            Assertions.assertNotNull(cell, "The board of width 4 should contain the cell (4, 4).")
            Assertions.assertEquals("(3, 4)", cell!!.getNeighbour(Direction.UP).toString(), neighbourMessage(cell, Direction.UP))
            Assertions.assertEquals("(4, 3)", cell.getNeighbour(Direction.LEFT).toString(), neighbourMessage(cell, Direction.LEFT))
            Assertions.assertEquals(null, cell.getNeighbour(Direction.DOWN), neighbourMessage(cell, Direction.DOWN))
            Assertions.assertEquals(null, cell.getNeighbour(Direction.RIGHT), neighbourMessage(cell, Direction.RIGHT))
        }
    }

    @Test
    fun test17TheSameCell() {
        val board = createSquareBoard(4)
        val first = board.getCell(1, 2)
        val second = board.getCellOrNull(1, 2)
        Assertions.assertTrue(
            first === second,
            "'getCell' and 'getCellOrNull' should return the same 'Cell' instances.\n" +
                    "Create only 'width * width' cells; all the functions working with cells " +
                    "should return existing cells instead of creating new ones.")
    }

    @Test
    fun test18TheSameCell() {
        val board = createSquareBoard(1)
        val first = board.getAllCells().first()
        val second = board.getCell(1, 1)
        Assertions.assertTrue(
            first === second,
            "'getAllCells' and 'getCell' should return the same 'Cell' instances.\n" +
                    "Create only 'width * width' cells; all the functions working with cells " +
                    "should return existing cells instead of creating new ones.")
    }

    @Test
    fun test19TheSameCell() {
        val board = createSquareBoard(4)
        val cell = board.getCell(1, 1)
        val first = board.run { cell.getNeighbour(Direction.RIGHT) }
        val second = board.getCell(1, 2)
        Assertions.assertTrue(
            first === second,
            "'getNeighbour' shouldn't recreate the 'Cell' instance.\n" +
                    "Create only 'width * width' cells; all the functions working with cells " +
                    "should return existing cells instead of creating new ones.")
    }

    @Test
    fun test20TheSameCell() {
        val board = createSquareBoard(2)
        val row = board.getRow(1, 1..1)
        val first = row[0]
        val second = board.getCell(1, 1)
        Assertions.assertTrue(
            first === second,
            "'getRow' shouldn't recreate the 'Cell' instances.\n" +
                    "Create only 'width * width' cells; all the functions working with cells " +
                    "should return existing cells instead of creating new ones.")
    }

    @Test
    fun test21TheSameCell() {
        val board = createSquareBoard(2)
        val column = board.getColumn(1..1, 2)
        val first = column[0]
        val second = board.getCell(1, 2)
        Assertions.assertTrue(
            first === second,
            "'getColumn' shouldn't recreate the 'Cell' instances.\n" +
                    "Create only 'width * width' cells; all the functions working with cells " +
                    "should return existing cells instead of creating new ones.")
    }
}
