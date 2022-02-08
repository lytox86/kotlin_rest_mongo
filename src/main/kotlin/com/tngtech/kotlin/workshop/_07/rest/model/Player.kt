package com.tngtech.kotlin.workshop._07.rest.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.index.Indexed
import java.text.SimpleDateFormat
import java.util.*

@TypeAlias("player")
@Document(collection = "players")
data class Player(@Id val handle: String,
                  @Indexed val totalScore: Int = 0,
                  val history: List<ScoreEvent> = listOf()) {
    operator fun plus(score: Int) = Player(handle, totalScore + score, history + ScoreEvent(score))
}

data class ScoreEvent(@Id val time: String,
                      val points: Int) {
    constructor(points: Int) : this(dateFormat.format(Date()), points)
    //needed by injection ?
    constructor() : this(dateFormat.format(Date()), 0)

    companion object {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    }
}

fun Player.toMap(): Map<String,Any> {
    val (name,points) = this

    return mapOf("name" to name, "points" to points)
}
