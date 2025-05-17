package com.syhan.maximumfitness.feature_workouts.domain.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive

@Serializable
data class Workout(
    val id: Int,
    val title: String,
    val description: String?,
    val type: Int,
    @Serializable(with = WorkoutDurationSerializer::class)
    val duration: String
)

object WorkoutDurationSerializer : KSerializer<String> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "duration",
        PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeString(value)
    }

    override fun deserialize(decoder: Decoder): String {
        var duration: String? = null

        val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException(
            "This serializer only works with JSON :3"
        )
        val element = jsonDecoder.decodeJsonElement()

        duration = if (element is JsonPrimitive && element.isString) {
            element.content
        } else if (element is JsonPrimitive && !element.isString) {
            element.content.toString()
        } else throw SerializationException("Invalid value $element")

        return duration
    }
}