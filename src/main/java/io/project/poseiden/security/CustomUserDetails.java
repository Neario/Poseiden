package io.project.poseiden.security;

import io.project.poseiden.model.User;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final User user;

    /**
     * Permet de retourner les roles de l'urilisateur
     * Le role est est récuperer via l'utilisateur en base et afin de recpecter la convention SpringSecurity le role est préfixé
     *
     * @return une liste contenant {@link SimpleGrantedAuthority} avec le role préfixé
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    /**
     * Retourne le mot de passe hash de l'utilisateur en base
     * Spring security compare avec le mot de passe saisi dans le formulaire de connexion
     * @return le mot de passe hash en base
     */
    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    /**
     * @return le pseudo de l'utilisateur
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
