package cat.itb.m13.toysandsahre.model.serveis;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;

@Service
@RequiredArgsConstructor
public class ElMeuUserDetailsService implements UserDetailsService {
    private final ServeisUser serveiUsuarisUserDetails;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return (UserDetails) serveiUsuarisUserDetails.consultarPerUsername(name);
    }

    public UserDetails loadUserById(Long id){
        return (UserDetails) serveiUsuarisUserDetails.consultarPerId(id);
    }
}
