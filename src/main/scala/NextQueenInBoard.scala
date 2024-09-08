class NextQueenInBoard(checker: Checker) {
  def next(board: Board, excluded: Seq[Queen] = Seq()): Option[Queen] = {

    val allPosition = for { y <- 0 to 7; x <- 0 to 7 } yield (x, y)

    val occupiedPosition = board.queens.map(queen => (queen.x, queen.y))
    val excludedPosition = excluded.map(queen => (queen.x, queen.y))

    val positionToCheck = allPosition
      .filter(pos => !occupiedPosition.contains(pos))
      .filter(pos => !excludedPosition.contains(pos))
      .map((x, y) => Queen(x, y))


    positionToCheck
      .find(queen => checker.checkQueens(board.queens :+ queen))

  }

  val allPosition: Seq[Tuple2[Int, Int]] = for { y <- 0 to 7; x <- 0 to 7 } yield (x, y)

  def nextBoard(board: Board, excluded: Seq[Board] = Seq()): Option[Board] = {

    val occupiedPosition = board.queens.map(queen => (queen.x, queen.y))

    val positionToCheck = allPosition
      .filter(pos => !occupiedPosition.contains(pos))

    positionToCheck
      .map((x, y) => Board(board.queens :+ Queen(x, y)))
      .filter(board => !excluded.contains(board))
      .find(board => checker.checkQueens(board.queens))
  }
}
