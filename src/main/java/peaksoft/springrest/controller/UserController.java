package peaksoft.springrest.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.springrest.dto.CompanyResponseView;
import peaksoft.springrest.dto.UserResponseView;
import peaksoft.springrest.service.UserService;
import peaksoft.springrest.dto.UserRequest;
import peaksoft.springrest.dto.UserResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
public class UserController {
    private final UserService service;
    @GetMapping("/teachers")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Get all teachers", description = "Only ADMIN!")
    public UserResponseView getAllTeachers(@RequestParam(name = "text", required = false) String text,
                                      @RequestParam int page,
                                      @RequestParam int size){
        return service.searchAndPaginationTeacher(text, page, size);
    }
    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Get all users! (ADMIN,INSTRUCTOR,STUDENT etc...)", description = "Only ADMIN!")
    public List<UserResponse> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/students")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    @Operation(summary = "Get all students", description = "Only ADMIN!")
    public UserResponseView getAllStudents(@RequestParam(name = "text", required = false) String text,
                                           @RequestParam int page,
                                           @RequestParam int size){
        return service.searchAndPaginationStudent(text, page, size);
    }
    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    @Operation(summary = "Get teacher/student by id", description = "Teacher can see only students!")
    public UserResponse getUserById(@PathVariable("id") Long userId) {
        return service.getUserById(userId);
    }
    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Add student", description = "Only ADMIN!")
    public UserResponse createStudent(@RequestBody UserRequest request) {
        return service.createStudent(request);
    }
    @PostMapping("/addTeacher")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Add teacher", description = "Only ADMIN!")
    public UserResponse createTeacher(@RequestBody UserRequest request) {
        return service.createTeacher(request);
    }
    @PostMapping("/addAdmin")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Add teacher", description = "Only ADMIN!")
    public UserResponse createAdmin(@RequestBody UserRequest request) {
        return service.createAdmin(request);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    @Operation(summary = "Update student", description = "Uses by ADMIN amd INSTRUCTOR")
    public UserResponse updateStudent(@PathVariable("id") Long userId, @RequestBody UserRequest request) {
        return service.updateStudent(userId, request);
    }
    @PutMapping("/super/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update teacher", description = "Only ADMIN!")
    public UserResponse updateTeacher(@PathVariable("id") Long userId, @RequestBody UserRequest request) {
        return service.updateTeacher(userId, request);
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Delete teacher", description = "Only ADMIN!")
    public String delete(@PathVariable("id") Long userId) {
        return service.delete(userId);
    }

}
