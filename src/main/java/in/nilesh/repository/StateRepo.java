package in.nilesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nilesh.entities.State;

public interface StateRepo extends JpaRepository<State, Integer> {

}
