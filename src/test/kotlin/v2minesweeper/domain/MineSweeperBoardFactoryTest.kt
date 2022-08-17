package v2minesweeper.domain

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs

class MineSweeperBoardFactoryTest : FunSpec({
    test("총 zone 갯수(높이 x 너비)보다 지뢰 갯수가 더 많으면 예외를 발생시킨다.") {
        // given
        val invalidMineNumber = 10
        val height = 3
        val width = 3

        // when then
        shouldThrowExactly<IllegalArgumentException> {
            MineSweeperBoardFactory(
                height = Height(height),
                width = Width(width),
                mineNumber = MineNumber(invalidMineNumber)
            ) { _, _, _ ->
                MineSweeperBoard(emptyMap())
            }
        }
    }

    test("지정한 높이, 너비, 지뢰 갯수에 맞게 지뢰판을 생성한다.") {
        // given
        val height = 2
        val width = 2
        val mineNumber = 2
        val mineSweeperBoardFactory = MineSweeperBoardFactory(
            height = Height(height),
            width = Width(width),
            mineNumber = MineNumber(mineNumber)
        ) { height, width, mineNumber ->
            MineSweeperBoard(
                mapOf(
                    (1 to 1) to MineZone,
                    (1 to 2) to SafeZone,
                    (2 to 1) to SafeZone,
                    (2 to 2) to MineZone
                )
            )
        }

        // when
        val mineSweeperBoard = mineSweeperBoardFactory.operate()

        // then
        assertSoftly(mineSweeperBoard) {
            zones.containsKey(1 to 1) shouldBe true
            zones[1 to 1] shouldBeSameInstanceAs MineZone
            zones.containsKey(1 to 2) shouldBe true
            zones[1 to 2] shouldBeSameInstanceAs SafeZone
            zones.containsKey(2 to 1) shouldBe true
            zones[2 to 1] shouldBeSameInstanceAs SafeZone
            zones.containsKey(2 to 2) shouldBe true
            zones[2 to 2] shouldBeSameInstanceAs MineZone
        }
    }
})
