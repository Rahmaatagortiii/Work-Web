package com.wellbeignatwork.backend.repository.Event;

import com.wellbeignatwork.backend.entity.Event.Subscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
}
