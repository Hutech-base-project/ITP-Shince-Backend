package com.itp_shince.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itp_shince.model.UserRole;
import com.itp_shince.repository.UserRoleRepository;


@Service
public class UserRoleService extends ServiceAbstract<UserRoleRepository,UserRole, Integer>{

	public List<UserRole> getAllByUserId(String id) {
		return repository.findAllByUserId(id);
	}

}
