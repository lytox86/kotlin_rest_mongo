package com.tngtech.kotlin.workshop._07.rest.controller

import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class LeadersControllerTest : PlayerScoreTestWithFongoAndMockMvc() {
    @Test
    fun getLeadersTest() {
        logger.info("Begin getLeadersTest")

        mvc.perform(MockMvcRequestBuilders.get("/leaders"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray )
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()" ).value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name" ).value(TEST_PLAYER_5.handle))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name" ).value(TEST_PLAYER_1.handle))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name" ).value(TEST_PLAYER_2.handle))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].points" ).value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].points" ).value(25))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].points" ).value(20))

        logger.info("End getLeadersTest")
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(LeadersControllerTest::class.java)
    }
}