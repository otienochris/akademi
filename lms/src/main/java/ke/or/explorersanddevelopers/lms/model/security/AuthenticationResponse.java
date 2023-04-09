package ke.or.explorersanddevelopers.lms.model.security;

import jakarta.validation.constraints.NotNull;
import ke.or.explorersanddevelopers.lms.enums.RolesEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @NotNull
    private String token;
    private RolesEnum role;
    private Date expiry;
}
