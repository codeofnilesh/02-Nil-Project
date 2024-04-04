package in.nilesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nilesh.entities.City;

public interface CityRepo extends JpaRepository<City, Integer> {

}
