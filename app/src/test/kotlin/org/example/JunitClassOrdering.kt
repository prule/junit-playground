package org.example

import org.junit.jupiter.api.ClassDescriptor
import org.junit.jupiter.api.ClassOrderer
import org.junit.jupiter.api.ClassOrdererContext
import org.junit.jupiter.api.Test

class JunitClassOrdering : ClassOrderer {

    val testOrder = listOf("slowtest", "fasttest")

    override fun orderClasses(context: ClassOrdererContext) {
        context.classDescriptors.sortWith(Comparer(testOrder))
        println("Ordered: ${context.classDescriptors.map { it.testClass.name }}")
    }

    class Comparer(val order: List<String>): Comparator<ClassDescriptor> {
        val comparer = StringContainsComparer(order)

        override fun compare(o1: ClassDescriptor, o2: ClassDescriptor): Int {
            return comparer.compare(o1.testClass.name, o2.testClass.name)
        }
    }

    class StringContainsComparer(val order: List<String>): Comparator<String> {
        override fun compare(o1: String, o2: String): Int {
            order.forEachIndexed { index, it ->
                if (o1.lowercase().contains(it)) {
                    if (o2.lowercase().contains(it)) {
                        // if they are both of the same type, sort by name
                        return o1.compareTo(o2)
                    }
                    // otherwise o1 comes first
                    order.forEachIndexed { index2, it2 ->
                        if (o2.lowercase().contains(it2)) {
                            return index.compareTo(index2)
                        }
                    }
                    return -1
                }
            }
            return 1
        }
    }

}