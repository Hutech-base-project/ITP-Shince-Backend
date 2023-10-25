package com.itp_shince.service;

import org.springframework.stereotype.Service;

import com.itp_shince.model.Role;
import com.itp_shince.repository.RoleRepository;


@Service
public class RoleService extends ServiceAbstract<RoleRepository, Role, Integer>{	
	public Role getByName(String role) {
		return repository.getByRoleName(role);
	}

}
