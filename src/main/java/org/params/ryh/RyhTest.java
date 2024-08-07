package org.params.ryh;

import org.params.common.dto.EncryptUtil;

public class RyhTest {

    public final static String devEnCode="i5t9zs843tpPYsXgP0ptE0z73HHLTdKMHdbUcxGYCyWQG0YhzvyM7nL5xuJz27im";

    public final static String uatEnCode="i5t9zs843tpPYsXgP0ptE0z73HHLTdKMHdbUcxGYCyWQG0YhzvyM7nL5xuJz27im";
    public static void main(String[] args) {
        String phone="7PTCXDjYd6ttk+7K9owLxw==";   //15812341284
        System.out.println(EncryptUtil.AESdecode(phone, uatEnCode));

        String name="ILHr8lectmO0GU8OeMOXiA==";
        System.out.println(EncryptUtil.AESdecode(name, uatEnCode));

        String nam2="拒绝";
        System.out.println(EncryptUtil.AESencode(nam2, uatEnCode));
    }
}
