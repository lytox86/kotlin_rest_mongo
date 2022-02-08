package com.tngtech.kotlin.workshop._07.rest.controller

import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class PlayerControllerTest : PlayerScoreTestWithFongoAndMockMvc() {
    @Test
    fun postPlayerScoreTest() {
        logger.info("Begin postPlayerScoreTest")

        val points = 5
        val expectedTotalScore = TEST_PLAYER_1.totalScore + points
        val expectedResult = "$expectedTotalScore"

        // Add 5 points to TEST_PLAYER_1's score.
        mvc.perform(MockMvcRequestBuilders
                .post("/player/${TEST_PLAYER_1.handle}/score")
                .content(points.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(expectedResult))

        playerRepository.save(TEST_PLAYER_1)

        logger.info("End postPlayerScoreTest")
    }

    @Test
    fun postPlayerTest() {
        logger.info("Begin postPlayerTest")

        val points = 5
        val expectedResult = "$points"
        val name = "John Doe"
        val unknown = "unknown"

        // Add 5 points to unknown player
        mvc.perform(MockMvcRequestBuilders
                .post("/player/$unknown/score")
                .content(points.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(expectedResult))

        mvc.perform(MockMvcRequestBuilders
                .post("/player")
                .content(name))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.name" ).value(name))

        playerRepository.findById(unknown).ifPresent({playerRepository.delete(it)})
        playerRepository.findById(name).ifPresent({playerRepository.delete(it)})

        logger.info("End postPlayerTest")
    }

    @Test
    fun getPlayerScoreTest() {
        logger.info("Begin getPlayerScoreTest")

        val expectedTotalScore = TEST_PLAYER_1.totalScore
        val expectedResult = "$expectedTotalScore"

        // Get points of TEST_PLAYER_1

        mvc.perform(MockMvcRequestBuilders.get("/player/${TEST_PLAYER_1.handle}/score"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().json(expectedResult))

        logger.info("End getPlayerScoreTest")
    }

    @Test
    fun getPlayers() {
        logger.info("Begin getPlayers")

        mvc.perform(MockMvcRequestBuilders.get("/players"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray )
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()" ).value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name" ).value("alice"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name" ).value("bob"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name" ).value("charlie"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].name" ).value("dawn"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].name" ).value("ed"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].points" ).value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].points" ).value(15))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].points" ).value(25))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].points" ).value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].points" ).value(10))

        logger.info("End getPlayers")
    }


    @Test
    fun deletePlayerTest() {
        logger.info("Begin deletePlayerTest")

        // Delete unknown player
        mvc.perform(MockMvcRequestBuilders.delete("/player")
                .content("newPlayer"))
                .andExpect(MockMvcResultMatchers.status().isNotFound)

        // Delete TEST_PLAYER_1
        mvc.perform(MockMvcRequestBuilders.delete("/player")
                .content(TEST_PLAYER_1.handle))
                .andExpect(MockMvcResultMatchers.status().isOk)

        playerRepository.save(TEST_PLAYER_1)

        logger.info("End deletePlayerTest")
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(PlayerControllerTest::class.java)
    }
}