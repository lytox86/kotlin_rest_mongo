package com.tngtech.kotlin.workshop._07.rest.controller

import com.tngtech.kotlin.workshop._07.rest.model.toMap
import com.tngtech.kotlin.workshop._07.rest.service.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LeadersController {
    @Autowired
    lateinit var playerService: PlayerService

    @GetMapping("/leaders")
    fun getLeaders(): ResponseEntity<List<Map<String,Any>>> {
        return ResponseEntity.ok(playerService.leaders().map {it.toMap()})
    }
}