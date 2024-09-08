import org.scalamock.scalatest.MockFactory
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class NextQueenInBoardShould
    extends AnyFunSuite
    with MockFactory
    with Matchers {

  val checker: Checker = mock[Checker]
  val nextQueenInBoard = NextQueenInBoard(checker)

  test("empty board should go for 0, 0") {
    val empty = Board.empty()

    val expected = Queen(0, 0)
    (checker.checkQueens).expects(Seq(expected)).returning(true)

    nextQueenInBoard.next(empty).get shouldBe expected
  }

  test("one queen should respect order") {
    val initialQueen = Queen(0, 0)
    val oneQueenBoard = Board(Seq(initialQueen))
    val expected = Queen(1, 0)

    (checker.checkQueens).expects(*).returning(true).anyNumberOfTimes()

    nextQueenInBoard.next(oneQueenBoard).get shouldBe expected
  }

  test("one queen should find the next free valid slot") {
    val initialQueen = Queen(0, 0)
    val oneQueenBoard = Board(Seq(initialQueen))
    val expected = Queen(0, 2)

    (checker.checkQueens).expects(Seq(initialQueen, expected)).returning(true)
    (checker.checkQueens).expects(*).returning(false).anyNumberOfTimes()

    nextQueenInBoard.next(oneQueenBoard).get shouldBe expected
  }

  test("should skip occupied positions") {
    val initialQueen = Queen(0, 0)
    val oneQueenBoard = Board(Seq(initialQueen))
    val expected = Queen(2, 1)

    (checker.checkQueens).expects(Seq(initialQueen, initialQueen))
      .returning(true)
      .anyNumberOfTimes()
    (checker.checkQueens).expects(Seq(initialQueen, expected))
      .returning(true)
    (checker.checkQueens).expects(*).returning(false).anyNumberOfTimes()

    nextQueenInBoard.next(oneQueenBoard).get shouldBe expected
  }

  test("next position is not always possible") {
    val initialQueen = Queen(0, 0)
    val oneQueenBoard = Board(Seq(initialQueen))

    (checker.checkQueens).expects(*).returning(false).anyNumberOfTimes()

    nextQueenInBoard.next(oneQueenBoard) shouldBe empty
  }

  test("empty board with (0, 0) excluded should go for 0, 1") {
    val empty = Board.empty()

    val expected = Queen(1, 0)
    (checker.checkQueens).expects(Seq(expected)).returning(true)

    nextQueenInBoard.next(empty, Seq(Queen(0, 0))).get shouldBe expected
  }

  test("empty board with (0, 0) excluded board should go for 0, 1") {
    val empty = Board.empty()

    val expected = Board(Seq(Queen(1, 0)))
    val excludedBoards = Seq(Board(Seq(Queen(0, 0))))
    (checker.checkQueens).expects(expected.queens).returning(true)

    nextQueenInBoard.nextBoard(empty, excludedBoards).get shouldBe expected
  }
}
