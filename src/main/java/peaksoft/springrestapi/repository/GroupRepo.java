package peaksoft.springrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.springrestapi.model.Course;
import peaksoft.springrestapi.model.Group;

import java.util.List;

@Repository
@Transactional
public interface GroupRepo extends JpaRepository<Group, Long> {

    Group findByGroupName(String name);
    Group findGroupById(Long id);
}
