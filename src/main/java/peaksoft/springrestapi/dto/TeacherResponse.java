package peaksoft.springrestapi.dto;

import lombok.Getter;
import lombok.Setter;
import peaksoft.springrestapi.model.Course;
@Getter
@Setter
public class TeacherResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
//    private Course course;
    private Long courseId;
}
