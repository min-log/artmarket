package llustmarket.artmarket.web.dto.alert;


import lombok.*;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AlramRequestDTO {
    private long alramId; // 알림 리스트 확인
    private long alramAllInId; // 알림 모두 읽기

}
