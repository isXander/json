package dev.isxander.json.entities

/**
 * This is used to represent the null pointer.
 * Maps cannot distinguish between a key with null as a value
 * or no key at all.
 */
object JsonNull : JsonElement()
fun Any?.asJson() = JsonNull