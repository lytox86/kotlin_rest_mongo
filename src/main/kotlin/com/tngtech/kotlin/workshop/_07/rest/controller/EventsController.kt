package com.tngtech.kotlin.workshop._07.rest.controller

import com.tngtech.kotlin.workshop._07.rest.model.ScoreEvent
import com.tngtech.kotlin.workshop._07.rest.service.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class EventsController {
    @Autowired
    lateinit var playerService: PlayerService

    @GetMapping("/player/{handle}/events")
    fun getPlayerScore(@PathVariable handle: String) : ResponseEntity<List<ScoreEvent>> {
        return ResponseEntity.ok(playerService.scoreEvents(handle))
    }
}