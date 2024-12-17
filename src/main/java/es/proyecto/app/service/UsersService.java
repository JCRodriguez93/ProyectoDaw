package es.proyecto.app.service;

import es.proyecto.app.entity.UsersEntity;
import es.proyecto.app.mapper.UsersMapper;
import es.proyecto.app.repository.RolesRepository;
import es.proyecto.app.repository.UsersRepository;
import es.swagger.codegen.models.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

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
    @Autowired
    private RolesRepository rolesRepository;

    /**
     * Obtener todos los usuarios.
     * @return Lista de usuarios.
     */
    public List<User> getAllUsers() {
        return mapper.toApiDomain(repository.findAll());
    }

    /**
     * Obtener un usuario por su ID.
     *
     * @param idUser ID del usuario.
     * @return Entidad del usuario o null si no se encuentra.
     */
    public User getUserById(Integer idUser) {
        Optional<UsersEntity> optionalUsersEntity = repository.findById(Integer.valueOf(idUser));
        return optionalUsersEntity.map(mapper::toApiDomain).orElse(null);
    }

    /**
     * Crear un nuevo usuario.
     * @param user Datos del usuario a crear.
     * @return Usuario creado.
     */
    public HttpStatus createUser(User user) {
        UsersEntity entity = mapper.toEntity(user);
        repository.save(entity);
        return HttpStatus.CREATED;
    }



    /**
     * Actualizar un usuario existente.
     * @param idUser ID del usuario a actualizar.
     * @param user Datos del usuario a actualizar.
     * @return Usuario actualizado o null si no se encuentra.
     */
    public HttpStatus updateUser(Integer idUser, User user) {
        Optional<UsersEntity> existingUser = repository.findById(idUser);
        if(existingUser.isEmpty()){
            return HttpStatus.NOT_FOUND;
        }
        user.setIdUser(idUser);
        repository.save(mapper.toEntity(user));
        return HttpStatus.OK;
    }


    /**
     * Eliminar un usuario por su ID.
     * @param idUser ID del usuario a eliminar.
     * @return true si el usuario fue eliminado, false si no se encontró.
     */
    public boolean deleteUser(Integer idUser) {
        if (repository.existsById(idUser)) {
            repository.deleteById(idUser);
            return true;
        } else {
            return false;
        }
    }
}
