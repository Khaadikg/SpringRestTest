package peaksoft.springrest.dto;

import lombok.Getter;
import lombok.Setter;
import peaksoft.springrest.model.StudyFormat;

@Getter
@Setter
public class StudentRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private StudyFormat studyFormat;
    private String groupName;
}
