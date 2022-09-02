package v2minesweeper.domain

class MineSweeperBoardFactory(
    val height: Height,
    val width: Width,
    val mineNumber: MineNumber,
    val mineSweeperBoardGenerator: (height: Height, width: Width, mineNumber: MineNumber) -> MineSweeperBoard
) {
    init {
        require((height.value * width.value) >= mineNumber.value) { "총 넓이보다 지뢰 갯수가 클 수 없습니다." }
    }

    fun operate(): MineSweeperBoard = mineSweeperBoardGenerator(
        height,
        width,
        mineNumber
    )
}

private const val INIT_POSITION_NUMBER = 1

fun createRandomMineSweeperBoard(height: Height, width: Width, mineNumber: MineNumber): MineSweeperBoard {
    val totalSize = (height.value * width.value)
    val mineZones = List(mineNumber.value) { MineZone() }
    val safeZones = List(totalSize - mineNumber.value) { SafeZone() }

    return (mineZones + safeZones).shuffled()
        .chunked(width.value)
        .flatMapIndexed { x, zones ->
            zones.mapIndexed { y, zone -> (x + INIT_POSITION_NUMBER to y + INIT_POSITION_NUMBER) to zone }
        }
        .toMap()
        .let { MineSweeperBoard(it.toZones()) }
}
