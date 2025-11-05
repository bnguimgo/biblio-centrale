package com.bnguimgo.biblio.biblocentrale.service;

import com.bnguimgo.biblio.biblocentrale.entity.Privilege;
import com.bnguimgo.biblio.biblocentrale.entity.Role;
import com.bnguimgo.biblio.biblocentrale.repository.PrivilegeRepository;
import com.bnguimgo.biblio.biblocentrale.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Slf4j
@Service
public class DataLoaderService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            log.info("Save Privilege " + privilege);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    public Role createRoleIfNotFound( String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            log.info("Save role " + role);
            return roleRepository.save(role);
        }
        return role;
    }
}
