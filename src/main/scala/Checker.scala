class Checker {
  def check(board: Board): Boolean = {
    if (board.queens.isEmpty) then true
    else checkQueens(board.queens)
  }

  def checkQueens(queens: Seq[Queen]): Boolean = {
    if(queens.isEmpty) return true
    val sameX = onlyOne(queens.groupBy(_.x))
    val sameY = onlyOne(queens.groupBy(_.y))

    val cartProduct = for {
      queen1 <- queens
      queen2 <- queens if (queen1 != queen2)
    } yield (queen1, queen2)

    val sameDiag =
      cartProduct.find(
        (q1, q2) => Math.abs(q1.x - q2.x) == Math.abs(q1.y - q2.y)
      )

    return sameX && sameY && sameDiag.isEmpty
  }

  private def onlyOne(queens: Map[Int, Seq[Queen]]): Boolean = {
    queens.map(_._2).forall(_.size <= 1)
  }

}
