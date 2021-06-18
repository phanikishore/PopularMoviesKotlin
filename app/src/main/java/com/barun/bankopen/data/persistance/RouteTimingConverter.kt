package com.barun.bankopen.data.persistance

import androidx.room.TypeConverter
import com.barun.bankopen.data.model.RouteTiming
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RouteTimingConverter {

    @TypeConverter
    fun stringToList(json: String?): List<RouteTiming>? {
        return json?.let { Json.decodeFromString(ListSerializer(RouteTiming.serializer()), it) }
    }

    @TypeConverter
    fun listToString(list: List<RouteTiming?>?): String {
       return Json.encodeToString(list)
    }

}