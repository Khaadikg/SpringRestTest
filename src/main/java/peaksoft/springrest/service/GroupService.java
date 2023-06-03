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

    public List<GroupResponse> getAllGroups() {
        List<GroupResponse> groupResponses = new ArrayList<>();
        for (Group group : groupRepo.findAll()) {
            groupResponses.add(mapToResponse(group));
        }
        return groupResponses;
    }
    public GroupResponse saveGroup(GroupRequest request) {
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
        Group group = groupRepo.findById(id).get();
        group.setGroupName(request.getGroupName());
        group.setDateStart(request.getDateStart());
        group.setDateFinish(request.getDateFinish());
        try {
            group.setCourseId(courseRepo.findCourseByCourseName(request.getCourseName()).getId());
        } catch (NullPointerException e) {
            log.error("Such course <" +request.getCourseName() +"> does not exist!");
        }
        List<Course> courses = new ArrayList<>();
        courses.add(courseRepo.findCourseByCourseName(request.getCourseName()));
        group.setCourses(courses);
        // filling with student users
        List<User> students = userRepo.getUsersByGroup(groupRepo.findByGroupName(request.getCourseName()));
        group.setStudents(students);
        return mapToResponse(groupRepo.save(group));
    }
    public GroupResponse mapToResponse(Group group) {
        GroupResponse response = new GroupResponse();
        response.setId(group.getId());
        response.setGroupName(group.getGroupName());
        response.setDateStart(group.getDateStart());
        response.setDateFinish(group.getDateFinish());
        try {
            response.setCourseName(courseRepo.findById(group.getCourseId()).get().getCourseName());
        } catch (Exception e) {
            log.error("Check for illegal argument! OR Your DB has no such course!");
        }
        return response;
    }
    public Group mapToGroup(GroupRequest request) {
        Group group = new Group();
        group.setGroupName(request.getGroupName());
        group.setDateStart(request.getDateStart());
        group.setDateFinish(request.getDateFinish());
        List<Course> courses = new ArrayList<>();
        group.setCourses(courses);
        try {
            courses.add(courseRepo.findCourseByCourseName(request.getCourseName()));
        } catch (NoSuchElementException e) {
            log.error("Such course by name " + request.getCourseName() + "does not exist!");
        }
        List<User> students = new ArrayList<>();
        try {
            students = userRepo.getUsersByGroup(groupRepo.findByGroupName(request.getGroupName()));
        } catch (NoSuchElementException e) {
            log.error("No such students found by group name = " + request.getCourseName());
        }
        group.setStudents(students);

        group.setCourseId(courseRepo.findCourseByCourseName(request.getCourseName()).getId());
        return group;
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
