case class Queen(x: Int, y: Int)

case class Board(queens: Seq[Queen]) {
  def add(queen: Queen) = Board(queens :+ queen)
}

object Board {
  def empty() = Board(Seq())
}
