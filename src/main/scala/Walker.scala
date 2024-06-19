import cats.data.State

def nextNode(node: Node): Node = ???
def isSolved(node: Node): Boolean = ???

val nextLong: State[Node, Boolean] =
  State(node => (nextNode(node), isSolved(node)))
