package com.gorbkostya.lasttime

import com.gorbkostya.lasttime.domain.StatusCalculator
import com.gorbkostya.lasttime.domain.model.Category
import com.gorbkostya.lasttime.domain.model.Task
import com.gorbkostya.lasttime.domain.model.TaskStatus
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.LocalDate

class StatusCalculatorTest {

    private val today = LocalDate.of(2026, 10, 5)

    private fun task(daysAgo: Long, interval: Int?) = Task(
        id = 1, title = "Тест", category = Category.HOME,
        intervalDays = interval, lastDone = today.minusDays(daysAgo), remind = false
    )

    @Test
    fun `97 дней из 90 — просрочено`() {
        val result = StatusCalculator.status(task(97, 90), today)
        assertEquals(TaskStatus.LATE, result)
    }


    @Test
    fun `30 дней из 90 — в порядке`() {
        val result = StatusCalculator.status(task(30, 90), today)
        assertEquals(TaskStatus.OK, result)
    }

    @Test
    fun `80 дней из 90 — скоро`() {
        val result = StatusCalculator.status(task(80, 90), today)
        assertEquals(TaskStatus.SOON, result)
    }

    @Test
    fun `75 дней из 100 — скоро`() {
        val result = StatusCalculator.status(task(75, 100), today)
        assertEquals(TaskStatus.SOON, result)
    }

    @Test
    fun `90 дней из 90 — просрочено`() {
        val result = StatusCalculator.status(task(90, 90), today)
        assertEquals(TaskStatus.LATE, result)
    }

    @Test
    fun `без срока — NO_DEADLINE`() {
        val result = StatusCalculator.status(task(500, null), today)
        assertEquals(TaskStatus.NO_DEADLINE, result)
    }

    @Test
    fun `0 дней из 30 — в порядке`() {
        val result = StatusCalculator.status(task(0, 30), today)
        assertEquals(TaskStatus.OK, result)
    }

    @Test
    fun `200 дней из 100 — прогресс не больше 1`() {
        val result = StatusCalculator.progress(task(200, 100), today)
        assertEquals(1f, result, 0.001f)
    }
}