package chess
package variant

case object KingOfTheHill extends Variant(
  id = 4,
  key = "kingOfTheHill",
  name = "King of the Hill",
  shortName = "KotH",
  title = "Bring your king to the center to win the game",
  standardInitialPosition = true) {

  private val center = Set(Pos.D4, Pos.D5, Pos.E4, Pos.E5)

  override def specialEnd(situation: Situation) =
    situation.board.kingPosOf(!situation.color) exists center.contains

  /**
   * You only need a king to be able to win in this variant
   */
  override def insufficientWinningMaterial(situation: Situation, color: Color) = false

  override def insufficientWinningMaterial(board: Board) = false
}

