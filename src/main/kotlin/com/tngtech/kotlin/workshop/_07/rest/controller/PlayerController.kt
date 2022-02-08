package com.tngtech.kotlin.workshop._07.rest.controller

import com.tngtech.kotlin.workshop._07.rest.model.toMap
import com.tngtech.kotlin.workshop._07.rest.service.PlayerService
import com.tngtech.kotlin.workshop._07.rest.model.Player
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class PlayerController {
    @Autowired
    lateinit var playerService: PlayerService

    @GetMapping("/player/{handle}/score")
    fun getPlayerScore(@PathVariable handle: String) : ResponseEntity<Int> {
        return ResponseEntity.ok(playerService.getScore(handle))
    }

    @GetMapping("/players")
    fun getPlayers() : ResponseEntity<List<Map<String,Any>>> {
        return ResponseEntity.ok(playerService.all().map(Player::toMap))
    }

    @PostMapping("/player")
    fun createPlayer(@RequestBody handle: String) : ResponseEntity<Map<String,Any>>  {
        return ResponseEntity.ok(playerService.create(handle).toMap())
    }

    @PostMapping("/player/{handle}/score")
    fun postPlayerScore(@PathVariable handle: String,
                        @RequestBody points: String) :  ResponseEntity<Int> {
        val score = playerService.score(handle, points.toInt())

        return ResponseEntity.ok(score)
    }

    @DeleteMapping("/player")
    fun deletePlayer(@RequestBody handle: String) : ResponseEntity<Void>  {
        return Optional.ofNullable(playerService.find(handle)).map{
            playerService.delete(it.handle)
            ResponseEntity.ok().build<Void>()
        }.orElse(ResponseEntity.notFound().build<Void>())
    }
}