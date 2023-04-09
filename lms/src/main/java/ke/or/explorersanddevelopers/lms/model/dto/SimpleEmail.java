package ke.or.explorersanddevelopers.lms.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleEmail {
    @NotNull
    private String[] to;
    private String[] cc;
    private String[] bcc;
    @NotNull
    private String subject;
    @NotNull
    private String text;
}
