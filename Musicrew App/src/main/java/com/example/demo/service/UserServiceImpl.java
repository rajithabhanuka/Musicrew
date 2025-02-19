package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (username != null) {
			User userEntity = userRepository.findByUsername(username);

			if (userEntity == null) {

				throw new UsernameNotFoundException(username);
			}
			return new org.springframework.security.core.userdetails.User(userEntity.getUsername(),
					userEntity.getPass(), getUserAuthority(userEntity));

		}
		return null;
	}

	private List<GrantedAuthority> getUserAuthority(User user) {
		Set<GrantedAuthority> roles = new HashSet<>();
		roles.add(new SimpleGrantedAuthority(user.getRole().getRole()));
		return new ArrayList<>(roles);
	}

}
