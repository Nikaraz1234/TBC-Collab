package com.example.tbcworks.data.model.event

import kotlinx.serialization.Serializable

@Serializable
enum class LocationTypeEnum {
    InPerson,
    Virtual,
    Hybrid
}