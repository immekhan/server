package com.itob.repository.impl;

import com.itob.repository.api.RolePrivilegeRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class RolePrivilegeRepositoryImpl implements RolePrivilegeRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<String> findPrivilegeByRoleHQLExample(String role) {

        //this is hibernate example
        List<String> privilegesList = new ArrayList<String>();

        if(!role.isEmpty()){
            StringBuilder sb=new StringBuilder()
                .append("select rp.id.privilege from AbsRolePrivilege rp where rp.id.role = '")
                .append(role).append("'");

            privilegesList = entityManager.createQuery(sb.toString()).getResultList();

        }
        return privilegesList;
    }

    @Override
    public List<String> findRoleByPrivilegeSQLExample(String privilege){

        ///this is SQL example
        List<String> rolePrivilegesList = new ArrayList<String>();
        if(!privilege.isEmpty()){
            StringBuilder sql=new StringBuilder()
                    .append("SELECT ID_ROLE FROM ABS_ROLE_PRIVILEGES WHERE ")
                    .append("UPPER(ID_PRIVILEGE) = '")
                    .append(privilege.toUpperCase()).append("'");
            rolePrivilegesList = jdbcTemplate.queryForList(sql.toString(),String.class);
        }
        return rolePrivilegesList;
    }

}
