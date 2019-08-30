package com.bytatech.ayoos.repository.search;

import com.bytatech.ayoos.domain.AddressLine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AddressLine entity.
 */
public interface AddressLineSearchRepository extends ElasticsearchRepository<AddressLine, Long> {
}
