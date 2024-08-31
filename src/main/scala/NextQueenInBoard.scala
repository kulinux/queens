class NextQueenInBoard(checker: Checker) {
  def next(board: Board): Queen = {

    val allPosition = for { x <- 0 to 7; y <- 0 to 7 } yield (x, y)

    return allPosition
      .map((x, y) => Queen(x, y))
      .find(queen => checker.checkQueens(board.queens :+ queen))
      .getOrElse(???)

  }
}
