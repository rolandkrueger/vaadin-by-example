package de.oio.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @author Roland Krüger
 */
@Entity
public class Role implements GrantedAuthority {
    private Long id;
    private String authority;

    public Role() {}

    public Role(String authority) {
        setAuthority(authority);
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthority(String authority) {
        Objects.requireNonNull(authority);
        this.authority = authority;
    }

    @Override
    @Column(unique = true)
    public String getAuthority() {
        return authority;
    }

    @Override
    public String toString() {
        return authority;
    }
}
