package com.wellbeignatwork.backend.repository.User;

import com.wellbeignatwork.backend.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);

    List<User> findUsersByMessagesIsNull();
    User findUserByDisplayNameAndPassword(String displayName, String password);
    List<User> findAll(Sort pointFidelite);

}
