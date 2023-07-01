package com.example.todoappfisko.repository.network

import com.squareup.moshi.Json
import java.util.Date

data class BackendList(
    @field:Json(name = "status") val status: String,
    @field:Json(name = "list") val list: List<BackendItem>,
    @field:Json(name = "revision") val revision: Int
)

data class BackendElement(
    @field:Json(name = "status") val status: String,
    @field:Json(name = "element") val element: BackendItem,
    @field:Json(name = "revision") val revision: Int
)

data class BackendItem(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "text") val text: String,
    @field:Json(name = "importance") val importance: BackendImportance,
    @field:Json(name = "done") val completed: Boolean,
    @field:Json(name = "deadline") val deadline: Long,
    @field:Json(name = "created_at") val createdAt: Long,
    @field:Json(name = "changed_at") val changedAt: Long,
    @field:Json(name = "last_updated_by") val lastUpdatedBy: String
)


enum class BackendImportance(val value: String) {
    @field:Json(name = "low") LOW("low"),
    @field:Json(name = "basic") BASIC("basic")
}
