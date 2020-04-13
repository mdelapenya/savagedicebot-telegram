package com.github.mdelapenya.savagedicebot.model;

public class TelegramUser {

    private Long id;
    private String firstName;
    private String lastName;
    private String alias;

    public TelegramUser() {
        this(null, null, null, null);
    }

    public TelegramUser(Long id, String firstName, String lastName, String alias) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public String toString() {
        return "TelegramUser{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", alias=" + alias + "}";
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 71 * hash + (int) (this.id ^ (this.id >>> 32));

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final TelegramUser other = (TelegramUser) obj;

        return id.equals(other.id);
    }

}
