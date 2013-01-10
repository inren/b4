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
package org.bricket.b4.security.controller;

import org.bricket.b4.core.controller.ResourceNotFoundException;
import org.bricket.b4.security.entity.Role;
import org.bricket.b4.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/roles")
public class RoleController {
	@Autowired
	private RoleRepository roleRepository;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Role> getRoles() {
		return roleRepository.findAll();
	}

	@RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
	@ResponseBody
	public Role getRole(@PathVariable("roleId") Role role) {
		if (role == null) {
			throw new ResourceNotFoundException();
		}
		return role;
	}
}