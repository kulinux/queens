case class Queen(x: Int, y: Int)

case class Board(queens: Seq[Queen])

case class Node(parent: Node, children: Seq[Node], board: Board)
