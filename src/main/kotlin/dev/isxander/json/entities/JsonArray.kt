package dev.isxander.json.entities

class JsonArray() : JsonElement(), Iterable<JsonElement> {

    private val list = arrayListOf<JsonElement>()

    constructor(array: JsonArray) : this() {
        list.addAll(array.list)
    }
    constructor(elements: Collection<JsonElement>) : this() {
        list.addAll(elements)
    }

    operator fun get(index: Int): JsonElement {
        return list[index]
    }

    operator fun plus(array: JsonArray): JsonArray {
        list.addAll(array.list)
        return this
    }

    operator fun plus(element: JsonElement): JsonArray {
        list.add(element)
        return this
    }

    operator fun minus(array: JsonArray): JsonArray {
        list.removeAll(array.list)
        return this
    }

    operator fun minus(element: JsonElement): JsonArray {
        list.remove(element)
        return this
    }

    val size: Int
        get() = list.size

    val indices: IntRange
        get() = list.indices

    val empty: Boolean
        get() = list.isEmpty()

    override fun iterator(): Iterator<JsonElement> = list.iterator()

}