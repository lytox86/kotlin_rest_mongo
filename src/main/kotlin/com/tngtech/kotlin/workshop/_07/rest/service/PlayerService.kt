package com.tngtech.kotlin.workshop._07.rest.service

import com.tngtech.kotlin.workshop._07.rest.model.Player
import com.tngtech.kotlin.workshop._07.rest.model.ScoreEvent
import com.tngtech.kotlin.workshop._07.rest.repository.PlayerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface PlayerService {
    fun leaders() : List<Player>
    fun score(handle: String, points: Int) : Int
    fun getScore(handle: String) : Int
    fun all() : List<Player>
    fun create(handle: String): Player
    fun delete(handle: String)
    fun find(handle: String): Player?
    fun scoreEvents(handle: String): List<ScoreEvent>
}

@Service("playerService")
class PlayerServiceImpl : PlayerService {
    @Autowired
    lateinit var playerRepository: PlayerRepository

    override fun leaders(): List<Player> = playerRepository.findTop3ByOrderByTotalScoreDesc()

    override fun score(handle: String, points: Int): Int {
        val player = playerRepository.findById(handle).orElse(Player(handle)) + points
        playerRepository.save(player)
        return player.totalScore
    }
    override fun find(handle: String): Player? {
        val player = playerRepository.findById(handle)
        return if (player.isPresent) player.get() else null
    }

    override fun getScore(handle: String): Int {
        val player = playerRepository.findById(handle).orElseGet{
            var p = Player(handle)
            playerRepository.save(p)
            p
        }
        return player.totalScore
    }

    override fun all(): List<Player> = playerRepository.findByOrderByHandle()

    override fun create(handle: String): Player {
        return playerRepository.save(Player(handle))
    }

    override fun delete(handle: String) {
        playerRepository.findById(handle).ifPresent({playerRepository.delete(it)})
    }

    override fun scoreEvents(handle: String): List<ScoreEvent> {
        return playerRepository.findById(handle).map{it.history}.orElse(emptyList())
    }
}