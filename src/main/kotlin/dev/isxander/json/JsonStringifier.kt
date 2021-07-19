package dev.isxander.json

import dev.isxander.json.entities.*
import java.io.File
import java.io.FileNotFoundException
import java.lang.StringBuilder
import java.nio.file.Files
import java.nio.file.Path

class JsonStringifier(val prettyPrint: Boolean = false, val indentationAmount: Int = 2) {

    private var depth = 0

    fun toJson(element: JsonElement): String {
        return when (element) {
            is JsonArray -> toJson(element.asJsonArray!!)
            is JsonObject -> toJson(element.asJsonObject!!)
            is JsonString -> toJson(element.asJsonString!!)
            is JsonBool -> toJson(element.asJsonBool!!)
            is JsonInt -> toJson(element.asJsonInt!!)
            is JsonFloat -> toJson(element.asJsonFloat!!)
            is JsonDouble -> toJson(element.asJsonDouble!!)
            is JsonChar -> toJson(element.asJsonChar!!)
            is JsonNull -> toJson(element.asJsonNull!!)
            is JsonLong -> toJson(element.asJsonLong!!)
        }
    }

    fun toJson(array: JsonArray): String {
        if (array.empty) return "[]"

        val sb = StringBuilder()
        sb.append("[")
        depth++
        for (i in array.indices) {
            val element = array[i]
            sb.append("$indent${toJson(element)}")
            if (i != array.size - 1) sb.append(",")
        }
        depth--
        sb.append("$indent]")
        return sb.toString()
    }

    fun toJson(obj: JsonObject): String {
        if (obj.empty) return "{}"

        val sb = StringBuilder()
        sb.append("{")
        depth++
        var i = 0
        for ((key, value) in obj) {
            sb
                .append(indent)
                .append(toJson(key.asJson()))
                .append(":")
                .append(if (prettyPrint) " " else "")
                .append(toJson(value))
                .append(if (i < obj.size - 1) "," else "")

            i++
        }
        depth--
        sb.append("$indent}")
        return sb.toString()
    }

    fun toJson(string: JsonString): String {
        return "\"${string.primitive}\""
    }

    fun toJson(int: JsonInt): String {
        return int.primitive.toString()
    }

    fun toJson(float: JsonFloat): String {
        return float.primitive.toString()
    }

    fun toJson(double: JsonDouble): String {
        return double.primitive.toString()
    }

    fun toJson(bool: JsonBool): String {
        return bool.primitive.toString()
    }

    fun toJson(char: JsonChar): String {
        return "'${char.primitive}'"
    }

    fun toJson(jsonNull: JsonNull): String {
        return "null"
    }

    private val indent: String
        get() = if (prettyPrint) "\n${" ".repeat(depth * indentationAmount)}" else ""

    fun writeToFile(element: JsonElement, path: Path) {
        if (Files.exists(path) && Files.isDirectory(path))
            throw FileNotFoundException("Path is a directory")

        Files.createDirectories(path.parent)
        Files.write(path, toJson(element).toByteArray())
    }

    fun writeToFile(element: JsonElement, file: File) = writeToFile(element, file.toPath())

}

