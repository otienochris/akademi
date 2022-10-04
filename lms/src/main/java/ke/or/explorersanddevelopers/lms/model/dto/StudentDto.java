package ke.or.explorersanddevelopers.lms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ke.or.explorersanddevelopers.lms.model.entity.Certificate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 04/10/2022
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Student Dto", description = "Student Dto details")
public class StudentDto extends UserDto implements Serializable {

    private static final long serialVersionUID = 2266645598292072269L;
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426614174000", notes = "Student record id.")
    private UUID studentId;

    @ApiModelProperty(notes = "Students' certificates")
    private List<Certificate> certificates = new ArrayList<>();
}
