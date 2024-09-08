import org.scalamock.scalatest.MockFactory
import org.scalatest.funsuite.AnyFunSuite
import cats.data.State
import org.scalatest.matchers.should.Matchers

object StateUtil {
  val checker = Checker()
  val nextQueenInBoard = NextQueenInBoard(checker)
  def freePosition(board: Board): Queen = nextQueenInBoard.next(board).get
}

class WalkerShould extends AnyFunSuite with MockFactory with Matchers {
  val state: State[Board, Unit] =
    State(board => (board.add(StateUtil.freePosition(board)), ()))

  test("empty board") {
    val emptyBoard = Board.empty()
    val expected = Board(Seq(Queen(0, 0)))

    state.run(emptyBoard).value._1 shouldBe expected
  }

  test("one queen") {
    val oneQueen = Board(Seq(Queen(0, 0)))
    val expected = Board(Seq(Queen(0, 0), Queen(2, 1)))

    state.run(oneQueen).value._1 shouldBe expected
  }

  test("two queen") {
    val twoQueen = Board(Seq(Queen(0, 0), Queen(2, 1)))
    val expected = Board(Seq(Queen(0, 0), Queen(2, 1), Queen(4, 2)))

    state.run(twoQueen).value._1 shouldBe expected
  }
  test("composing state") {
    val newState = for {
      _ <- state
      _ <- state
    } yield ()
    
    val emptyBoard = Board.empty()
    val expected = Board(Seq(Queen(0, 0), Queen(2, 1)))

    newState.run(emptyBoard).value._1 shouldBe expected
  }

}

