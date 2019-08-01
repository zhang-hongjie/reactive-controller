//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.config.reactive;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUser implements UserDetails {
    private final String username;
    private final String firstname;
    private final String lastname;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(String username, String firstname, String lastname, String email, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.authorities = authorities;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        throw new IllegalStateException();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public boolean isEnabled() {
        return true;
    }
}
