package com.tngtech.kotlin.workshop._07.rest.service

import com.tngtech.kotlin.workshop._07.rest.PlayerScoreTestWithFongo
import com.tngtech.kotlin.workshop._07.rest.model.Player
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.*

class PlayerServiceTest : PlayerScoreTestWithFongo() {
    @Autowired
    lateinit var playerService: PlayerService

    @Test
    fun testLeaders() {
        logger.info("Begin testLeaders")

        // Verify that the leaders are as expected.
        val leaders = playerService.leaders()
        assertEquals(3, leaders.size, "There should be 3 leaders.")
        assertEquals(TEST_PLAYER_5, leaders[0], "The first leader should be dawn.")
        assertEquals(TEST_PLAYER_1, leaders[1], "The second leader should be charlie.")
        assertEquals(TEST_PLAYER_2, leaders[2], "The third leader should be alice.")

        logger.info("End testLeaders")
    }

    @Test
    fun testAll() {
        logger.info("Begin testAll")

        // Verify that the leaders are as expected.
        val allPlayers = playerService.all()
        assertEquals(5, allPlayers.size, "There should be 5 players.")
        assertTrue(allPlayers.contains(TEST_PLAYER_1),"player 1 should be returned")
        assertTrue(allPlayers.contains(TEST_PLAYER_2),"player 2 should be returned")
        assertTrue(allPlayers.contains(TEST_PLAYER_3),"player 3 should be returned")
        assertTrue(allPlayers.contains(TEST_PLAYER_4),"player 4 should be returned")
        assertTrue(allPlayers.contains(TEST_PLAYER_5),"player 5 should be returned")
        assertEquals(allPlayers[0],TEST_PLAYER_2,"player 2 should be the first")
        assertEquals(allPlayers[4],TEST_PLAYER_4,"player 4 should be the last")
        logger.info("End testAll")
    }


    @Test
    fun testFind() {
        logger.info("Begin testFind")

        // Verify that the leaders are as expected.
        val player1 = playerService.find(TEST_PLAYER_1.handle)
        val player2 = playerService.find("unknown")

        assertNull(player2, "player unknown does not exist.")
        assertEquals(player1?.handle, TEST_PLAYER_1.handle, "player 1 should be found")

        logger.info("End testFind")
    }

    @Test
    fun testScore() {
        logger.info("Begin testScore")

        playerRepository.save(Player(TEST_PLAYER_HANDLE))

        // Score 10 points.
        playerService.score(TEST_PLAYER_HANDLE, 10)
        val player = playerRepository.findById(TEST_PLAYER_HANDLE).get()
        assertEquals(10, player.totalScore, "Total score should be 10 after the first scoring event.")
        assertEquals(1, player.history.size, "The history should have a single element.")
        assertEquals(10, player.history[0].points, "The recorded points should be 10.")

        // Score 5 more points.
        playerService.score(TEST_PLAYER_HANDLE, 5)
        val player2 = playerRepository.findById(TEST_PLAYER_HANDLE).get()
        assertEquals(15, player2.totalScore, "Total score should be 15 after the second scoring event.")
        assertEquals(2, player2.history.size, "The history should have a single element.")
        assertEquals(10, player2.history[0].points, "The first recorded points should be 10.")
        assertEquals(5, player2.history[1].points, "The second recorded points should be 5.")

        playerRepository.delete(Player(TEST_PLAYER_HANDLE))

        logger.info("End testScore")
    }

    @Test
    fun testEvents() {
        logger.info("Begin testEvents")

        playerRepository.save(Player(TEST_PLAYER_HANDLE))

        // Score 10 points.
        playerService.score(TEST_PLAYER_HANDLE, 10)

        // Score 5 more points.
        playerService.score(TEST_PLAYER_HANDLE, 5)

        val events = playerService.scoreEvents(TEST_PLAYER_HANDLE)
        assertEquals(2, events.size, "There should be 2 events.")
        assertEquals(10, events[0].points, "The first recorded points should be 10.")
        assertEquals(5, events[1].points, "The second recorded points should be 5.")

        playerRepository.delete(Player(TEST_PLAYER_HANDLE))

        logger.info("End testEvents")
    }

    @Test
    fun testDelete() {
        logger.info("Begin testDelete")

        playerRepository.save(Player(TEST_PLAYER_HANDLE))

        playerService.delete(TEST_PLAYER_HANDLE)
        val optPlayer = playerRepository.findById(TEST_PLAYER_HANDLE)
        assertFalse(optPlayer.isPresent,"player not deleted")

        logger.info("End testDelete")
    }

    @Test
    fun testCreate() {
        logger.info("Begin testCreate")

        playerService.create(TEST_PLAYER_HANDLE)
        val optPlayer = playerRepository.findById(TEST_PLAYER_HANDLE)
        assertTrue(optPlayer.isPresent,"player not created")

        playerRepository.delete(Player(TEST_PLAYER_HANDLE))

        logger.info("End testCreate")
    }

    @Test
    fun testGetScore() {
        logger.info("Begin testGetScore")

        var score = playerService.getScore(TEST_PLAYER_1.handle)
        assertEquals(score, 25,"TEST_PLAYER_1 should have 25 points")

        score = playerService.getScore(TEST_PLAYER_HANDLE)
        assertEquals(score, 0,"TEST_PLAYER_HANDLE should have 0 points")

        playerRepository.delete(Player(TEST_PLAYER_HANDLE))

        logger.info("End testGetScore")
    }


    companion object {
        val logger: Logger = LoggerFactory.getLogger(PlayerServiceTest::class.java)
        const val TEST_PLAYER_HANDLE = "testPlayer"
    }
}