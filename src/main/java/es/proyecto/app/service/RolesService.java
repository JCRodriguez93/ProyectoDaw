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

    /**
     * Obtiene todos los roles registrados.
     *
     * @return lista de roles convertidos a modelo API.
     */
    public List<Role> getAllRoles() {
        return mapper.toApiDomain(repository.findAll());
    }

    /**
     * Obtiene un rol por su identificador.
     *
     * @param id identificador del rol.
     * @return entidad {@link RolesEntity} si existe, o {@code null} si no se encuentra.
     */
    public RolesEntity getRoleById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Actualiza un rol existente o lo guarda si no existe.
     *
     * @param rolesEntity entidad {@link RolesEntity} con los datos del rol a actualizar o crear.
     */
    public void updateRole(RolesEntity rolesEntity) {
        repository.save(rolesEntity);
    }
}


