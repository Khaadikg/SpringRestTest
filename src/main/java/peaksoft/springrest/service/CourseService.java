package peaksoft.springrest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import peaksoft.springrest.dto.*;
import peaksoft.springrest.model.Company;
import peaksoft.springrest.model.Course;
import peaksoft.springrest.repository.CompanyRepo;
import peaksoft.springrest.repository.CourseRepo;

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
        Company company = new Company();
        try {
            company = companyRepo.getCompanyByCompanyName(request.getCompanyName());
        } catch (NoSuchElementException e) {
            log.error("Course: No such company = " + request.getCompanyName());
        }
        return Course.builder()
                .courseName(request.getCourseName())
                .durationMonth(request.getDurationMonth())
                .company(company)
                .companyId(company.getId()).build();
    }
    public CourseResponse mapToResponse(Course course){
        return CourseResponse.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .durationMonth(course.getDurationMonth()).build();
    }
    public List<CourseResponse> view(List<Course> courses) {
        List<CourseResponse> responses = new ArrayList<>();
        for (Course course : courses) {
            responses.add(mapToResponse(course));
        }
        return responses;
    }
    public CourseResponseView searchAndPagination(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        CourseResponseView courseResponseView = new CourseResponseView();
        courseResponseView.setCourseResponses(view(search(text, pageable)));
        return courseResponseView;
    }
    public List<Course> search(String text, Pageable pageable) {
        text = (text == null) ? "" : text;
        return courseRepo.searchAndPagination(text, pageable);
    }
}
