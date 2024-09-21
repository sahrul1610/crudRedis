package com.app79.crudRedis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.app79.crudRedis.repository")
public class CrudRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudRedisApplication.class, args);
	}

}
