package com.repo;

import com.entity.EventType;
import org.springframework.data.repository.CrudRepository;

public interface EventTypeRepo extends CrudRepository<EventType,Integer> {
}
