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
import org.bricket.b4.security.entity.Group;
import org.bricket.b4.security.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/groups")
public class GroupController {
	@Autowired
	private GroupRepository groupRepository;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Group> getGroups() {
		return groupRepository.findAll();
	}

	@RequestMapping(value = "/{groupId}", method = RequestMethod.GET)
	@ResponseBody
	public Group getGroup(@PathVariable("groupId") Group group) {
		if (group == null) {
			throw new ResourceNotFoundException();
		}
		return group;
	}
}