package in.nilesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nilesh.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	public User findByEmail(String email);

	public User findByEmailAndPwd(String email, String pwd);

}
