import org.scalamock.scalatest.MockFactory
import org.scalatest.funsuite.AnyFunSuite
import cats.data.State
import org.scalatest.matchers.should.Matchers

case class Node(board: Board, parent: Option[Node], tryiedBoards: Seq[Board])

object StateUtilWithRollback {
  val checker = Checker()
  val nextQueenInBoard = NextQueenInBoard(checker)
  def nextNode(node: Node): Node = {
    val nextTryiedBoards = node.tryiedBoards :+ node.board
    nextQueenInBoard.nextBoard(node.board, node.tryiedBoards) match {
      case None => Node(node.parent.get.board, node.parent.get.parent, nextTryiedBoards)
      case Some(found) => Node(found, Some(node), nextTryiedBoards)
    }
  }
}

class WalkerWithRollbackShould extends AnyFunSuite with MockFactory with Matchers {
  val state: State[Node, Unit] =
    State(node => (StateUtilWithRollback.nextNode(node), ()))

  test("empty board") {
    val emptyBoard = Board.empty()
    val expectedBoard = Board(Seq(Queen(0, 0)))

    val initial = Node(emptyBoard, None, Seq())
    
    state.run(initial).value._1.board shouldBe expectedBoard
  }

  test("one queen") {
    val oneQueen = Board(Seq(Queen(0, 0)))
    val expectedBoard = Board(Seq(Queen(0, 0), Queen(2, 1)))

    val initial = Node(oneQueen, None, Seq())

    state.run(initial).value._1.board shouldBe expectedBoard
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

    val parentNode = Node(parentBoard, None, Seq())
    val initial = Node(initialBoard, Some(parentNode), Seq())

    state.run(initial).value._1.board shouldBe parentBoard
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

    val initial = Node(emptyBoard, None, Seq())

    newState.run(initial).value._1.board shouldBe expectedBoard
  }

  def stateLength(deep: Int) = {
    List.fill(deep)(()).foldLeft(state) (
      (acc, iter) => for {
        _ <- acc
        _ <- state
      } yield ()
    )

  }

  import scala.util.control.Breaks._

  //51877 8 List(Queen(0,0), Queen(4,1), Queen(7,2), Queen(5,3), Queen(2,4), Queen(6,5), Queen(1,6), Queen(3,7))
  test("solve") {
    val emptyBoard = Board.empty()
    val initial = Node(emptyBoard, None, Seq())

    var currentNode = initial

    breakable  {
      var i = 0
      while(true) {
        val nextNode = StateUtilWithRollback.nextNode(currentNode)

        if(i % 1000 == 0) {
          println(i + " " + nextNode.board.queens.size + " " + nextNode.board.queens)
        }
        if(nextNode.board.queens.size == 8) {
          println(i + " " + nextNode.board.queens.size + " " + nextNode.board.queens)
          println("FOUND ONE")
          break
        }
        currentNode = nextNode
        i = i + 1
      }
    }
  }

}

