package com.kishore.tmdbapp.data.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object JsonMapSerializer : KSerializer<Map<String, List<RouteTiming>>> {
    private val mapSerializer =
        MapSerializer(String.serializer(), ListSerializer(RouteTiming.serializer()))
    override val descriptor: SerialDescriptor
        get() = mapSerializer.descriptor

    override fun deserialize(decoder: Decoder): Map<String, List<RouteTiming>> {
        return mapSerializer.deserialize(decoder)
    }

    override fun serialize(encoder: Encoder, value: Map<String, List<RouteTiming>>) {
        return mapSerializer.serialize(encoder, value)
    }

}