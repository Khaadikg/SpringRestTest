package peaksoft.springrestapi.dto;

import lombok.Getter;
import lombok.Setter;
import peaksoft.springrestapi.model.StudyFormat;

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
