package es.proyecto.app.service;

import es.proyecto.app.entity.RolesEntity;
import es.proyecto.app.repository.RolesRepository;
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
@Service
@Transactional
public class RolesService {

//    private final RolesMapper mapper = RolesMapper.INSTANCE;
//
//    @Autowired
//    private RolesRepository repository;
//
//    /**
//     * Obtiene la lista de todos los roles de la base de datos.
//     * Solo los usuarios ADMIN pueden acceder a esta operación.
//     *
//     * @return Lista de roles como objetos RoleResponse.
//     */
//    public List<RoleResponse> getRoles() {
//        return mapper.toApiDomain(repository.findAll());
//    }
//
//    /**
//     * Actualiza el nombre de un rol por su ID.
//     *
//     * @param idRole   El ID del rol.
//     * @param roleName El nuevo nombre para el rol.
//     * @return HttpStatus indicando el resultado de la operación.
//     */
//    public HttpStatus putRoleById(Integer idRole, String roleName) {
//        Optional<RolesEntity> optRole = repository.findById(idRole);
//        if (optRole.isEmpty()) {
//            return HttpStatus.NOT_FOUND; // No se encuentra el rol
//        }
//
//        RolesEntity roleEntity = optRole.get();
//        roleEntity.setRole_name(roleName);
//        repository.save(roleEntity);
//        return HttpStatus.OK; // Se actualizó con éxito
//    }
//
//    /**
//     * Recupera un rol por su ID.
//     *
//     * @param idRole El ID del rol.
//     * @return El objeto RolesEntity si existe, null en caso contrario.
//     */
//    public RolesEntity getRoleById(Integer idRole) {
//        return repository.findById(idRole).orElse(null);
//    }
//
//    /**
//     * Crea un nuevo rol en la base de datos.
//     *
//     * @param roleResponse El objeto RoleResponse para crear un nuevo rol.
//     * @return HttpStatus indicando el resultado de la operación.
//     */
//    public HttpStatus postRole(RoleResponse roleResponse) {
//        RolesEntity entity = mapper.toEntity(roleResponse);
//        repository.save(entity);
//        return HttpStatus.CREATED; // Se crea correctamente
//    }
//
//    /**
//     * Elimina un rol por su ID si existe.
//     *
//     * @param id El ID del rol a eliminar.
//     * @return HttpStatus indicando el resultado de la operación.
//     */
//    public HttpStatus deleteRole(Integer id) {
//        Optional<RolesEntity> optRole = repository.findById(id);
//        if (optRole.isEmpty()) {
//            return HttpStatus.NOT_FOUND;
//        }
//        repository.deleteById(id);
//        return HttpStatus.NO_CONTENT;
//    }
}
