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

  def nextBoard(board: Board, excluded: Seq[Board] = Seq()): Option[Board] = {

    val allPosition = for { y <- 0 to 7; x <- 0 to 7 } yield (x, y)

    val occupiedPosition = board.queens.map(queen => (queen.x, queen.y))

    val positionToCheck = allPosition
      .filter(pos => !occupiedPosition.contains(pos))
      .map((x, y) => Queen(x, y))


    positionToCheck
      .map(queen => Board(board.queens :+ Queen(queen._1, queen._2)))
      .filter(board => !excluded.contains(board))
      .find(board => checker.checkQueens(board.queens))
  }
}
