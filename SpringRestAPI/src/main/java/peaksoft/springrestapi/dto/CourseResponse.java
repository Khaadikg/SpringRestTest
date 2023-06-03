package peaksoft.springrestapi.dto;

import lombok.Getter;
import lombok.Setter;
import peaksoft.springrestapi.model.Company;
import peaksoft.springrestapi.model.User;

@Getter
@Setter
public class CourseResponse {
    private Long id;
    private String courseName;
    private String durationMonth;
//    private String company;
//    private String teacher;

}
