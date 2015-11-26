package controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import entities.User;
import lombok.Getter;
import lombok.Setter;
import service.OperatorBean;

@ManagedBean
@SessionScoped
public class UsersController {

	@EJB
	private OperatorBean ob;

	private boolean sortAscending = true;

	@Getter
	@Setter
	private List<User> users;
	
	@Getter
	@Setter
	private String username;
	
	@Getter
	@Setter
	private String email;
	
	@Getter
	@Setter
	private String id;
	
	public void delete(long id) {
		ob.deleteUser(id);
		loadUsers();
	}

	public List<User> getUsersList() {
		if (users == null) {
			loadUsers();
		}
		return users;
	}

	public void loadUsers() {
		users = ob.listUsers();
	}
	
	public void resetFilters() {
		username = email = id = "";
		loadUsers();
	}
	
	public void filter() {
		loadUsers();
		if (!username.equals("")) {
			users = 
				users.stream()
				.filter(p -> p.getUsername().contains(username))
				.collect(Collectors.toList());
		}
		if (!email.equals("")) {
			users = 
					users.stream()
					.filter(p -> p.getUsername().contains(username))
					.collect(Collectors.toList());
		}
		if (!id.equals("")) {
			users = 
					users.stream()
					.filter(p -> p.getId().equals(Long.valueOf(id)))
					.collect(Collectors.toList());
		}
	}

	public String sortBy(String byWhat) {

		if (sortAscending) {
			Collections.sort(getUsersList(), new Comparator<User>() {

				@Override
				public int compare(User o1, User o2) {
					switch (byWhat) {
					case "username":
						return o1.getUsername().compareTo(o2.getUsername());
					case "email":
						return o1.getUsername().compareTo(o2.getUsername());
					case "id":
						return o1.getId().compareTo(o2.getId());
					default:
						return 0;
					}
				}

			});
		} else {
			Collections.sort(getUsersList(), new Comparator<User>() {

				@Override
				public int compare(User o1, User o2) {

					switch (byWhat) {
					case "username":
						return o2.getUsername().compareTo(o1.getUsername());
					case "email":
						return o2.getUsername().compareTo(o1.getUsername());
					case "id":
						return o2.getId().compareTo(o1.getId());
					default:
						return 0;
					}
				}

			});
		}

		sortAscending = !sortAscending;

		return null;
	}
}
