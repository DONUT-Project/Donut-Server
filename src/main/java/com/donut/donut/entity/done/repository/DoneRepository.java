package com.donut.donut.entity.done.repository;

import com.donut.donut.entity.done.Done;
import com.donut.donut.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoneRepository extends JpaRepository<Done, Long> {
    List<Done> findAllByUser(User user);
    Optional<Done> findByDoneIdAndUser(Long doneId, User user);
    void deleteByDoneId(Long doneId);
    Optional<Done> findByDoneId(Long doneId);
}
