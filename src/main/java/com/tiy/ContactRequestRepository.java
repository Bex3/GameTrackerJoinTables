package com.tiy;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactRequestRepository extends CrudRepository<ContactRequest, Integer> {
    List<ContactRequest> findByRequestingUser(User requestingUser);
    List<ContactRequest> findByTargetUser(User targetUser);
    List<ContactRequest> findByRequestingUserAndTargetUser(User requestingUser, User targetUser);
}
