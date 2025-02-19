package es.proyecto.app.service;

import es.proyecto.app.entity.RolesEntity;
import es.proyecto.app.entity.UsersEntity;
import es.proyecto.app.error.UsersException;
import es.proyecto.app.mapper.UsersMapper;
import es.proyecto.app.repository.RolesRepository;
import es.proyecto.app.repository.UsersRepository;
import es.swagger.codegen.models.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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
   private PasswordEncoder passwordEncoder;
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
     *
     * @param user Datos del usuario a crear.
     */
    public void createUser(User user) {

        UsersEntity entity = mapper.toEntity(user);
        entity.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(entity);
    }

    /**
     * Actualizar un usuario existente.
     * @param idUser ID del usuario a actualizar.
     * @param user Datos del usuario a actualizar.
     * @return Usuario actualizado o null si no se encuentra.
     */
    public HttpStatus updateUser(Integer idUser, User user) {
        Optional<RolesEntity> role = rolesRepository.findById(user.getRoleId());
        if (role.isEmpty()) {
            throw new UsersException("User role not found.");
        }

        Optional<UsersEntity> existingUser = repository.findById(idUser);
        if (existingUser.isEmpty()) {
            return HttpStatus.NOT_FOUND;
        }

        UsersEntity entityToUpdate = mapper.toEntity(user);
        entityToUpdate.setIdUser(idUser);

        // Encriptar la nueva contraseña solo si se ha proporcionado
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            entityToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            entityToUpdate.setPassword(existingUser.get().getPassword()); // Mantener la contraseña actual
        }

        repository.save(entityToUpdate);
        return HttpStatus.OK;
    }
    public boolean existsByEmail(String email) { return repository.existsByEmail(email); }
    public UsersEntity findByEmail(String email) { return repository.findByEmail(email); }

    /**
     * Eliminar un usuario por su ID.
     *
     * @param idUser ID del usuario a eliminar.
     */
    public void deleteUser(Integer idUser) {
        if (repository.existsById(idUser)) {
            repository.deleteById(idUser);
        }
    }
}
