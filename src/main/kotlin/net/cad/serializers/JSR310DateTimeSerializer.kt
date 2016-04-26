package net.cad.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

import java.io.IOException
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor

class JSR310DateTimeSerializer private constructor() : JsonSerializer<TemporalAccessor>() {

    @Throws(IOException::class)
    override fun serialize(value: TemporalAccessor, generator: JsonGenerator, serializerProvider: SerializerProvider) {
        generator.writeString(ISOFormatter.format(value))
    }

    companion object {

        private val ISOFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"))

        val INSTANCE = JSR310DateTimeSerializer()
    }
}
