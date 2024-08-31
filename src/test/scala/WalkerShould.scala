import org.scalamock.scalatest.MockFactory
import org.scalatest.funsuite.AnyFunSuite
import cats.data.State

object StateUtil {
  def freePosition(board: Board): Queen = ???
}

class WalkerShould extends AnyFunSuite with MockFactory {
  test("foo") {
    val state: State[Board, Unit] =
      State(board => (board.add(StateUtil.freePosition(board)), ()))

    val emptyBoard = Board.empty()

  }
}
