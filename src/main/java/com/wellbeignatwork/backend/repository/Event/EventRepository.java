package com.wellbeignatwork.backend.repository.Event;

import com.wellbeignatwork.backend.entity.Event.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
@Query("select e from Event e where e.startDate between :startDate  and :endDate ")
    public List<Event> reminder(LocalDateTime startDate, LocalDateTime endDate);
/*@Query("SELECT COUNT(e) as n, e.eventTags as tag FROM Event e GROUP BY e.eventTags ")
    public List<ITagsCount> tags();*/

}
