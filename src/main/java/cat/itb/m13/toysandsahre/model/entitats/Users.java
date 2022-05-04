package cat.itb.m13.toysandsahre.model.entitats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "users")
public class Users implements UserDetails {

    @Id
    @GeneratedValue
    int id;
    String name;
    String lastname;
    String email;
    String password;
    String adress;
    String city;
    String country;
    @Column(name = "postalCode")
    Long postalCode;
    @Column(name = "dateCreated")
    Date dateCreated;
    @Column(name = "lastLogin")
    int lastLogin;
    int status;
    String description;
    @Column(name = "profileImage")
    String profileImage;

    private String rol = "USER"; //per defecte


    @OneToMany
    private List<Products> products;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "google_id", referencedColumnName = "google_id")
//    private GoogleUsers googleUsers;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        //l'altre rol seria "ROLE_ADMIN", per exemple
        return roles;
    }

    @Override
    public String getUsername() {
        return null;
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
