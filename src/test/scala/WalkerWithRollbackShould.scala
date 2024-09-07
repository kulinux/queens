import org.scalamock.scalatest.MockFactory
import org.scalatest.funsuite.AnyFunSuite
import cats.data.State
import org.scalatest.matchers.should.Matchers

case class Node(board: Board, parent: Option[Node])

object StateUtilWithRollback {
  val checker = Checker()
  val nextQueenInBoard = NextQueenInBoard(checker)
  def nextNode(node: Node): Node = {
    println(s"next node ${node}")
    nextQueenInBoard.next(node.board) match {
      case None => Node(node.parent.get.board, node.parent.get.parent)
      case Some(found) => Node(node.board.add(found), Some(node))
    }
  }
}

class WalkerWithRollbackShould extends AnyFunSuite with MockFactory with Matchers {
  val state: State[Node, Unit] =
    State(node => (StateUtilWithRollback.nextNode(node), ()))

  test("empty board") {
    val emptyBoard = Board.empty()
    val expectedBoard = Board(Seq(Queen(0, 0)))

    val initial = Node(emptyBoard, None)
    val expected = Node(expectedBoard, None)
    

    state.run(initial).value._1 shouldBe expected
  }

  test("one queen") {
    val oneQueen = Board(Seq(Queen(0, 0)))
    val expectedBoard = Board(Seq(Queen(0, 0), Queen(2, 1)))

    val initial = Node(oneQueen, None)
    val expected = Node(expectedBoard, None)

    state.run(initial).value._1 shouldBe expected
  }

  test("rollback") {
    val initialBoard = Board(Seq(
      Queen(0, 0), 
      Queen(2, 1), 
      Queen(1, 2), 
      Queen(5, 3), 
      Queen(7, 4), 
      Queen(3, 5), 
      Queen(4, 7)
    ))
    val parentBoard = Board(Seq(
      Queen(0, 0), 
      Queen(2, 1), 
      Queen(1, 2), 
      Queen(5, 3), 
      Queen(7, 4), 
      Queen(3, 5), 
    ))

    val expectedBoard = Board(Seq(Queen(0, 0), Queen(2, 1)))

    val parentNode = Node(parentBoard, None)
    val initial = Node(initialBoard, Some(parentNode))
    val expected = parentNode

    state.run(initial).value._1 shouldBe expected
  }

  test("composing state for eight") {
    val newState = for {
      _ <- state
      _ <- state
      _ <- state
      _ <- state
      _ <- state
      _ <- state
      _ <- state
      _ <- state
    } yield ()

    val emptyBoard = Board.empty()
    val expectedBoard = 
       Board(
         List(
          Queen(0, 0), 
          Queen(2, 1), 
          Queen(1, 2), 
          Queen(5, 3), 
          Queen(7, 4), 
          Queen(3, 5), 
        )
      )
    val expectedParent =
      Some(Node(Board(List(Queen(0, 0), Queen(2, 1), Queen(1, 2), Queen(5, 3), Queen(7, 4))),
        Some(Node(Board(List(Queen(0, 0), Queen(2, 1), Queen(1, 2), Queen(5, 3))),
          Some(Node(Board(List(Queen(0, 0), Queen(2, 1), Queen(1, 2))),
            Some(Node(Board(List(Queen(0, 0), Queen(2, 1))),
              Some(Node(Board(List(Queen(0, 0))),
                Some(Node(Board(List()), None))
              ))
            ))
          ))
        ))
    ))

    val initial = Node(emptyBoard, None)
    val expected = Node(expectedBoard, expectedParent)

    newState.run(initial).value._1 shouldBe expected
  }
}

