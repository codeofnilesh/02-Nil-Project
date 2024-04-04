package in.nilesh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nilesh.entities.City;

public interface CityRepo extends JpaRepository<City, Integer> {

	public List<City> findAllByStateId(Integer stateId);

}
