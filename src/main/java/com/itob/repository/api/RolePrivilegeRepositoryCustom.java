package com.itob.repository.api;

import java.util.List;

public interface RolePrivilegeRepositoryCustom {

    List<String> findPrivilegeByRoleHQLExample(String role);

    List<String> findRoleByPrivilegeSQLExample(String privilege);
}
