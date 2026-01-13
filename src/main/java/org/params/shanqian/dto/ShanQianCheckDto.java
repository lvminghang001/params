package org.params.shanqian.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ShanQianCheckDto {

    private String order_num;
    private Integer unit_price;
    private String url;
    private String product_name;
    private String company_name;
    private List<SqAgreement> agreements;

//    public void setData(OpenProductDTO productDTO,String url,String orderNum){
//        order_num=orderNum;
//        unit_price=productDTO.getSortPrice().multiply(new BigDecimal("100")).intValue();
//        company_name= "";
//        this.url=url;
//        product_name="融优花";
//        agreements = new ArrayList<>();
//        agreements.add(new SqAgreement("个人信息共享清单","https://ramljykqhb.rongyouhua.com/html/agreement/infoInventory.html"));
//        agreements.add(new SqAgreement("个人身份信息处理授权书","https://ramljykqhb.rongyouhua.com/html/agreement/infoProcessing.html"));
//        agreements.add(new SqAgreement("个人信息处理授权书","https://ramljykqhb.rongyouhua.com/html/agreement/inforDispose.html"));
//        agreements.add(new SqAgreement("数据查询授权说明书","https://ramljykqhb.rongyouhua.com/html/agreement/inquireExplain.html"));
//    }
    private record SqAgreement(String title, String url) {}
}
