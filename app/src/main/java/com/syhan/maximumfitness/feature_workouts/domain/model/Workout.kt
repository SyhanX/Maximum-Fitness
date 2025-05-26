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


private const val TAG = "Workout"

@Serializable
data class Workout(
    val id: Int,
    val title: String,
    val description: String?,
    val type: Int,
    @Serializable(with = WorkoutDurationSerializer::class)
    val duration: Int?
)

object WorkoutDurationSerializer : KSerializer<Int?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "duration",
        PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: Int?) {
        encoder.encodeInt(value ?: -1)
    }

    override fun deserialize(decoder: Decoder): Int? {
        val duration: Int?

        val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException(
            "This serializer only works with JSON :3"
        )
        val element = jsonDecoder.decodeJsonElement()

        duration = try {
            if (element is JsonPrimitive) {
                element.content
                    .toInt()
            } else throw SerializationException("Invalid value $element")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        return duration
    }
}