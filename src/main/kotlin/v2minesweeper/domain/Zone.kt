package v2minesweeper.domain

sealed interface Zone

object MineZone : Zone

object SafeZone : Zone
