class Checker {
  def check(board: Board): Boolean = {
    if (board.queens.isEmpty) then true
    else checkQueens(board.queens)
  }

  def checkQueens(queens: Seq[Queen]): Boolean = {
    val sameX = onlyOne(queens.groupBy(_.x))
    val sameY = onlyOne(queens.groupBy(_.y))
    val sameDiag = onlyOne(queens.groupBy(q => q.x - q.y))
    sameX && sameY && sameDiag
  }

  private def onlyOne(queens: Map[Int, Seq[Queen]]): Boolean = {
    queens.map(_._2).forall(_.size <= 1)
  }

}
