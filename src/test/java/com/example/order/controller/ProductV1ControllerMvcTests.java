package com.example.order.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureCache
class ProductV1ControllerMvcTests {

	@Autowired
    private MockMvc mockMvc;
	
    private static MongodExecutable mongodExecutable;
    private static MongoClient mongoClient;
    
    @BeforeAll
    static void setUp() throws Exception {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        int port = 27017;
        mongodExecutable = starter.prepare(MongodConfig.builder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build());
        mongodExecutable.start();
        mongoClient = MongoClients.create("mongodb://localhost:" + port);
    }

    @AfterAll
    static void tearDown() {
        if (mongoClient != null) {
            mongoClient.close();
        }
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    } 
    
    @Test
    void testGetProductsOk() throws Exception {
    	this.mockMvc.perform(get("/v1/products"))
				.andExpect(content().string(notNullValue()))
				.andExpect(status().isOk());
    }  
}
