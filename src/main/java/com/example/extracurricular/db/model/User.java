package com.example.extracurricular.db.model;

/**
 * Class that represents User entity.
 *
 * @author Yurii Khmil
 */
public final class User {
    private int id;

    private String login;

    private String password;

    private String nameEn;

    private String nameUk;

    private Role role;

    private boolean banned;

    public enum Role {
        STUDENT, TEACHER, ADMIN
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameUk() {
        return nameUk;
    }

    public void setNameUk(String nameUk) {
        this.nameUk = nameUk;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Override
    public String toString() {
        return "{Id: " + id + ", login: " + login + ", password: " + password + ", nameEn: " + nameEn +
                ", nameUk: " + nameUk + ", role: " + role + ", banned: " + banned + "}";
    }
}
