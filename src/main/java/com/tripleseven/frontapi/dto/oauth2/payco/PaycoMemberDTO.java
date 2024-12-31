package com.tripleseven.frontapi.dto.oauth2.payco;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaycoMemberDTO {

    private Header header;
    private Body body;

    @Getter
    @AllArgsConstructor
    public static class Header {
        private boolean isSuccessful;
        private int resultCode;
        private String resultMessage;
    }

    @Getter
    @AllArgsConstructor
    public static class Body {

        private PaycoMember member;

        @Getter
        @AllArgsConstructor
        public static class PaycoMember {

            private int idNo;
            private String email;
            private String mobile;
            private String name;
            private String genderCode;
            private String birthdayMMdd;
        }
    }
}
