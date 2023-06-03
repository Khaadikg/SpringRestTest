package peaksoft.springrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.springrestapi.model.Course;

import java.util.List;

@Repository
@Transactional
public interface CourseRepo extends JpaRepository<Course, Long> {

    Course findCourseByCourseName(String name);
    Course findCourseById(Long id);
}
