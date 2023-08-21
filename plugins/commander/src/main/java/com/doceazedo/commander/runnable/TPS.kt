package com.doceazedo.commander.runnable

import java.math.RoundingMode
import java.text.DecimalFormat

object TPS : Runnable {
    private var tickCount = 0
    private var ticksHistory = LongArray(600)

    override fun run() {
        ticksHistory[tickCount % ticksHistory.size] = System.currentTimeMillis()
        tickCount += 1
    }

    fun getTPS(ticks: Int = 100): Double {
        if (tickCount < ticks) return 20.0
        val target = (tickCount - 1 - ticks) % ticksHistory.size
        val elapsed = System.currentTimeMillis() - ticksHistory[target]
        val tps = ticks / (elapsed / 1000.0)

        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.UP
        val tpsRounded = df.format(tps).toDouble()

        if (tpsRounded > 20) return 20.0
        return tpsRounded
    }
}