package es.proyecto.app.mapper;

import es.proyecto.app.entity.RolesEntity;
import es.proyecto.app.entity.UsersEntity;
import es.swagger.codegen.models.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-11T20:45:48+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.14 (Oracle Corporation)"
)
@Component
public class UsersMapperImpl implements UsersMapper {

    @Override
    public User toApiDomain(UsersEntity source) {
        if ( source == null ) {
            return null;
        }

        User user = new User();

        user.setIdUser( source.getIdUser() );
        user.setUserName( source.getUserName() );
        user.setUserSurname( source.getUserSurname() );
        user.setUserBirth( source.getBirthDate() );
        user.setEmail( source.getEmail() );
        user.setPassword( source.getPassword() );
        user.setRoleId( sourceRoleIdIdRole( source ) );

        return user;
    }

    @Override
    public List<User> toApiDomain(List<UsersEntity> source) {
        if ( source == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( source.size() );
        for ( UsersEntity usersEntity : source ) {
            list.add( toApiDomain( usersEntity ) );
        }

        return list;
    }

    @Override
    public UsersEntity toEntity(User source) {
        if ( source == null ) {
            return null;
        }

        UsersEntity.UsersEntityBuilder usersEntity = UsersEntity.builder();

        usersEntity.roleId( userToRolesEntity( source ) );
        usersEntity.idUser( source.getIdUser() );
        usersEntity.userName( source.getUserName() );
        usersEntity.userSurname( source.getUserSurname() );
        usersEntity.birthDate( source.getUserBirth() );
        usersEntity.email( source.getEmail() );
        usersEntity.password( source.getPassword() );

        return usersEntity.build();
    }

    private Integer sourceRoleIdIdRole(UsersEntity usersEntity) {
        if ( usersEntity == null ) {
            return null;
        }
        RolesEntity roleId = usersEntity.getRoleId();
        if ( roleId == null ) {
            return null;
        }
        int idRole = roleId.getIdRole();
        return idRole;
    }

    protected RolesEntity userToRolesEntity(User user) {
        if ( user == null ) {
            return null;
        }

        RolesEntity.RolesEntityBuilder rolesEntity = RolesEntity.builder();

        if ( user.getRoleId() != null ) {
            rolesEntity.idRole( user.getRoleId() );
        }

        return rolesEntity.build();
    }
}
