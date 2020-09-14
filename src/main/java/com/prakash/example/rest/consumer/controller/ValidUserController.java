/**
 * 
 */
package com.prakash.example.rest.consumer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.prakash.example.rest.consumer.model.User;

import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;

/**
 * @author Prakash
 *
 */
@RestController
@RequestMapping("/api/prakash")
public class ValidUserController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/validusers")
	public List<User> getValidUsers() {
		// invoke DWP rest api to get users living in London
		ResponseEntity<List<User>> londonUsersResponse = restTemplate.exchange(
				"https://bpdts-test-app.herokuapp.com/city/London/users", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<User>>() {
				});
		List<User> londonUsers = londonUsersResponse.getBody();

		// invoke DWP rest api to get all users
		ResponseEntity<List<User>> allUsersResponse = restTemplate.exchange(
				"https://bpdts-test-app.herokuapp.com/users", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<User>>() {
				});
		List<User> allUsers = allUsersResponse.getBody();

		List<User> validUsers = findUsersEitherInLondonOrRadiuswithin50Miles(londonUsers, allUsers);
		return validUsers;
	}

	/**
	 * This method returns list of users who are listed as either living in
	 * London or whose current coordinates are within 50 miles of London
	 * 
	 * @param londonUsers
	 *            the list containing london users
	 * @param allUsers
	 *            the list containing all the users
	 * @return the list with valid users
	 */
	public static List<User> findUsersEitherInLondonOrRadiuswithin50Miles(List<User> londonUsers, List<User> allUsers) {
		List<User> validUsers = new ArrayList<>();
		// 51 deg 30 min 26 sec N
		double londonLat = 51 + (30 / 60.0) + (26 / 60.0 / 60.0);
		// 0 deg 7 min 39 sec W
		double londonLon = 0 - (7 / 60.0) - (39 / 60.0 / 60.0);

		for (User user : allUsers) {

			Double latitude = user.getLatitude();
			Double longitude = user.getLongitude();

			GeodesicData result = Geodesic.WGS84.Inverse(londonLat, londonLon, latitude, longitude);

			double distanceInMeters = result.s12;
			double distanceInMiles = distanceInMeters / 1609.34;

			if (distanceInMiles <= 50 || londonUsers.contains(user)) {
				validUsers.add(user);
			}
		}
		return validUsers;
	}

}
