package peaksoft.springrest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import peaksoft.springrest.dto.*;
import peaksoft.springrest.model.Course;
import peaksoft.springrest.model.Group;
import peaksoft.springrest.model.User;
import peaksoft.springrest.repository.CourseRepo;
import peaksoft.springrest.repository.GroupRepo;
import peaksoft.springrest.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {
    private final GroupRepo groupRepo;
    private final CourseRepo courseRepo;
    private final UserRepo userRepo;
    public GroupResponse saveGroup(GroupRequest request)  {
        return mapToResponse(groupRepo.save(mapToGroup(request)));
    }
    public GroupResponse getGroupById(Long groupId) {
        return mapToResponse(groupRepo.findById(groupId).get());
    }
    public String deleteGroup(Long groupId) {
        Group group = groupRepo.findById(groupId).get();
        groupRepo.deleteById(groupId);
        return "Successfully deleted group " + group.getGroupName() + " !";
    }
    public GroupResponse updateGroup(Long id, GroupRequest request) {
        Group group = new Group();
        try {
            group = groupRepo.findById(id).get();
        } catch (NullPointerException e) {
            log.error("No such group found by id = " + id);
        }
        group.setGroupName(request.getGroupName());
        group.setDateStart(request.getDateStart());
        group.setDateFinish(request.getDateFinish());
        List<Course> courses = null;
        List<User> students = null;
        try {
            courses.add(courseRepo.findCourseByCourseName(request.getCourseName()));
        } catch (NoSuchElementException e) {
            log.error("Such course by name = " + request.getCourseName() + " does not exist!");
        }
        try {
            students = userRepo.getUsersByGroup(request.getGroupName());
        } catch (NoSuchElementException e) {
            log.error("Student: Group by name = " + request.getCourseName() + " does not exist!");
        }
        try {
            group.setCourseId(courseRepo.findCourseByCourseName(request.getCourseName()).getId());
        } catch (NullPointerException e) {
            log.error("Such course <" +request.getCourseName() +"> does not exist!");
        }
        // filling with courses
        courses.add(courseRepo.findCourseByCourseName(request.getCourseName()));
        group.setCourses(courses);
        // filling with student (users)
        group.setStudents(students);
        return mapToResponse(groupRepo.save(group));
    }
    public GroupResponse mapToResponse(Group group) {
        String courseName = null;
        try {
            courseName = courseRepo.findById(group.getCourseId()).get().getCourseName();
        } catch (Exception e) {
            log.error("Check for illegal argument! OR Your DB has no such course!");
        }
            return GroupResponse.builder()
                    .id(group.getId())
                    .groupName(group.getGroupName())
                    .dateStart(group.getDateStart())
                    .dateFinish(group.getDateFinish())
                    .courseName(courseName).build();
    }
    public Group mapToGroup(GroupRequest request) {
        List<Course> courses = new ArrayList<>();
        List<User> students = new ArrayList<>();
        try {
            courses.add(courseRepo.findCourseByCourseName(request.getCourseName()));
        } catch (NoSuchElementException e) {
            log.error("Such course by name " + request.getCourseName() + "does not exist!");
        }
        try {
            students = userRepo.getUsersByGroup(request.getGroupName());
        } catch (NoSuchElementException e) {
            log.error("No such students found by group name = " + request.getCourseName());
        }
        return Group.builder()
                .groupName(request.getGroupName())
                .dateStart(request.getDateStart())
                .dateFinish(request.getDateFinish())
                .courses(courses)
                .students(students)
                .courseId(courseRepo.findCourseByCourseName(request.getCourseName()).getId()).build();
    }

    public List<GroupResponse> view(List<Group> groups) {
        List<GroupResponse> responses = new ArrayList<>();
        for (Group group : groups) {
            responses.add(mapToResponse(group));
        }
        return responses;
    }
    public GroupResponseView searchAndPagination(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        GroupResponseView groupResponseView = new GroupResponseView();
        groupResponseView.setGroupResponses(view(search(text, pageable)));
        return groupResponseView;
    }
    public List<Group> search(String text, Pageable pageable) {
        text = (text == null) ? "" : text;
        return groupRepo.searchAndPagination(text, pageable);
    }
}
