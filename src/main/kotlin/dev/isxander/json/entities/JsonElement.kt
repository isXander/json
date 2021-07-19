package dev.isxander.json.entities

import dev.isxander.json.JsonStringifier

sealed class JsonElement {

    val asJsonObject: JsonObject?
        get() = this as? JsonObject

    val asJsonArray: JsonArray?
        get() = this as? JsonArray

    val asJsonString: JsonString?
        get() = this as? JsonString
    val asString: String?
        get() = asJsonString?.primitive

    val asJsonInt: JsonInt?
        get() = this as? JsonInt
    val asInt: Int?
        get() = asJsonInt?.primitive

    val asJsonFloat: JsonFloat?
        get() = this as? JsonFloat
    val asFloat: Float?
        get() = asJsonFloat?.primitive

    val asJsonDouble: JsonDouble?
        get() = this as? JsonDouble
    val asDouble: Double?
        get() = asJsonDouble?.primitive

    val asJsonLong: JsonLong?
        get() = this as? JsonLong
    val asLong: Long?
        get() = asJsonLong?.primitive

    val asJsonBool: JsonBool?
        get() = this as? JsonBool
    val asBool: Boolean?
        get() = asJsonBool?.primitive

    val asJsonChar: JsonChar?
        get() = this as? JsonChar
    val asChar: Char?
        get() = asJsonChar?.primitive

    val asJsonNull: JsonNull?
        get() = this as? JsonNull

    override fun toString(): String {
        return JsonStringifier().toJson(this)
    }

    fun toPrettyString(indentationAmount: Int = 2): String {
        return JsonStringifier(prettyPrint = true, indentationAmount = indentationAmount).toJson(this)
    }
}