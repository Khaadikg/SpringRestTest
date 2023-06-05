package peaksoft.springrest.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.springrest.model.Company;
import peaksoft.springrest.model.Group;
import peaksoft.springrest.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    @Query("select u from User u where u.group.groupName = :groupName")
    List<User> getUsersByGroup(@Param("groupName") String groupName);
    @Query("select u from User u " +
            "where lower(concat(u.firstName, u.lastName)) like lower(concat('%',:text,'%')) and  u.role = 'INSTRUCTOR'")
    List<User> searchAndPaginationForTeachers(@Param("text") String text, Pageable pageable);

    @Query("select u from User u " +
            "where lower(concat(u.firstName, u.lastName)) like lower(concat('%',:text,'%')) and  u.role = 'STUDENT'")
    List<User> searchAndPaginationForStudents(@Param("text") String text, Pageable pageable);

}
