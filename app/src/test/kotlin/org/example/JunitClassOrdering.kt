package org.example

import org.junit.jupiter.api.ClassDescriptor
import org.junit.jupiter.api.ClassOrderer
import org.junit.jupiter.api.ClassOrdererContext

class JunitClassOrdering : ClassOrderer {

    override fun orderClasses(context: ClassOrdererContext) {
        context.classDescriptors.sortWith(Comparer())
        println("Ordered: ${context.classDescriptors.map { it.testClass.name }}")
    }

    class Comparer: Comparator<ClassDescriptor> {
        override fun compare(o1: ClassDescriptor, o2: ClassDescriptor): Int {
            if (o1.testClass.name.lowercase().endsWith("slowtest")) {
                if (o2.testClass.name.lowercase().endsWith("slowtest")) {
                    return o1.testClass.name.compareTo(o2.testClass.name)
                }
                return -1
            }
            if (o1.testClass.name.lowercase().endsWith("fasttest")) {
                if (o2.testClass.name.lowercase().endsWith("fasttest")) {
                    return o1.testClass.name.compareTo(o2.testClass.name)
                }
                return -1
            }

            return o1.testClass.name.compareTo(o2.testClass.name)
        }
    }
}