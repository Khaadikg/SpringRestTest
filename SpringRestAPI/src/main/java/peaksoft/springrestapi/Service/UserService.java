package peaksoft.springrestapi.Service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import peaksoft.springrestapi.dto.StudentRequest;
import peaksoft.springrestapi.dto.TeacherRequest;
import peaksoft.springrestapi.dto.UserRequest;
import peaksoft.springrestapi.dto.UserResponse;
import peaksoft.springrestapi.model.Group;
import peaksoft.springrestapi.model.Role;
import peaksoft.springrestapi.model.User;
import peaksoft.springrestapi.repository.CourseRepo;
import peaksoft.springrestapi.repository.GroupRepo;
import peaksoft.springrestapi.repository.UserRepo;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    @Autowired
    private final UserRepo userRepository;
    @Autowired
    private final GroupRepo groupRepo;
    @Autowired
    private final CourseRepo courseRepo;

    private final BCryptPasswordEncoder encoder;

    public UserResponse createStudent(UserRequest request) {
        User user = mapToUser(request);
        user.setRole(Role.STUDENT);
        try {
            user.setGroup(groupRepo.findById(request.getGroupId()).get());
        } catch (Exception e) {
            log.error("Student: No such group found by id = " + request.getGroupId() + " !");
        }
        return mapToResponse(userRepository.save(user));
    }

    public UserResponse createTeacher(UserRequest request) {
        User user = mapToUser(request);
        user.setRole(Role.INSTRUCTOR);
        try {
            user.setCourse(courseRepo.findCourseById(request.getCourseId()));
        } catch (Exception e) {
            log.error("Teacher: No such course found by id = " + request.getCourseId() + " !");
        }
        return mapToResponse(userRepository.save(user));
    }
    public UserResponse createAdmin(UserRequest request) {
        User user = mapToUser(request);
        user.setRole(Role.ADMIN);
        return mapToResponse(userRepository.save(user));
    }
    public List<UserResponse> getAllStudents() {
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            if (user.getRole() == Role.STUDENT) userResponses.add(mapToResponse(user));
        }
        return userResponses;
    }
    public List<UserResponse> getAllUsers() {
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userResponses.add(mapToResponse(user));
        }
        return userResponses;
    }

    public List<UserResponse> getAllTeachers() {
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            if (user.getRole() == Role.INSTRUCTOR) userResponses.add(mapToResponse(user));
        }
        return userResponses;
    }

    public UserResponse getUserById(Long id) {
        User user = new User();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) auth.getPrincipal();
        try {
            user = userRepository.findById(id).get();
            if (userDetails.getRole() == Role.INSTRUCTOR && user.getRole()
                    == Role.INSTRUCTOR || user.getRole() == Role.ADMIN) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            log.error("No such user found by id = " + id + "!");
        }
        return (mapToResponse(user));
    }

    public UserResponse updateStudent(Long id, UserRequest request) {
        User user = new User();
        try {
            user = userRepository.findById(id).get();
            if (user.getRole() != Role.STUDENT) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            log.error("No such user found by id = " + id + "!");
        }
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRoleName()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        try {
            user.setGroup(groupRepo.findGroupById(request.getGroupId()));
        } catch (NoSuchElementException e) {
            log.error("Student: No such group found by id = " + id);
        }

        return mapToResponse(userRepository.save(user));
    }
    public UserResponse updateTeacher(Long id, UserRequest request) {
        User user = new User();
        try {
            user = userRepository.findById(id).get();
            if (user.getRole() != Role.INSTRUCTOR) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            log.error("No such user found by id = " + id + "!");
        }
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        try {
            user.setRole(Role.valueOf(request.getRoleName()));
        } catch (NullPointerException e) {
            log.error("Request does not have given ROLE!");
        }
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        try {
            user.setCourse(courseRepo.findCourseById(request.getCourseId()));
        } catch (NoSuchElementException e) {
            log.error("Teacher: No such teacher found by id = " + id);
        }

        return mapToResponse(userRepository.save(user));
    }

    public String delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            log.error("User with id = " + id + " not found!");
        }
        return "Successfully deleted user!";
    }

    /////////////////////////
    /// BLOCK OF MAPPINGS ///
    /////////////////////////
    public UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .roleName(String.valueOf(user.getRole()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .localDate(user.getCreatedDate()).build();
    }
    public User mapToUser(UserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .createdDate(LocalDate.now())
                .email(request.getEmail()).build();
    }

}

