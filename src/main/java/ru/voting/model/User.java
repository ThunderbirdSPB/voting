package ru.voting.model;

import jakarta.validation.constraints.*;
import org.springframework.util.CollectionUtils;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class User extends AbstractBaseEntity{
    @Email
    @NotBlank
    @Size(max = 320)
    private String email;

    @NotNull
    @Size(min = 5, max = 320)
    private String password;

    private boolean voted;

    @NotEmpty
    private Set<Role> roles;

    public User(Integer id, String email, String password, boolean voted, Set<Role> roles) {
        super(id);
        this.email = email;
        this.password = password;
        this.voted = voted;
        this.roles = roles;
    }

    public User() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return voted == user.voted && email.equals(user.email) && password.equals(user.password) && roles.equals(user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, voted, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", voted=" + voted +
                ", roles=" + roles +
                '}';
    }
}
