package io.project.poseiden.security;

import io.project.poseiden.model.User;
import io.project.poseiden.repository.UserRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Charge les utilisateurs depuis la base de données lors de l'authentification
     * fait appel à {@link UserDetailsService}
     * Spring Securiy appelle automatiquement la méthode loadUserByUsername
     * Lors de l'envoie du formulaire de connexion , regarde si l'utilisateur existe , et recupère les infos
     * Si aucun utilisateur trouvé lève une exception {@link UsernameNotFoundException}
     * @param username est le pseudo rentré dans la page de connexion
     * @return un {@link UserDetails} avec l'utilisateur trouvé
     * @throws UsernameNotFoundException si aucun utilisateur trouvé
     */
    @Override
    @Nonnull
        public UserDetails loadUserByUsername(@Nonnull String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new CustomUserDetails(user);
    }
}
