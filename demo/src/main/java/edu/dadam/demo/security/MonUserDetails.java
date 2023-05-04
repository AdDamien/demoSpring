package edu.dadam.demo.security;

import edu.dadam.demo.model.Utilisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MonUserDetails implements UserDetails {

    private Utilisateur utilisateur;

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public MonUserDetails (Utilisateur utilisateur){
        this.utilisateur = utilisateur ;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(utilisateur.getRole().getNom()));
    }
        /*return utilisateur.isAdmin()
                ? List.of(new SimpleGrantedAuthority("ROLE_ADMINISTRATEUR"))
                : List.of(new SimpleGrantedAuthority("ROLE_UTILISATEUR"));
    }


     Autre notation
      @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> roles = new ArrayList<>();

        if(utilisateur.isAdmin()) {
            roles.add(new SimpleGrantedAuthority("ADMINISTRATEUR"));
        } else {
            roles.add(new SimpleGrantedAuthority("UTILISATEUR"));
        }

        return roles;
    }*/

    @Override
    public String getPassword() {
        return utilisateur.getMotDePasse();
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
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
