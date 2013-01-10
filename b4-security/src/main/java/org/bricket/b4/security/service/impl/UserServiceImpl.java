/**
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bricket.b4.security.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.bricket.b4.core.service.B4ServiceException;
import org.bricket.b4.core.service.B4ServiceImpl;
import org.bricket.b4.security.entity.Role;
import org.bricket.b4.security.entity.User;
import org.bricket.b4.security.repository.RoleRepository;
import org.bricket.b4.security.repository.UserRepository;
import org.bricket.b4.security.service.RoleService;
import org.bricket.b4.security.service.RoleService.Roles;
import org.bricket.b4.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "userService")
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl extends B4ServiceImpl implements UserService,
		UserDetailsService {
	@Resource
	UserRepository userRepository;

	@Resource
	RoleRepository roleRepository;

	@Autowired
	RoleService roleService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	protected void onInit() throws B4ServiceException {
		roleService.init();

		if (userRepository.count() == 0) {
			Role adminRole = roleRepository.findByName(Roles.ROLE_ADMIN.name());
			Role userRole = roleRepository.findByName(Roles.ROLE_USER.name());

			List<User> users = new ArrayList<User>();
			for (Users u : Users.values()) {
				User user = new User();
				user.setEmail(u.getEmail());
				user.setPassword(passwordEncoder.encode(u.getPassword()));

				switch (u) {
				case ADMIN:
					user.setRoles(new HashSet<Role>(Arrays.<Role> asList(
							adminRole, userRole)));
					break;
				case USER:
					user.setRoles(new HashSet<Role>(Arrays
							.<Role> asList(userRole)));
					break;
				}

				users.add(user);
			}
			Iterable<User> result = userRepository.save(users);
			log.info("created auto generated users: " + result);
		}
		log.info("user service initialized");
	}

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("no user for email: " + email);
		}
		UserDetailsImpl userDetails = new UserDetailsImpl(user);
		return userDetails;
	}
}
