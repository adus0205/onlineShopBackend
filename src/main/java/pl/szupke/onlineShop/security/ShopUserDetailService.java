package pl.szupke.onlineShop.security;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.szupke.onlineShop.security.model.ShopUserDetails;
import pl.szupke.onlineShop.security.model.User;
import pl.szupke.onlineShop.security.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ShopUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(Long.parseLong(username)).orElseThrow();
        ShopUserDetails shopUserDetails = new ShopUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities().stream()
                        .map(userRole -> (GrantedAuthority) userRole::name)
                        .toList()
        );
        shopUserDetails.setId(user.getId());
        return shopUserDetails;
    }
}
