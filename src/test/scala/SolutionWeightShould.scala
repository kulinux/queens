import org.scalamock.scalatest.MockFactory
import org.scalatest.funsuite.AnyFunSuite

class SolutionWeightShould extends AnyFunSuite with MockFactory {
  val checker = mock[Checker]
  val solutionWeight = SolutionWeight(checker)

  test("should be 0 for an empty board") {
    val board = Board(Seq())

    assert(0 == solutionWeight.numberOfAttackingQueens(board))
  }

  test("should be 1 for an two queens in the same row") {
    val q1 = Queen(0, 2)
    val q2 = Queen(0, 6)

    (checker.checkQueens).expects(Seq(q1, q2)).returning(true).once()
    (checker.checkQueens).expects(Seq(q2, q1)).returning(true).once()

    val board = Board(
      Seq(
        q1,
        q2
      )
    )

    assert(2 == solutionWeight.numberOfAttackingQueens(board))
  }
}
