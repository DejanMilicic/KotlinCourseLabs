package lab5

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.MethodOrderer

@TestMethodOrder(MethodOrderer.MethodName::class)
class TestGameBoard {
    operator fun GameBoard.get(i: Int, j: Int) = get(getCell(i, j))
    operator fun GameBoard.set(i: Int, j: Int, value: String?) = set(getCell(i, j), value)

    @Test
    fun test01GetAndSetElement() {
        val gameBoard = createGameBoard(2)
        gameBoard[1, 1] = "a"
        Assertions.assertEquals("a", gameBoard[1, 1])
    }

    @Test
    fun test02Filter() {
        val gameBoard = createGameBoard(2)
        gameBoard[1, 1] = "a"
        gameBoard[1, 2] = "b"
        val cells = gameBoard.filter { it == "a" }
        Assertions.assertEquals(1, cells.size)
        val cell = cells.first()
        Assertions.assertEquals(1, cell.i)
        Assertions.assertEquals(1, cell.j)
    }

    @Test
    fun test03All() {
        val gameBoard = createGameBoard(2)
        gameBoard[1, 1] = "a"
        gameBoard[1, 2] = "a"
        Assertions.assertFalse(gameBoard.all { it == "a" })
        gameBoard[2, 1] = "a"
        gameBoard[2, 2] = "a"
        Assertions.assertTrue(gameBoard.all { it == "a" })
    }

    @Test
    fun test04Any() {
        val gameBoard = createGameBoard(2)
        gameBoard[1, 1] = "a"
        gameBoard[1, 2] = "b"
        Assertions.assertTrue(gameBoard.any { it?.toCharArray()?.firstOrNull() in 'a'..'b' })
        Assertions.assertTrue(gameBoard.any { it == null })
    }

    @Test
    fun test05TheSameCell() {
        val gameBoard = createGameBoard(2)
        gameBoard[1, 1] = "a"
        val cell1 = gameBoard.find { it == "a" }
        gameBoard[1, 1] = "b"
        val cell2 = gameBoard.find { it == "b" }
        Assertions.assertEquals(cell1, cell2)
    }

    @Test
    fun test06FindReturnsTheSameCell() {
        val gameBoard = createGameBoard(2)
        gameBoard[1, 1] = "a"
        val first = gameBoard.find { it == "a" }
        val second = gameBoard.getCell(1, 1)
        Assertions.assertTrue(
            first === second,
            "find shouldn't recreate the 'Cell' instances.\n" +
                    "Create only 'width * width' cells; all the functions working with cells\n" +
                    "should return existing cells instead of creating new ones."
        )
    }

    @Test
    fun test07FilterTheSameCell() {
        val gameBoard = createGameBoard(2)
        gameBoard[1, 1] = "a"
        val cells = gameBoard.filter { it == "a" }
        val first = cells.first()
        val second = gameBoard.getCell(1, 1)
        Assertions.assertTrue(
            first === second,
            "'filter' shouldn't recreate the 'Cell' instances.\n" +
                    "Create only 'width * width' cells; all the functions working with cells\n" +
                    "should return existing cells instead of creating new ones."
        )
    }
}
