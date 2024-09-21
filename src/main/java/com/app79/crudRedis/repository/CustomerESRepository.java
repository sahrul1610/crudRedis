package com.app79.crudRedis.repository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.app79.crudRedis.model.ESCustomer;

public interface CustomerESRepository extends ElasticsearchRepository<ESCustomer, Long> {
}