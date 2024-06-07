case class Queen(x: Int, y: Int)

case class Board(queens: Seq[Queen])

class Checker {
  def check(board: Board): Boolean = {
    if (board.queens.isEmpty) return true
    val sameRow = board.queens.groupBy(_.x).map(_._2).exists(_.size <= 1)
    val sameColumn = board.queens.groupBy(_.y).map(_._2).exists(_.size <= 1)
    val sameDiag =
      board.queens.groupBy(q => q.x - q.y).map(_._2).exists(_.size <= 1)
    return sameColumn && sameRow && sameDiag
  }
}

@main def hello(): Unit =
  println("Hello world!")
  println(msg)

def msg = "I was compiled by Scala 3. :)"
