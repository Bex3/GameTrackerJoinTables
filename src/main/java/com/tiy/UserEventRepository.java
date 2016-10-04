package com.tiy;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserEventRepository extends CrudRepository<UserEvent, Integer> {
    List<UserEvent> findByUser(User user);
    List<UserEvent> findByUserAndEvent(User user, Event event);
}
