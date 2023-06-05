package peaksoft.springrest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CourseResponse {
    private Long id;
    private String courseName;
    private String durationMonth;
//    private String company;
//    private String teacher;

}
