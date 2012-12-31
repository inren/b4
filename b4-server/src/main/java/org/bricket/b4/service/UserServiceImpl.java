package org.bricket.b4.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.bricket.b4.domain.User;
import org.bricket.b4.repository.UserRepository;
import org.bricket.b4.service.common.B4ServiceException;
import org.bricket.b4.service.common.B4ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl extends B4ServiceImpl implements UserService {
    private static final Logger log = LoggerFactory
	    .getLogger(UserServiceImpl.class);

    @Resource
    UserRepository userRepository;

    @Override
    @Transactional
    protected void onInit() throws B4ServiceException {
	if (userRepository.count() == 0) {
	    List<User> users = new ArrayList<User>();
	    for (Users u : Users.values()) {
		User user = new User();
		user.setEmail(u.getEmail());
		user.setPassword(u.getPassword());
		users.add(user);
	    }
	    Iterable<User> result = userRepository.save(users);
	    log.info("created auto generated users: " + result);
	}
	log.info("user service initialized");
    }
}
