package peaksoft.springrestapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.springrestapi.model.Company;

import java.util.List;

@Repository
@Transactional
public interface CompanyRepo extends JpaRepository<Company, Long> {

    Company getCompanyByCompanyName(String name);
    @Query("select c from Company c " +
            "where lower(c.companyName) like lower(concat('%',:text,'%')) " +
            "or lower(c.locatedCountry) like lower(concat('%',:text,'%'))")
    List<Company> searchAndPagination(@Param("text") String text, Pageable pageable);
}
