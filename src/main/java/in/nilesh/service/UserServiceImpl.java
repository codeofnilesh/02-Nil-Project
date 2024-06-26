package in.nilesh.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.nilesh.binding.LoginForm;
import in.nilesh.binding.RegisterForm;
import in.nilesh.binding.ResetPwdForm;
import in.nilesh.entities.City;
import in.nilesh.entities.Country;
import in.nilesh.entities.State;
import in.nilesh.entities.User;
import in.nilesh.repository.CityRepo;
import in.nilesh.repository.CountryRepo;
import in.nilesh.repository.StateRepo;
import in.nilesh.repository.UserRepo;
import in.nilesh.utils.EmailUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private CountryRepo coRepo;

	@Autowired
	private StateRepo stRepo;

	@Autowired
	private CityRepo ciRepo;

	@Autowired
	private UserRepo usRepo;

	@Autowired
	private EmailUtils emailUtils;

	Random random = new Random();

	@Override
	public Map<Integer, String> getCountries() {
		List<Country> findAll = coRepo.findAll();

		Map<Integer, String> countries = new HashMap<>();

		findAll.forEach(n -> countries.put(n.getCountryId(), n.getCountryName()));
		return countries;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		Map<Integer, String> states = new HashMap<>();

		List<State> findAll = stRepo.findByCountryId(countryId);
		findAll.forEach(s -> states.put(s.getStateId(), s.getStateName()));
		return states;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		Map<Integer, String> cities = new HashMap<>();

		List<City> byStateId = ciRepo.findAllByStateId(stateId);
		byStateId.forEach(k -> cities.put(k.getCityId(), k.getCityName()));

		return cities;
	}

	@Override
	public User getUser(String email) {
		return usRepo.findByEmail(email);
	}

	@Override
	public boolean saveUser(RegisterForm formObj) {

		formObj.setPwd(generateRandomPwd());
		formObj.setPwdUpdated("NO");
		User user = new User();
		BeanUtils.copyProperties(formObj, user);
		usRepo.save(user);
		String subject = "Your account created - Ashok IT";
		String body = "Your Pwd : " + formObj.getPwd();

		return emailUtils.mailSend(subject, body, formObj.getEmail());
	}

	private String generateRandomPwd() {
		String alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuv";

		StringBuilder randomString = new StringBuilder(5);
		for (int i = 0; i < 5; i++) {
			int randomIndex = random.nextInt(alphanumericCharacters.length() - 1);
			char randomChar = alphanumericCharacters.charAt(randomIndex);
			randomString.append(randomChar);
		}

		return randomString.toString();
	}

	@Override
	public User login(LoginForm formObj) {
		return usRepo.findByEmailAndPwd(formObj.getEmail(), formObj.getPwd());
	}

	@Override
	public boolean resetPwd(ResetPwdForm formObj) {
		Optional<User> findById = usRepo.findById(formObj.getUserId());
		if (findById.isPresent()) {
			User user = findById.get();
			user.setPwd(formObj.getNewPwd());
			user.setPwdUpdated("YES");
			usRepo.save(user);
			return true;
		}
		return false;
	}

}
