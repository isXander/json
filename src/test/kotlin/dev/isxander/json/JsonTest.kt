package dev.isxander.json

import dev.isxander.json.entities.JsonArray
import dev.isxander.json.entities.JsonElement
import dev.isxander.json.entities.JsonObject
import dev.isxander.json.entities.asJson
import org.junit.jupiter.api.*

class JsonTest {

    @Test
    @DisplayName("Make sure parser has the same output as input")
    fun parserTest() {
        val input = JsonObject(mapOf(
            "empty_object" to JsonObject(),
            "empty_array" to JsonArray(),
            "array_with_stuff" to JsonArray(listOf(
                JsonObject(mapOf(
                    "im a child of an object" to "aga".asJson()
                )),
                'a'.asJson()
            )),
            "boolean go brrr" to true.asJson()
        ))


        println("INPUT")
        println(input.toPrettyString())
        println()
        println("OUTPUT")
        val output = JsonParser(input.toPrettyString(), true).parse<JsonElement>().toPrettyString()
        println(output)

        assert(input.toPrettyString() == output)
    }

}