package peaksoft.springrest.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.springrest.model.Company;
import peaksoft.springrest.model.Course;

import java.util.List;

@Repository
@Transactional
public interface CourseRepo extends JpaRepository<Course, Long> {

    @Query("select c from Course c where lower(c.courseName) like lower(:text) ")
    Course findCourseByCourseName(@Param("text") String name);
    Course findCourseById(Long id);
    @Query("select c from Course c " +
            "where lower(c.courseName) like lower(concat('%',:text,'%'))")
    List<Course> searchAndPagination(@Param("text") String text, Pageable pageable);
}
