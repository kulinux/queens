import org.scalatest.funsuite.AnyFunSuite

class CheckerShould extends AnyFunSuite {

  val checker = Checker()

  test("true over empty board") {
    val actual = check()

    assert(actual == true)
  }

  test("true over one queen") {
    val actual = check(Queen(0, 0))

    assert(actual, true)
  }

  test("fail over two queens in the same row") {
    val actual =
      check(
        Queen(0, 0),
        Queen(0, 5)
      )

    assert(actual == false)
  }


  test("fail over two queens in the same column") {
    val actual =
      check(
        Queen(0, 0),
        Queen(5, 0)
      )

    assert(actual == false)
  }

  test("true over two queens in the different column, row, and diag") {
    val actual =
      check(
        Queen(0, 0),
        Queen(2, 1)
      )

    assert(actual == true)
  }

  test("fail over two queens in the same column with three elements") {
    val actual = check(
      Queen(0, 0),
      Queen(2, 1),
      Queen(1, 0)
    )

    assert(actual == false)
  }

  test("fail over two queens in the same diagonal") {
    val actual =
      check(
        Queen(7, 6),
        Queen(6, 5)
      )

    assert(actual == false)
  }

  test("fail over two queens in the same diagonal inverse") {
    val actual =
      check(
        Queen(7, 3),
        Queen(6, 4)
      )

    assert(actual == false)
  }

  def check(queens: Queen*): Boolean = {
    return checker.check(
      Board(
        queens
      )
    )
  }

}
