package br.edu.zup.tax_calc_api.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleEnumTest {
    @Test
    void testRoleEnumValues() {
        RoleEnum[] roles = RoleEnum.values();
        assertEquals(2, roles.length);
        assertEquals(RoleEnum.ADMIN, roles[0]);
        assertEquals(RoleEnum.USER, roles[1]);
    }

    @Test
    void testRoleEnumValueOf() {
        assertEquals(RoleEnum.ADMIN, RoleEnum.valueOf("ADMIN"));
        assertEquals(RoleEnum.USER, RoleEnum.valueOf("USER"));
    }

    @Test
    void testRoleEnumName() {
        assertEquals("ADMIN", RoleEnum.ADMIN.name());
        assertEquals("USER", RoleEnum.USER.name());
    }
}
