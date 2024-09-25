package com.example.lern.tools

import com.example.lern.tools.Homework.fib
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo


class HomeworkTest {
    /*
        Test if fib(0) and fib(1) return 0 or 1
     */

    @Test
    fun `n is 0 returns 0`() {
        val result = fib(0)

        expectThat(result).isEqualTo(0)
    }

    @Test
    fun `n is 1 returns 1`() {
        val result = fib(1)

        expectThat(result).isEqualTo(1)
    }

    @Test
    fun `n is 5 returns 5`() {
        val result = fib(5)

        expectThat(result).isEqualTo(5)
    }

    @Test
    fun `n is 92 returns 7540113804746346429`() {
        val result = fib(92)

        expectThat(result).isEqualTo(7540113804746346429)
    }

    @Test
    fun `n is -1 returns null`() {
        val result = fib(-1)

        expectThat(result).isEqualTo(null)
    }
}