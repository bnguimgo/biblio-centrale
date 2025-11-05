package com.bnguimgo.biblio.biblocentrale.utils;

public enum PrivilegeEnum {

    READ_PRIVILEGE("READ"),
    WRITE_PRIVILEGE("WRITE"),
    DELETE_PRIVILEGE("DELETE");

    private final String privilege;
    PrivilegeEnum(String privilege) {
        this.privilege = privilege;
    }
}
