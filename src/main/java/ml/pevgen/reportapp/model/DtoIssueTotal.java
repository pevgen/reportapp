package ml.pevgen.reportapp.model;

import lombok.*;


@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DtoIssueTotal {

    private Double max;
    private Double min;
    private Double avg;
    private Double median;
}
