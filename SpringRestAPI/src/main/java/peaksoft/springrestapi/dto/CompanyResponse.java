package peaksoft.springrestapi.dto;

import lombok.Getter;
import lombok.Setter;
import peaksoft.springrestapi.model.Course;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CompanyResponse {
    private Long id;
    private String companyName;
    private String locatedCountry;
    private String owner;
    private LocalDate date;
//    private List<Course> courses; // нужно ли нам показывать эти данные?
}
