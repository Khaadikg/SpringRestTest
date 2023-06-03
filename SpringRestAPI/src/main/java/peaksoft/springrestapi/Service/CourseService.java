package peaksoft.springrestapi.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import peaksoft.springrestapi.dto.CourseRequest;
import peaksoft.springrestapi.dto.CourseResponse;
import peaksoft.springrestapi.model.Company;
import peaksoft.springrestapi.model.Course;
import peaksoft.springrestapi.repository.CompanyRepo;
import peaksoft.springrestapi.repository.CourseRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {
    private final CourseRepo courseRepo;
    private final CompanyRepo companyRepo;

    public List<CourseResponse> getAllCourses() {
        List<CourseResponse> responses =  new ArrayList<>();
        for(Course course : courseRepo.findAll()) {
            responses.add(mapToResponse(course));
        }
        return responses;
    }
    public CourseResponse saveCourse(CourseRequest request) {
        return mapToResponse(courseRepo.save(mapToCourse(request)));
    }
    public String deleteCourse(Long courseId) {
        Course course = courseRepo.findById(courseId).get();
        courseRepo.deleteById(courseId);
        return "Successfully deleted course " + course.getCourseName() + " !";
    }
    public CourseResponse getCourseById(Long id) {
        Course course = new Course();
        try {
            course = courseRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            log.error("No such course found by id = " + id);
        }
        return mapToResponse(course);
    }
    public CourseResponse updateCourse(Long id, CourseRequest request) {
        Course course = courseRepo.findById(id).get();
        course.setCourseName(request.getCourseName());
        course.setDurationMonth(request.getDurationMonth());
        course.setCompany(companyRepo.getCompanyByCompanyName(request.getCompanyName()));
        courseRepo.save(course);
        return mapToResponse(course);
    }
    public Course mapToCourse(CourseRequest request) {
        Course course = new Course();
        course.setCourseName(request.getCourseName());
        course.setDurationMonth(request.getDurationMonth());
//        course.setCompanyId(companyRepo.getCompanyByCompanyName(request.getCompanyName()).getId());
        course.setCompany(companyRepo.getCompanyByCompanyName(request.getCompanyName()));
        return course;
    }
    public CourseResponse mapToResponse(Course course){
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setCourseName(course.getCourseName());
//        response.setTeacher(course.getTeacher().getFirstName() + course.getTeacher().getFirstName());
//        response.setCompany(course.getCompany().getCompanyName());
        response.setDurationMonth(course.getDurationMonth());
        return response;
    }
}
