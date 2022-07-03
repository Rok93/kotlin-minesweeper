package minesweeper.domain

fun randomGenerateStrategy(width: Int, height: Int, countOfMine: Int): Map<Position, Zone> {
    val zoneArea = height * width
    val safeZones = List(zoneArea - countOfMine) { SafeZone }
    val mineZones = List(countOfMine) { MineZone }
    return (mineZones + safeZones).shuffled()
        .chunked(width)
        .flatMapIndexed { x, zones ->
            zones.mapIndexed { y, zone -> Position(x + 1, y + 1) to zone }
        }
        .toMap()
}
