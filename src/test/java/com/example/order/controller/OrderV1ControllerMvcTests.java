package com.example.order.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.order.dto.OrderProductRequestDTO;
import com.example.order.dto.OrderRequestDTO;
import com.example.order.entity.Product;
import com.example.order.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
class OrderV1ControllerMvcTests {

	@Autowired
    private MockMvc mockMvc;
	
	@MockitoBean
	private ProductRepository productRepository;
	
    private static MongodExecutable mongodExecutable;
    private static MongoClient mongoClient;
    
    private ObjectMapper objectMapper = new ObjectMapper();

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
    void testGetOrdersEndpoint() throws Exception {
        this.mockMvc.perform(get("/v1/orders"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testCreateOrderEndpointEmptyBodyBadRequest() throws Exception {
		OrderRequestDTO orderDTO = new OrderRequestDTO();
    	this.mockMvc.perform(post("/v1/orders")
				.content(objectMapper.writeValueAsString(orderDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString(
						"[{\"ReasonCode\":\"invalid.data.address\",\"Description\":\"Field invalid data\",\"Details\":\"must not be null\",\"Recoverable\":false}]")))
				.andExpect(status().isBadRequest());
    }
    
    @Test
    void testCreateOrderEndpointOk() throws Exception {
    	OrderRequestDTO orderDTO = new OrderRequestDTO();
		List<OrderProductRequestDTO> orderProductList = new ArrayList<>();
		OrderProductRequestDTO orderProductRequestDTO = new OrderProductRequestDTO();
		orderProductRequestDTO.setId("ff07fc0d-686d-4a39-9961-ec687c98ad47");
		orderProductRequestDTO.setName("Guaraná 2L");
		orderProductRequestDTO.setAmount(5);
		orderProductList.add(orderProductRequestDTO);
		orderDTO.setOrderedProducts(orderProductList);
		orderDTO.setAddress("test");
		
		Product product = new Product("ff07fc0d-686d-4a39-9961-ec687c98ad47", "Guaraná 2L", new BigDecimal("6.50"));
		
		given(productRepository.findById(anyString())).willReturn(Optional.of(product));
		
    	this.mockMvc.perform(post("/v1/orders")
				.content(objectMapper.writeValueAsString(orderDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
    }
    
    @Test
    void testGetOrderById() throws Exception {
    	this.mockMvc.perform(get("/v1/orders/ff07fc0d-686d-4a39-9961-ec687c98ad47"))
    			.andExpect(status().isNotImplemented());
    }
    
    @Test
    void testUpdateOrder() throws Exception {
    	OrderRequestDTO orderDTO = new OrderRequestDTO();
		List<OrderProductRequestDTO> orderProductList = new ArrayList<>();
		OrderProductRequestDTO orderProductRequestDTO = new OrderProductRequestDTO();
		orderProductRequestDTO.setId("ff07fc0d-686d-4a39-9961-ec687c98ad47");
		orderProductRequestDTO.setName("Guaraná 2L");
		orderProductRequestDTO.setAmount(5);
		orderProductList.add(orderProductRequestDTO);
		orderDTO.setOrderedProducts(orderProductList);
		orderDTO.setAddress("test");
    	this.mockMvc.perform(put("/v1/orders/ff07fc0d-686d-4a39-9961-ec687c98ad47")
				.content(objectMapper.writeValueAsString(orderDTO))
				.contentType(MediaType.APPLICATION_JSON))
    			.andExpect(status().isNotImplemented());
    }
    
    @Test
    void testDeleteOrder() throws Exception {
    	this.mockMvc.perform(delete("/v1/orders/ff07fc0d-686d-4a39-9961-ec687c98ad47"))
    			.andExpect(status().isNotImplemented());
    }
}
