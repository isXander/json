package dev.isxander.json

import dev.isxander.json.entities.*
import dev.isxander.json.exception.JsonSyntaxException
import java.io.File
import java.lang.StringBuilder
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

/**
 * Converts JSON text into a parse tree of [JsonElement]s
 *
 * @param json input string
 * @param lenient whether or not parser should skip over errors and create
 * potentially malformed parse tree or throw exceptions
 *
 * @throws JsonSyntaxException when parser encounters a fatal error or when the json is invalid when [lenient] is disabled
 *
 * @since 1.0
 * @author isXander
 */
class JsonParser(val json: String, val lenient: Boolean) {

    /** Current character index */
    private var i = -1

    /**
     * Gets json string from [path]
     *
     * @since 1.0
     * @author isXander
     */
    constructor(path: Path, lenient: Boolean) : this(Files.lines(path).collect(Collectors.joining("\n")), lenient)

    /**
     * Gets json string from [file]
     *
     * @since 1.0
     * @author isXander
     */
    constructor(file: File, lenient: Boolean) : this(Files.lines(file.toPath()).collect(Collectors.joining("\n")), lenient)

    /**
     * Executes the parsing
     *
     * @param T the type of JsonElement you expect to receive
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : JsonElement> parse(): T {
        return nextUnknown() as T
    }

    private fun nextUnknown(): JsonElement {
        while (i < json.length) {
            i++
            val char = json[i]

            when {
                IGNORED_CHARS.contains(char) -> continue
                char == '{' -> return nextObject()
                char == '[' -> return nextArray()
                char == '"' -> return nextString()
                char == '\'' -> return nextChar()
                char.isNumber() -> return nextNumber()
                json.substring(i).isBoolLazy() -> return nextBool()
                json.substring(i).startsWith("null") -> return nextNull()
                // no valid character was given. if we arent lenient throw exception
                !lenient -> throw JsonSyntaxException("Invalid character was given: $char at index $i")
            }
        }
        throw JsonSyntaxException("No valid character was ever given!")
    }

    private fun nextObject(): JsonObject {
        val obj = JsonObject()
        var currentKey: String? = null
        var expectingKey = true

        while (i < json.length) {
            i++
            val char = json[i]

            if (IGNORED_CHARS.contains(char)) continue
            // we are looking for a key so there should be no character
            // apart from a whitespace or a " if we are lenient just skip over
            if (char == '"' && expectingKey) {
                expectingKey = false
                currentKey = nextString().primitive
                continue
            }

            if (char == ':') {
                if (currentKey != null) {
                    obj[currentKey] = nextUnknown()
                    currentKey = null
                    continue
                } else if (lenient) {
                    continue
                } else {
                    throw JsonSyntaxException("No key was specified for value!")
                }
            }

            if (char == ',') {
                if (expectingKey) {
                    if (!lenient) throw JsonSyntaxException("Comma was given but parser was already expecting a key.")
                } else {
                    expectingKey = true
                    continue
                }
            }

            if (char == '}') {
                if (currentKey != null) {
                    if (!lenient) throw JsonSyntaxException("No value has been given to key before ending the object.")
                } else {
                    return obj
                }
            }
        }
        throw JsonSyntaxException("Object was never closed.")
    }

    private fun nextArray(): JsonArray {
        val array = JsonArray()
        var expectingVal = true

        while (i < json.length) {
            i++
            val char = json[i]

            if (char == ',') {
                if (expectingVal) {
                    if (!lenient) throw JsonSyntaxException("Reader was already expecting value but another comma was given")
                    continue
                }
                expectingVal = true
                continue
            }

            if (char == ']') {
                // trailing commas doesn't hurt anyone...
                return array
            }

            if (IGNORED_CHARS.contains(char)) continue

            if (expectingVal) {
                array + nextUnknown()
                expectingVal = false
                continue
            }
        }
        throw JsonSyntaxException("Array was never closed.")
    }

    private fun nextString(): JsonString {
        var escapedLast = false
        val sb = StringBuilder()

        while (i < json.length) {
            i++
            val char = json[i]

            if (char == '\\') {
                if (escapedLast) {
                    sb.append('\\')
                } else {
                    escapedLast = true
                }
                continue
            }

            if (char == '"' && !escapedLast) {
                return sb.toString().asJson()
            }

            sb.append(char)
        }
        throw JsonSyntaxException("String literal was never closed.")
    }

    private fun nextNumber(): JsonElement {
        return JsonInt(1)
    }

    private fun nextBool(): JsonBool {
        val cutString = json.substring(i)
        return when {
            cutString.startsWith("true") -> true.asJson()
            cutString.startsWith("false") -> false.asJson()
            lenient -> false.asJson()
            else -> throw JsonSyntaxException("Boolean was expected.")
        }
    }

    private fun nextChar(): JsonChar {
        i++
        return json[i].asJson()
    }

    private fun nextNull(): JsonNull {
        i += 3
        return JsonNull
    }

    companion object {
        val IGNORED_CHARS = arrayOf(' ', '\n', '\t')
    }

}

private fun String.isBoolLazy(): Boolean = this.startsWith("true") || this.startsWith("false")

private fun Char.isNumber(): Boolean = when (this) {
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> true
    else -> false
}