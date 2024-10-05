package org.example

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class JunitClassOrderingTest {
    @Test
    fun testOrdering() {
        val comparer = JunitClassOrdering.StringContainsComparer(listOf("1", "2", "3"))
        listOf("1", "2", "3").sortedWith(comparer).shouldBe(listOf("1", "2", "3"))
        listOf("2", "1", "3").sortedWith(comparer).shouldBe(listOf("1", "2", "3"))

        listOf("2", "x", "1", "3").sortedWith(comparer).shouldBe(listOf("1", "2", "3", "x"))
        listOf("x", "2", "x", "1", "3", "x").sortedWith(comparer).shouldBe(listOf("1", "2", "3", "x", "x", "x"))
    }
}