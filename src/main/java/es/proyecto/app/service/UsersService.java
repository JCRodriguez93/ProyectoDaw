package es.proyecto.app.service;

import es.proyecto.app.entity.RolesEntity;
import es.proyecto.app.entity.UsersEntity;
import es.proyecto.app.mapper.RolesMapper;
import es.proyecto.app.mapper.UsersMapper;
import es.proyecto.app.repository.RolesRepository;
import es.proyecto.app.repository.UsersRepository;
import es.swagger.codegen.models.Role;
import es.swagger.codegen.models.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Servicio de Users para la gestión de usuarios en el sistema.
 */
@Validated
@Transactional
@Service
public class UsersService {

    private final UsersMapper mapper = UsersMapper.INSTANCE;

    @Autowired
    private UsersRepository repository;

    public List<User> getAllUsers() {
        return mapper.toApiDomain(repository.findAll());
    }


    public UsersEntity getRoleById(Integer id) {

        return repository.findById(id).orElse(null);
    }
    public void updateUser(UsersEntity usersEntity) {
        repository.save(usersEntity);
    }
}


