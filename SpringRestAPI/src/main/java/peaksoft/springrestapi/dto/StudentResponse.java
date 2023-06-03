package peaksoft.springrestapi.dto;

import lombok.Getter;
import lombok.Setter;
import peaksoft.springrestapi.model.StudyFormat;

@Getter
@Setter
public class StudentResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private StudyFormat studyFormat;
    private Long groupId;
}
