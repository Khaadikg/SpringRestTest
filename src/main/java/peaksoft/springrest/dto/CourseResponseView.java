package peaksoft.springrest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseResponseView {
    private List<CourseResponse> courseResponses;
}
