package dev.isxander.json.entities

class JsonBool(val primitive: Boolean) : JsonElement()
fun Boolean.asJson(): JsonBool = JsonBool(this)

class JsonString(val primitive: String) : JsonElement()
fun String.asJson(): JsonString = JsonString(this)

class JsonInt(val primitive: Int) : JsonElement()
fun Int.asJson(): JsonInt = JsonInt(this)

class JsonFloat(val primitive: Float) : JsonElement()
fun Float.asJson(): JsonFloat = JsonFloat(this)

class JsonDouble(val primitive: Double) : JsonElement()
fun Double.asJson(): JsonDouble = JsonDouble(this)

class JsonLong(val primitive: Long) : JsonElement()
fun Long.asJson(): JsonLong = JsonLong(this)

class JsonChar(val primitive: Char) : JsonElement()
fun Char.asJson(): JsonChar = JsonChar(this)