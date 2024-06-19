class SolutionWeight(val checker: Checker) {
  def numberOfAttackingQueens(board: Board): Int = {
    val cartesianProduct: Seq[Tuple2[Queen, Queen]] = for {
      q1 <- board.queens
      q2 <- board.queens
    } yield (q1, q2)
    cartesianProduct
      .filter(queens => queens._1 != queens._2)
      .filter(queens => checker.checkQueens(Seq(queens._1, queens._2)))
      .size
  }
}
