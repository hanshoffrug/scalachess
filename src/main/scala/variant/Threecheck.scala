package chess
package variant

case object ThreeCheck extends Variant(
  id = 5,
  key = "threeCheck",
  name = "Three-check",
  shortName = "3check",
  title = "Check your opponent 3 times to win the game",
  standardInitialPosition = true) {

  override def finalizeBoard(board: Board): Board = board updateHistory {
      _.withCheck(Color.White, board.checkWhite).withCheck(Color.Black, board.checkBlack)
  }

  override def specialEnd(situation: Situation) = situation.check && {
    val checks = situation.board.history.checkCount
    situation.color.fold(checks.white, checks.black) >= 3
  }

  /**
   * It's not possible to check or checkmate the opponent with only a king
   */
  override def insufficientWinningMaterial(situation: Situation, color: Color) =
    situation.board.rolesOf(color) == List(King)

  // When there is insufficient mating material, there is still potential to win by checking the opponent 3 times
  // by the variant ending. However, no players can check if there are only kings remaining
  override def insufficientWinningMaterial(board: Board) = board.actors.forall(_._2.piece is King)
}
