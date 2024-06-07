class CheckerShould extends munit.FunSuite {

  val checker = Checker()

  test("true over empty board") {
    val actual = check()

    assertEquals(actual, true)
  }

  test("true over one queen") {
    val actual = check(Queen(0, 0))

    assertEquals(actual, true)
  }

  test("fail over two queens in the same row") {
    val actual =
      check(
        Queen(0, 0),
        Queen(0, 5)
      )

    assertEquals(actual, false)
  }

  test("fail over two queens in the same column") {
    val actual =
      check(
        Queen(0, 0),
        Queen(5, 0)
      )

    assertEquals(actual, false)
  }

  test("fail over two queens in the same diagonal") {
    val actual =
      check(
        Queen(7, 6),
        Queen(6, 5)
      )

    assertEquals(actual, false)
  }

  def check(queens: Queen*): Boolean = {
    return checker.check(
      Board(
        queens
      )
    )
  }

}
