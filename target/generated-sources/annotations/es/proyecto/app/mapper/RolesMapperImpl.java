package es.proyecto.app.mapper;

import es.proyecto.app.entity.RolesEntity;
import es.swagger.codegen.models.Role;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-05T17:35:06+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class RolesMapperImpl implements RolesMapper {

    @Override
    public Role toApiDomain(RolesEntity source) {
        if ( source == null ) {
            return null;
        }

        Role role = new Role();

        role.setIdRole( source.getIdRole() );
        role.setRoleName( source.getRoleName() );

        return role;
    }

    @Override
    public List<Role> toApiDomain(List<RolesEntity> source) {
        if ( source == null ) {
            return null;
        }

        List<Role> list = new ArrayList<Role>( source.size() );
        for ( RolesEntity rolesEntity : source ) {
            list.add( toApiDomain( rolesEntity ) );
        }

        return list;
    }

    @Override
    public RolesEntity toEntity(Role source) {
        if ( source == null ) {
            return null;
        }

        RolesEntity.RolesEntityBuilder rolesEntity = RolesEntity.builder();

        if ( source.getIdRole() != null ) {
            rolesEntity.idRole( source.getIdRole() );
        }
        rolesEntity.roleName( source.getRoleName() );

        return rolesEntity.build();
    }
}
