package llustmarket.artmarket.web.dto.alert;


import lombok.*;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Alrams {
    private List< AlramDTO> alrams;
}

