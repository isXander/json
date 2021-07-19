package dev.isxander.json.entities

import kotlin.collections.LinkedHashMap

/**
 * A map of json entries
 *
 * A LinkedHashMap is used to maintain a constant order throughout serialization and deserialization
 *
 * @constructor an empty object
 * @author isXander
 * @since 1.0
 */
class JsonObject() : JsonElement(), Iterable<Map.Entry<String, JsonElement>> {

    val map = LinkedHashMap<String, JsonElement>()

    /**
     * A json object with the initial entries being the object provided
     *
     * @since 1.0
     * @author isXander
     */
    constructor(obj: JsonObject) : this() { addAll(obj) }

    /**
     * A json object with the initial entries being the map provided
     *
     * @since 1.0
     * @author isXander
     */
    constructor(map: Map<String, JsonElement>) : this() { this.map.putAll(map) }

    /**
     * Get a value from the json map
     *
     * @param element the optional return value if the key does not exist
     * @since 1.0
     * @author isXander
     */
    operator fun get(key: String, element: JsonElement? = null): JsonElement? = map[key] ?: element

    /**
     * Get a value from the json map
     *
     * @param element the optional return value if the key does not exist
     * @since 1.0
     * @author isXander
     */
    operator fun get(key: String, element: JsonObject? = null): JsonObject? = map[key]?.asJsonObject ?: element

    /**
     * Get a value from the json map
     *
     * @param element the optional return value if the key does not exist
     * @since 1.0
     * @author isXander
     */
    operator fun get(key: String, element: JsonArray? = null): JsonArray? = map[key]?.asJsonArray ?: element

    /**
     * Get a value from the json map
     *
     * @param element the optional return value if the key does not exist
     * @since 1.0
     * @author isXander
     */
    operator fun get(key: String, element: JsonBool? = null): JsonBool? = map[key]?.asJsonBool ?: element

    /**
     * Get a value from the json map
     *
     * @param element the optional return value if the key does not exist
     * @since 1.0
     * @author isXander
     */
    operator fun get(key: String, element: JsonInt? = null): JsonInt? = map[key]?.asJsonInt ?: element

    /**
     * Get a value from the json map
     *
     * @param element the optional return value if the key does not exist
     * @since 1.0
     * @author isXander
     */
    operator fun get(key: String, element: JsonFloat? = null): JsonFloat? = map[key]?.asJsonFloat ?: element

    /**
     * Get a value from the json map
     *
     * @param element the optional return value if the key does not exist
     * @since 1.0
     * @author isXander
     */
    operator fun get(key: String, element: JsonDouble? = null): JsonDouble? = map[key]?.asJsonDouble ?: element

    /**
     * Get a value from the json map
     *
     * @param element the optional return value if the key does not exist
     * @since 1.0
     * @author isXander
     */
    operator fun get(key: String, element: JsonString? = null): JsonString? = map[key]?.asJsonString ?: element

    /**
     * Get a value from the json map
     *
     * @param element the optional return value if the key does not exist
     * @since 1.0
     * @author isXander
     */
    operator fun get(key: String, element: Boolean? = null): Boolean? = map[key]?.asBool ?: element

    /**
     * Get a value from the json map
     *
     * @param element the optional return value if the key does not exist
     * @since 1.0
     * @author isXander
     */
    operator fun get(key: String, element: String? = null): String? = map[key]?.asString ?: element

    /**
    * Get a value from the json map
    *
    * @param element the optional return value if the key does not exist
    * @since 1.0
    * @author isXander
    */
    operator fun get(key: String, element: Int? = null): Int? = map[key]?.asInt ?: element

    /**
     * Get a value from the json map
     *
     * @param element the optional return value if the key does not exist
     * @since 1.0
     * @author isXander
     */
    operator fun get(key: String, element: Float? = null): Float? = map[key]?.asFloat ?: element

    /**
     * Get a value from the json map
     *
     * @param element the optional return value if the key does not exist
     * @since 1.0
     * @author isXander
     */
    operator fun get(key: String, element: Double? = null): Double? = map[key]?.asDouble ?: element

    /**
     * Set a value in the json map
     *
     * @param element the element to add
     * @since 1.0
     * @author isXander
     */
    operator fun set(key: String, element: JsonElement?) {
        map[key] = element ?: JsonNull
    }

    /**
     * @return a new object with both references combined
     * @since 1.0
     * @author isXander
     */
    operator fun plus(obj: JsonObject): JsonObject {
        val newMap = LinkedHashMap(map)
        newMap.putAll(obj.map)
        return JsonObject(newMap)
    }

    /**
     * Adds all entries to the map
     *
     * @since 1.0
     * @author isXander
     */
    fun addAll(obj: JsonObject) = map.putAll(obj.map)

    /**
     * Adds all entries to the map
     *
     * @since 1.0
     * @author isXander
     */
    fun addAll(map: Map<String, JsonElement>) = this.map.putAll(map)

    /**
     * @return a new object with all the object's entries removed from the map
     * @since 1.0
     * @author isXander
     */
    operator fun minus(obj: JsonObject): JsonObject {
        val newMap = LinkedHashMap(map)
        obj.map.forEach { (key, _) -> newMap.remove(key) }
        return JsonObject(newMap)
    }

    /**
     * Removes all entries in the object from the map
     *
     * @since 1.0
     * @author isXander
     */
    fun remove(obj: JsonObject) = obj.map.forEach {(key, _) -> map.remove(key)}

    /**
     * Removes all entries in the collection from the map
     *
     * @since 1.0
     * @author isXander
     */
    fun remove(keys: Collection<String>) = keys.forEach { map.remove(it) }

    /**
     * Removes all entries in the array from the map
     *
     * @since 1.0
     * @author isXander
     */
    fun remove(vararg key: String) = key.iterator().forEach { map.remove(it) }

    /**
     * The current size of the map
     *
     * @since 1.0
     * @author isXander
     */
    val size: Int get() = map.size

    /**
     * @returns if the map is empty
     * @since 1.0
     * @author isXander
     */
    val empty: Boolean get() = map.isEmpty()

    override fun iterator(): Iterator<Map.Entry<String, JsonElement>> = map.iterator()



}