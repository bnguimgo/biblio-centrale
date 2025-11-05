package com.bnguimgo.biblio.biblocentrale.service;

import com.bnguimgo.biblio.biblocentrale.entity.Privilege;
import com.bnguimgo.biblio.biblocentrale.entity.Role;
import com.bnguimgo.biblio.biblocentrale.entity.User;
import com.bnguimgo.biblio.biblocentrale.repository.RoleRepository;
import com.bnguimgo.biblio.biblocentrale.repository.UserRepository;
import com.bnguimgo.biblio.biblocentrale.utils.PrivilegeEnum;
import com.bnguimgo.biblio.biblocentrale.utils.RoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class InitDataLoaderSetup implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    @Autowired
    private DataLoaderService dataLoaderService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup){
            log.info("already Setup datas ");
            return;
        }

        log.info("Initialize datas at setup ");
        Privilege readPrivilege = dataLoaderService.createPrivilegeIfNotFound(PrivilegeEnum.READ_PRIVILEGE.name());
        Privilege writePrivilege = dataLoaderService.createPrivilegeIfNotFound(PrivilegeEnum.WRITE_PRIVILEGE.name());
        Privilege deletPrivilege = dataLoaderService.createPrivilegeIfNotFound(PrivilegeEnum.DELETE_PRIVILEGE.name());

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege, deletPrivilege);
        Role adminRole = dataLoaderService.createRoleIfNotFound(RoleEnum.ROLE_ADMIN.name(), adminPrivileges);
        List<Privilege> managerPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        Role managerRole = dataLoaderService.createRoleIfNotFound(RoleEnum.ROLE_MANAGER.name(), managerPrivileges);
        Role userRole = dataLoaderService.createRoleIfNotFound(RoleEnum.ROLE_USER.name(), Collections.singletonList(readPrivilege));

        //Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        // Create Admin profil
        User admin = new User();
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        String adminEmail = "admin@biblio.com";
        admin.setEmail(adminEmail);
        admin.setCreatedDate(LocalDateTime.now());
        //admin.setRoles(Collections.singletonList(adminRole));
        admin.setRoles(Arrays.asList(adminRole, managerRole, userRole));
        admin.setActif(true);
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            log.info("Save admin " + admin);
            userRepository.save(admin);
        }

        // Create Admin profil
        User admin_2 = new User();
        admin_2.setFirstName("Bnguimgo");
        admin_2.setLastName("Bnguimgo");
        String adminEmail_2 = "bnguimgo@yahoo.fr";
        admin_2.setEmail(adminEmail_2);
        admin_2.setCreatedDate(LocalDateTime.now());
        //admin.setRoles(Collections.singletonList(adminRole));
        admin_2.setRoles(Arrays.asList(adminRole, managerRole, userRole));
        admin_2.setActif(true);
        if (userRepository.findByEmail(adminEmail_2).isEmpty()) {
            log.info("Save admin_2 " + admin_2);
            userRepository.save(admin_2);
        }

        // Create a Manager profil
        User manager = new User();
        manager.setFirstName("Manager");
        manager.setLastName("Manager");
        String managerEmail = "manager@biblio.com";
        manager.setEmail(managerEmail);
        manager.setCreatedDate(LocalDateTime.now());
        manager.setRoles(Arrays.asList(managerRole, userRole));
        manager.setActif(true);
        if (userRepository.findByEmail(managerEmail).isEmpty()) {
            log.info("Save manager " + manager);
            userRepository.save(manager);
        }

        // Create a User profil
        User user = new User();
        user.setFirstName("User");
        user.setLastName("User");
        String userEmail = "user@biblio.com";
        user.setEmail(userEmail);
        user.setCreatedDate(LocalDateTime.now());
        user.setRoles(Collections.singletonList(userRole));
        user.setActif(true);
        if (userRepository.findByEmail(userEmail).isEmpty()) {
            log.info("Save user " + user);
            userRepository.save(user);
        }

        log.info("Initialize datas Successfully ...");
        alreadySetup = true;
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
