class Checker {
  def check(board: Board): Boolean = {
    if (board.queens.isEmpty) return true
    return checkQueens(board.queens)
  }

  def checkQueens(queens: Seq[Queen]): Boolean = {
    val sameRow = queens.groupBy(_.x).map(_._2).exists(_.size <= 1)
    val sameColumn = queens.groupBy(_.y).map(_._2).exists(_.size <= 1)
    val sameDiag =
      queens.groupBy(q => q.x - q.y).map(_._2).exists(_.size <= 1)
    return sameColumn && sameRow && sameDiag
  }

}
