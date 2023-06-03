package peaksoft.springrest.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.springrest.model.Course;
import peaksoft.springrest.model.Group;

import java.util.List;

@Repository
@Transactional
public interface GroupRepo extends JpaRepository<Group, Long> {

    Group findByGroupName(String name);
    Group findGroupById(Long id);
    @Query("select g from Group g " +
            "where lower(g.groupName) like lower(concat('%',:text,'%'))")
    List<Group> searchAndPagination(@Param("text") String text, Pageable pageable);
}
