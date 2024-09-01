class NextQueenInBoard(checker: Checker) {
  def next(board: Board): Option[Queen] = {

    val allPosition = for { y <- 0 to 7; x <- 0 to 7 } yield (x, y)

    val occupiedPosition = board.queens.map(queen => (queen.x, queen.y))


    val positionToCheck = allPosition
      .filter(pos => !occupiedPosition.contains(pos))
      .map((x, y) => Queen(x, y))


    positionToCheck
      .find(queen => checker.checkQueens(board.queens :+ queen))

  }
}
