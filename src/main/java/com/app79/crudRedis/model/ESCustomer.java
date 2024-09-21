package com.app79.crudRedis.model;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "customer")
public class ESCustomer {
   private Long id;
   private String name;
   private Integer age;
}
