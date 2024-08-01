package org.params.ryh;

import org.params.common.dto.EncryptUtil;

public class RyhTest {

    public final static String devEnCode="i5t9zs843tpPYsXgP0ptE0z73HHLTdKMHdbUcxGYCyWQG0YhzvyM7nL5xuJz27im";

    public static void main(String[] args) {
        String phone="lAyqUFDQuwxs6+EP+N1Cdw==";   //15812341284
        System.out.println(EncryptUtil.AESdecode(phone, devEnCode));
    }
}
