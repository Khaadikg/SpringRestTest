package peaksoft.springrestapi.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import peaksoft.springrestapi.dto.GroupRequest;
import peaksoft.springrestapi.dto.GroupResponse;
import peaksoft.springrestapi.model.Course;
import peaksoft.springrestapi.model.Group;
import peaksoft.springrestapi.model.User;
import peaksoft.springrestapi.repository.CourseRepo;
import peaksoft.springrestapi.repository.GroupRepo;
import peaksoft.springrestapi.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;

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
//        group.setCourseId(request.getCourseId());
        List<Course> courses = new ArrayList<>();
        courses.add(courseRepo.findCourseById(group.getCourseId()));
        group.setCourses(courses);
        // filling with student users
        List<User> students = userRepo.getUsersByGroup(groupRepo.findByGroupName(request.getGroupName()));
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
        courses.add(courseRepo.findCourseByCourseName(request.getCourseName()));
        group.setCourses(courses);
        List<User> students = userRepo.getUsersByGroup(groupRepo.findByGroupName(request.getGroupName()));
        group.setStudents(students);
        group.setCourseId(courseRepo.findCourseByCourseName(request.getCourseName()).getId());
        return group;
    }


}
