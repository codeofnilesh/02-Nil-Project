package in.nilesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nilesh.entities.Country;

public interface CountryRepo extends JpaRepository<Country, Integer> {

}
