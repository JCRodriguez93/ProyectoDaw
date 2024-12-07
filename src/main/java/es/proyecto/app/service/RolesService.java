package es.proyecto.app.service;

import es.proyecto.app.entity.RolesEntity;
import es.proyecto.app.mapper.RolesMapper;
import es.proyecto.app.repository.RolesRepository;
import es.swagger.codegen.models.RoleListResponse;
import es.swagger.codegen.models.RoleResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

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

    public List<RoleListResponse> getRoles() {
        return mapper.toApiDomainList(repository.findAll());
    }

    public HttpStatus putRoleById(Integer idRole, String roleName) {
        Optional<RolesEntity> optRole = repository.findById(idRole);
        if (optRole.isEmpty()) {
            return HttpStatus.NOT_FOUND;
        }

        RolesEntity roleEntity = optRole.get();
        roleEntity.setRoleName(roleName);
        repository.save(roleEntity);

        return HttpStatus.OK;
    }

    public HttpStatus postRole(RoleResponse roleResponse) {
        RolesEntity entity = mapper.toEntity(roleResponse);
        repository.save(entity);

        return HttpStatus.CREATED;
    }

    public HttpStatus deleteRole(Integer id) {
        Optional<RolesEntity> optRole = repository.findById(id);
        if (optRole.isEmpty()) {
            return HttpStatus.NOT_FOUND;
        }

        repository.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }
}


