package peaksoft.springrestapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import peaksoft.springrestapi.Service.CompanyService;
import peaksoft.springrestapi.dto.CompanyRequest;
import peaksoft.springrestapi.dto.CompanyResponse;
import peaksoft.springrestapi.dto.CompanyResponseView;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/companies")
@Tag(name = "Company controller", description = "using CRUD upon companies")
public class CompanyController {
    private final CompanyService service;
    @GetMapping
    @Operation(summary = "Get all companies", description = "Only ADMIN!")
    public CompanyResponseView getAll(@RequestParam(name = "text", required = false) String text,
                                            @RequestParam int page,
                                            @RequestParam int size){
        return service.searchAndPagination(text, page, size);
    }
    @GetMapping("{id}")
    @Operation(summary = "Get company by id", description = "Only ADMIN!")
    public CompanyResponse getCompany(@PathVariable("id")Long id) {
        return service.getCompanyById(id);
    }
    @PostMapping
    @Operation(summary = "Add company", description = "Only ADMIN!")
    public CompanyResponse save(@RequestBody CompanyRequest companyRequest){
        return service.saveCompany(companyRequest);
    }
    @PutMapping("{id}")
    @Operation(summary = "Update company", description = "Only ADMIN!")
    public CompanyResponse update(@PathVariable("id")Long id, @RequestBody CompanyRequest companyRequest) {
        return service.updateCompany(id, companyRequest);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete company", description = "Only ADMIN!")
    public String delete(@PathVariable("id")Long id) {
        service.deleteCompany(id);
        return "{Good! Company deleted!}";
    }
}
