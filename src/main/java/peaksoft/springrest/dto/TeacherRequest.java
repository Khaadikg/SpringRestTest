package peaksoft.springrest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    //    private Course course;
    private Long courseId;
}
