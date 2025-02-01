package com.example.order;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.example.order.entity.Product;
import com.example.order.filter.LoggingFilter;
import com.example.order.repository.ProductRepository;

import jakarta.annotation.PostConstruct;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
	
    @PostConstruct
    public void init() {
        // Setting Spring Boot default to UTC timezone for consistency across instances in different regions
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/v1/**")
                .build();
    }
	
    @Bean
    public HttpExchangeRepository httpExchangeRepository() {
        return new InMemoryHttpExchangeRepository();
    }
    
    @Bean
    public LoggingFilter customLoggingFilter() {
        return new LoggingFilter();
    }
    
	/*
	 * NOTE: Initializing Product DB with some data just for testing purposes.
	 * Assuming in a PROD like environment this info comes either from another product service or already populated.
	 * Also assuming external systems calling order service can only request existing products and they know the product id/name they are requesting.
	 */
    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
        return args -> {
        	if (repository.count() == 0) {
	            repository.save(new Product(UUID.randomUUID().toString(), "Guaraná 2L", new BigDecimal("6.50")));
	            repository.save(new Product(UUID.randomUUID().toString(), "Guaraná Lata", new BigDecimal("3.25")));
	            repository.save(new Product(UUID.randomUUID().toString(), "Pepsi 2L", new BigDecimal("8.50")));
	            repository.save(new Product(UUID.randomUUID().toString(), "Pepsi Lata", new BigDecimal("5")));
	            repository.save(new Product(UUID.randomUUID().toString(), "Cerveja Colorado APPIA", new BigDecimal("7.80")));
	            repository.save(new Product(UUID.randomUUID().toString(), "Cerveja Corona Long Neck", new BigDecimal("6.35")));
	            repository.save(new Product(UUID.randomUUID().toString(), "Brahma Chopp Lata", new BigDecimal("2.89")));
        	}
        };
    }
}
