package llustmarket.artmarket.web.dto.member;


import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MemberDTO {
    private long memberId;
    private String nickname;
    private String identity;
}
