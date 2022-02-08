package com.tngtech.kotlin.workshop._07.rest.repository

import com.tngtech.kotlin.workshop._07.rest.model.Player
import org.springframework.data.mongodb.repository.MongoRepository

interface PlayerRepository : MongoRepository<Player, String> {
    fun findTop3ByOrderByTotalScoreDesc(): List<Player>
    fun findByOrderByHandle(): List<Player>
}