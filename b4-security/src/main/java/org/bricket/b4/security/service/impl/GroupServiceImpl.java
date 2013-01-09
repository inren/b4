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
import org.bricket.b4.security.entity.Group;
import org.bricket.b4.security.entity.Role;
import org.bricket.b4.security.entity.User;
import org.bricket.b4.security.repository.GroupRepository;
import org.bricket.b4.security.repository.RoleRepository;
import org.bricket.b4.security.repository.UserRepository;
import org.bricket.b4.security.service.GroupService;
import org.bricket.b4.security.service.RoleService;
import org.bricket.b4.security.service.RoleService.Roles;
import org.bricket.b4.security.service.UserService;
import org.bricket.b4.security.service.UserService.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "groupService")
@Transactional(readOnly = true)
@Slf4j
public class GroupServiceImpl extends B4ServiceImpl implements GroupService {

	@Resource
	private GroupRepository groupRepository;

	@Resource
	private UserRepository userRepository;

	@Resource
	private RoleRepository roleRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Transactional
	@Override
	protected final void onInit() throws B4ServiceException {
		userService.init();
		roleService.init();

		if (groupRepository.count() == 0) {
			User admin = userRepository.findByEmail(Users.ADMIN.getEmail());
			User user = userRepository.findByEmail(Users.USER.getEmail());

			Role adminRole = roleRepository.findByName(Roles.ROLE_ADMIN.name());
			Role userRole = roleRepository.findByName(Roles.ROLE_USER.name());

			List<Group> groups = new ArrayList<Group>();
			for (Groups group : Groups.values()) {
				Group g = new Group();
				g.setName(group.getName());
				switch (group) {
				case GROUP_ADMIN:
					g.setRoles(new HashSet<Role>(Arrays
							.<Role> asList(adminRole)));
					g.setUsers(new HashSet<User>(Arrays.<User> asList(admin)));
					break;
				case GROUP_USER:
					g.setRoles(new HashSet<Role>(Arrays.<Role> asList(userRole)));
					g.setUsers(new HashSet<User>(Arrays.<User> asList(admin,
							user)));
					break;
				}
				groups.add(g);
			}
			Iterable<Group> result = groupRepository.save(groups);
			log.info("created auto generated groups: " + result);
		}
		log.info("group service initialized");
	}
}
