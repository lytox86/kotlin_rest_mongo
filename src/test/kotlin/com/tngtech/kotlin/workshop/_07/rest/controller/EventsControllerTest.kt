package com.tngtech.kotlin.workshop._07.rest.controller

import com.tngtech.kotlin.workshop._07.rest.model.Player
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class EventsControllerTest : PlayerScoreTestWithFongoAndMockMvc() {
    @Test
    fun getEventsTest() {
        logger.info("Begin getEventsTest")

        var player = Player(TEST_PLAYER_HANDLE)
        playerRepository.save(player)
        player += 10
        player += 15
        player += 5

        playerRepository.save(player)

        mvc.perform(MockMvcRequestBuilders.get("/player/$TEST_PLAYER_HANDLE/events"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray )
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()" ).value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].points" ).value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].points" ).value(15))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].points" ).value(5))

        playerRepository.delete(player)

        logger.info("End getEventsTest")
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(EventsControllerTest::class.java)
        const val TEST_PLAYER_HANDLE = "testPlayer"

    }
}