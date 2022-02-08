package com.tngtech.kotlin.workshop._07.rest


import com.github.fakemongo.Fongo
import com.mongodb.MongoClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.mongodb.config.AbstractMongoConfiguration


@Configuration
class TestConfig : AbstractMongoConfiguration() {
    @Autowired
    lateinit var env: Environment

    override fun getDatabaseName() = env.getProperty("mongo.db.name", "test")

    @Throws(Exception::class)
    override fun mongoClient(): MongoClient {
        logger.info("Instantiating Fongo with name $databaseName.")
        return Fongo(databaseName).mongo
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(TestConfig::class.java)
    }
}