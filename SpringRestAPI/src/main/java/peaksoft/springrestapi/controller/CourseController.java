package peaksoft.springrestapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.springrestapi.Service.CourseService;
import peaksoft.springrestapi.dto.CourseRequest;
import peaksoft.springrestapi.dto.CourseResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
@PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
@Tag(name = "Course controller", description = "Using CRUD upon courses")
public class CourseController {
    private final CourseService service;
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    @Operation(summary = "Get all courses", description = "Uses by ADMIN and INSTRUCTOR")
    public List<CourseResponse> getAllCourses() {
        return service.getAllCourses();
    }
    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    @Operation(summary = "Get course by id", description = "Uses by ADMIN and INSTRUCTOR")
    public CourseResponse getCourse(@PathVariable("id")Long id) {
        return service.getCourseById(id);
    }
    @PostMapping
    @Operation(summary = "Add course", description = "Only ADMIN!")
    public CourseResponse save(@RequestBody CourseRequest courseRequest){
        return service.saveCourse(courseRequest);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    @Operation(summary = "Update course", description = "Uses by ADMIN and INSTRUCTOR")
    public CourseResponse update(@PathVariable("id")Long id, @RequestBody CourseRequest courseRequest) {
        return service.updateCourse(id, courseRequest);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete course", description = "Only ADMIN!")
    public String delete(@PathVariable("id")Long id) {
        service.deleteCourse(id);
        return "{Course has been deleted!}";
    }
}
