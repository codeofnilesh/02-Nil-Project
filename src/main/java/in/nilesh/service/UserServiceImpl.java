package in.nilesh.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import in.nilesh.entities.City;
import in.nilesh.entities.Country;
import in.nilesh.entities.State;
import in.nilesh.entities.User;
import in.nilesh.repository.CityRepo;
import in.nilesh.repository.CountryRepo;
import in.nilesh.repository.StateRepo;
import in.nilesh.repository.UserRepo;

public class UserServiceImpl implements UserService {

	@Autowired
	private CountryRepo coRepo;

	@Autowired
	private StateRepo stRepo;

	@Autowired
	private CityRepo ciRepo;
	
	@Autowired
	private UserRepo usRepo;

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

}