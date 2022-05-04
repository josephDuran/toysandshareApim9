package cat.itb.m13.toysandsahre.seguretat.jwt;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginPassword {
    private String username;
    private String password;
}
