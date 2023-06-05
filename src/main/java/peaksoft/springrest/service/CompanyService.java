package peaksoft.springrest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import peaksoft.springrest.dto.CompanyRequest;
import peaksoft.springrest.dto.CompanyResponse;
import peaksoft.springrest.dto.CompanyResponseView;
import peaksoft.springrest.model.Company;
import peaksoft.springrest.repository.CompanyRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {
    private final CompanyRepo companyRepo;
    public CompanyResponse getCompanyById(Long id){
        Company company = new Company();
        try {
             company = companyRepo.findById(id).get();
        } catch (Exception e) {
            log.error("No such company found by id = " + id);
        }
        return mapToResponse(company);
    }

    public CompanyResponse saveCompany(CompanyRequest request){
        return mapToResponse(companyRepo.save(mapToCompany(request)));
    }
    public CompanyResponse updateCompany(Long id, CompanyRequest request){
        Company company = new Company();
        try {
            company = companyRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            log.error("No such company found by id = " + id);
        }
        company.setCompanyName(request.getCompanyName());
        company.setLocatedCountry(request.getLocatedCountry());
        company.setOwner(request.getOwner());
        return mapToResponse(companyRepo.save(company));
    }

    public String deleteCompany(Long companyId) {
        try {
            companyRepo.deleteById(companyId);
        } catch (Exception e) {
            log.error("No such company found by id = " + companyId);
        }
        return "Successfully deleted company!";
    }
    public void deleteCompany(Company company) {
        companyRepo.delete(company);
    }

    public Company mapToCompany(CompanyRequest request){
        // что нужно принять от пользователя
        return Company.builder()
                .companyName(request.getCompanyName())
                .locatedCountry(request.getLocatedCountry())
                .owner(request.getOwner())
                .date(LocalDate.now()).build();
    }
    public CompanyResponse mapToResponse(Company company){
        // что нужно показать пользователю
        return CompanyResponse.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .locatedCountry(company.getLocatedCountry())
                .owner(company.getOwner())
                .date(company.getDate()).build();
    }

    public List<CompanyResponse> view(List<Company> companies) {
        List<CompanyResponse> responses = new ArrayList<>();
        for (Company company : companies) {
            responses.add(mapToResponse(company));
        }
        return responses;
    }
    public CompanyResponseView searchAndPagination(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        CompanyResponseView companyResponseView = new CompanyResponseView();
        companyResponseView.setCompanyResponses(view(search(text, pageable)));
        return companyResponseView;
    }
    public List<Company> search(String text, Pageable pageable) {
        text = (text == null) ? "" : text;
        return companyRepo.searchAndPagination(text, pageable);
    }
}
