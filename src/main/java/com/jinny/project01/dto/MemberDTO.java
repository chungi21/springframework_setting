package com.jinny.project01.dto;

import lombok.*;

@Getter
@Setter
//@Data
//@NoArgsConstructor
@ToString
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private int memberAge;
    private String memberMobile;
}
