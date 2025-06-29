package es.proyecto.app.service;

import es.proyecto.app.entity.RolesEntity;
import es.proyecto.app.entity.UsersEntity;
import es.proyecto.app.error.ProductException;
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
     * Obtiene todos los usuarios registrados.
     *
     * @return lista de usuarios en formato API.
     */
    public List<User> getAllUsers() {
        return mapper.toApiDomain(repository.findAll());
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param idUser identificador del usuario.
     * @return usuario en formato API o {@code null} si no se encuentra.
     */
    public User getUserById(Integer idUser) {
        Optional<UsersEntity> optionalUsersEntity = repository.findById(Integer.valueOf(idUser));
        return optionalUsersEntity.map(mapper::toApiDomain).orElse(null);
    }


    /**
     * Crea un nuevo usuario.
     * Valida y encripta la contraseña antes de guardarlo.
     *
     * @param user objeto usuario con los datos a crear.
     * @throws UsersException si la contraseña no cumple con el formato requerido.
     */
    public void createUser(User user) {
        validatePassword(user.getPassword()); // Validar antes de encriptar

        UsersEntity entity = mapper.toEntity(user);
        entity.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(entity);
    }

    /**
     * Actualiza un usuario existente.
     * Valida el rol, actualiza la contraseña solo si se proporciona,
     * y mantiene la contraseña actual si no se modifica.
     *
     * @param idUser identificador del usuario a actualizar.
     * @param user   objeto usuario con los nuevos datos.
     * @return {@link HttpStatus#OK} si se actualizó correctamente,
     *         {@link HttpStatus#NOT_FOUND} si el usuario no existe.
     * @throws UsersException si el rol especificado no existe o la contraseña es inválida.
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

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            validatePassword(user.getPassword()); // Validar antes de encriptar
            entityToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            entityToUpdate.setPassword(existingUser.get().getPassword()); // Mantener la contraseña actual
        }

        repository.save(entityToUpdate);
        return HttpStatus.OK;
    }

    /**
     * Verifica si un email ya está registrado en el sistema.
     *
     * @param email correo electrónico a verificar.
     * @return {@code true} si el email existe, {@code false} en caso contrario.
     */
    public boolean existsByEmail(String email) { return repository.existsByEmail(email); }

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email correo electrónico del usuario.
     * @return entidad {@link UsersEntity} si existe, o {@code null} si no.
     */
    public UsersEntity findByEmail(String email) { return repository.findByEmail(email); }

    /**
     * Elimina un usuario por su ID.
     *
     * @param idUser identificador del usuario a eliminar.
     */
    public void deleteUser(Integer idUser) {
        if (repository.existsById(idUser)) {
            repository.deleteById(idUser);
        }
    }

    /**
     * Obtiene un usuario por su correo electrónico.
     *
     * @param email correo electrónico del usuario.
     * @return entidad {@link UsersEntity} si existe, o {@code null} si no.
     */
    public UsersEntity getUserByEmail(String email) {
        // Busca el usuario en la base de datos a partir del email
        return repository.findByEmail(email);
    }


    /**
     * Valida el formato de la contraseña según la expresión regular:
     * Al menos una letra minúscula, una mayúscula, un número, un carácter especial,
     * y longitud entre 8 y 25 caracteres.
     *
     * @param password contraseña a validar.
     * @throws UsersException si la contraseña no cumple con el formato requerido.
     */
    private void validatePassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@!%*?&+])[A-Za-z\\d$@!%*?&+]{8,25}$";
        if (!password.matches(regex)) {
            throw UsersException.INVALID_PASSWORD_EXCEPTION;
        }
    }


}
