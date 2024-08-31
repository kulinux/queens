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

  test("one queen should find the next free valid slot") {
    val initialQueen = Queen(0, 0)
    val oneQueenBoard = Board(Seq(initialQueen))
    val expected = Queen(2, 1)

    (checker.checkQueens).expects(Seq(initialQueen, expected)).returning(true)
    (checker.checkQueens).expects(*).returning(false).anyNumberOfTimes()

    nextQueenInBoard.next(oneQueenBoard).get shouldBe expected
  }

  test("next position is not always possible") {
    val initialQueen = Queen(0, 0)
    val oneQueenBoard = Board(Seq(initialQueen))

    (checker.checkQueens).expects(*).returning(false).anyNumberOfTimes()

    nextQueenInBoard.next(oneQueenBoard) shouldBe empty
  }
}
