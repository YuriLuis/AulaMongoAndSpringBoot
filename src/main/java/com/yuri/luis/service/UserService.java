package com.yuri.luis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuri.luis.dto.UserDTO;
import com.yuri.luis.model.User;
import com.yuri.luis.repository.UserRepository;
import com.yuri.luis.service.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findAll() { // OK
		return userRepository.findAll();
	}

	public User findById(String id) { //OK
		Optional<User> obj = userRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public User insert(User obj) {//OK
		return userRepository.save(obj);
	}

	public void delete(String id) {//OK
		findById(id);
		userRepository.deleteById(id);
	}
	
	public User update(User obj) { //OK
		User newObj = findById(obj.getId());
		updateData(newObj, obj);
		return userRepository.save(newObj);
	}

	private void updateData(User newObj, User obj) { //OK
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}

	public User fromDTOToObject(UserDTO objDto) { // OK
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail(), objDto.getPassword());
	}
}
