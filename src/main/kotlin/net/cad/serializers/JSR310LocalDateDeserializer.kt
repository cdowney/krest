package net.cad.serializers

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

/**
 * Custom Jackson deserializer for transforming a JSON object (using the ISO 8601 date formatwith optional time)
 * to a JSR310 LocalDate object.
 */
class JSR310LocalDateDeserializer private constructor() : JsonDeserializer<LocalDate>() {

    @Throws(IOException::class)
    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalDate? {
        when (parser.currentToken) {
            JsonToken.START_ARRAY -> {
                if (parser.nextToken() == JsonToken.END_ARRAY) {
                    return null
                }
                val year = parser.intValue

                parser.nextToken()
                val month = parser.intValue

                parser.nextToken()
                val day = parser.intValue

                if (parser.nextToken() != JsonToken.END_ARRAY) {
                    throw context.wrongTokenException(parser, JsonToken.END_ARRAY, "Expected array to end.")
                }
                return LocalDate.of(year, month, day)
            }

            JsonToken.VALUE_STRING -> {
                val string = parser.text.trim { it <= ' ' }
                if (string.length == 0) {
                    return null
                }
                return LocalDate.parse(string, ISO_DATE_OPTIONAL_TIME)
            }
        }
        throw context.wrongTokenException(parser, JsonToken.START_ARRAY, "Expected array or string.")
    }

    companion object {

        val INSTANCE = JSR310LocalDateDeserializer()

        private val ISO_DATE_OPTIONAL_TIME: DateTimeFormatter

        init {
            ISO_DATE_OPTIONAL_TIME = DateTimeFormatterBuilder().append(DateTimeFormatter.ISO_LOCAL_DATE).optionalStart().appendLiteral('T').append(DateTimeFormatter.ISO_OFFSET_TIME).toFormatter()
        }
    }
}
