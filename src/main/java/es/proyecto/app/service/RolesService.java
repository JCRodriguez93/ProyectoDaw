package es.proyecto.app.service;

import es.proyecto.app.entity.RolesEntity;
import es.proyecto.app.mapper.RolesMapper;
import es.proyecto.app.repository.RolesRepository;
import es.swagger.codegen.models.Role;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Servicio de Roles para la gestión de roles en el sistema.
 */
@Validated
@Transactional
@Service
public class RolesService {

    private final RolesMapper mapper = RolesMapper.INSTANCE;

    @Autowired
    private RolesRepository repository;

    public List<Role> getAllRoles() {
        return mapper.toApiDomain(repository.findAll());
    }
    public RolesEntity getRoleById(Integer id) {
        return repository.findById(id).orElse(null);
    }
    public void updateRole(RolesEntity rolesEntity) {
        repository.save(rolesEntity);
    }
}


