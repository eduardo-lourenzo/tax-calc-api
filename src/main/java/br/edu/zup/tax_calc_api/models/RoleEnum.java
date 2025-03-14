package br.edu.zup.tax_calc_api.models;

public enum RoleEnum {
    ADMIN,
    USER;

    public String getRoleName() {
        return "ROLE_" + this.name();
    }
}
