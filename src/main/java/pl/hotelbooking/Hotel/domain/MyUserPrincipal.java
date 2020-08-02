package pl.hotelbooking.Hotel.domain;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.hotelbooking.Hotel.domain.dto.UserDTO;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class MyUserPrincipal implements UserDetails {

    private UserDTO userDTO;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.userDTO.getRole().toString()));
    }

    @Override
    public String getPassword() {
        return this.userDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userDTO.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.userDTO.getIsNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
