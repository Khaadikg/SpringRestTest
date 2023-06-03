package peaksoft.springrestapi.dto;

import lombok.Getter;
import lombok.Setter;
import peaksoft.springrestapi.model.Company;

@Getter
@Setter
public class CourseRequest {
    private String courseName;
    private String durationMonth;
    private String companyName;

}
