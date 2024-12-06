package org.params.xyq;

import com.alibaba.fastjson2.JSONObject;
import org.params.yql.utils.CommonUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *    鑫用钱加解密参数制造
 */

@RestController
@RequestMapping(value = "/xyq")
public class XyqController {
    String hitMsg= """
            {"md5Phone":"5b8332a8793b15a6c875a0bc4ce629ef","md5Name":"db06c78d1e24cf708a14ce81c9b617ec","location":"西藏自治区,拉萨市","sex":1,"age":58,
            "loanLimit":"14","cityCode":"540100","loanLongTime":"3","zmScore":"700以上","creditSituation":"无逾期记录","socialInsurance":
            "有","accumulationFund":"无","businessInsurance":"无","houseProperty":"无","carProperty":"有","monthlyIncome":"5000","businessOwners":"无",
            "openSpendBai":"无","openJdbt":"无","openRedit":"无","education":"初中及以下","occupation":"自由职业","ip":"120.36.215.195"}
            """;

    String pushMsg= """
            {"realPhone":"18025896320","realName":"张三","location":"西藏自治区,拉萨市","sex":1,"age":58,
            "loanLimit":"14","cityCode":"540100","loanLongTime":"3","zmScore":"700以上","creditSituation":"无逾期记录","socialInsurance":
            "有","accumulationFund":"无","businessInsurance":"无","houseProperty":"无","carProperty":"有","monthlyIncome":"5000","businessOwners":"无",
            "openSpendBai":"无","openJdbt":"无","openRedit":"无","education":"初中及以下","occupation":"自由职业","ip":"120.36.215.195"}
            """;

    @PostMapping(value = "/params")
    public String params(@RequestBody JSONObject jsonObject) throws Exception {
         Long times=System.currentTimeMillis();
         String data=XyhAesUtils.encrypt(jsonObject.toJSONString(),"UHm4wIsV5OaK3puI");
         String result= """
                 {"channel":"fgD3jQrUeY0wujvdscuSknMt8ShB5X7s4v8mJDKsG6",
                 "timestamp":%s,
                 "sign":"%s",
                 "content":
                 "%s"
                 }
                 """;
         result= String.format(result, times,getSign(times),data);
         return result;
    }

    private String getSign(Long times){
        String signStr="%s&%s&UHm4wIsV5OaK3puI";
        signStr= CommonUtils.safeMd5(String.format(signStr,"fgD3jQrUeY0wujvdscuSknMt8ShB5X7s4v8mJDKsG6",times));
        return signStr;
    }

    public static void main(String[] args) {
        String msg= """
                {"channel":"fgD3jQrUeY0wujvdscuSknMt8ShB5X7s4v8mJDKsG6",
                "content":"VPzxLL3NDp04dJJ0ei2wwecgvF7ooHoRlfgxXtSva3bhpVL8YJkpaZPA5Ndlv7+8Fvi2vWAdv3hCSdxZHkfMEyyIndd1Wo+jYE3BsItW41b2fI0Fds/+RXbZbXqG/d/tprQ6Ue42mfFMgKZSeZzdgof9pINRh3+PRqLXgLIwPeHcZeDFFoLSUm5oHoBB1HUmiJhxbZp0tak8xNEUaJfTg/rtniqaaYSuemaBHST8a2RlwE2IuO7hjVKYRgor8fYm0xgpR/tS7W4eZSQ98nRb4jGeU7sOdX5pC8sdtvjQbJhOj64DpGzF1lJHBDzHlCvLQdRlZ9uVr24XQUjzhgFKV0HfAwAA/qMoPZTZFj2QxyjpVQQWv1F5vNNegOTGznxCQi5QL9ZBpCOIa+PkJI7woITw3habsJUALb+1t+kThjpU4miC+Yr+UAeXjQTrBiRGV4TYiumCogdnC30qoIHnnSzui7uT/130HDVX/yOhOloWLTAKFN0ZRmbIJgRbrlv66bkMCV+4horF4oUkZsI/CyHCqYtwdJoa4I1XFW4n4YZWirRBVUoUtnTzzsPE5/sWMNAEgFI2IWxU7NLjra1VRXPYvXY2mk38l6GRGeuXIW6m7dVbRF9LtJezzXH9rNhqxtP8vX2c1iOv87lnBYuNumhiEkf95F4WcbLYnvjs5RpKKxlZ5r9/9SHQIXmuSPybo22nv3sVGnaeZ8PH3B+q6tm/0l0+p8Elue1QINB8+bo=",
                "sign":"9464be3acb54baa71d55d802eb2f0cc1","timestamp":1733296257}
                """;


        String msg2= """
                      {\\"channel\\":\\"vwn191pHxBBNE7j9p8e7Ced19lt691C86XTpzHEOI7\\",\\"content\\":\\
                      "R96tJ7cZ6EBf9QDU4LCfwtbIWcZwrtOg4lc45sDNaHcUa5BHfslE11BE3cdQgJbCnxZiTioZvQkMJfkcDk7Q2Mse8Is18ChlR4e4306ON09aALUMXE+vZCrp1s23wkILfcuEGeql8q7axk/Ie3Gm5iNYDLgxDaHTpJj5GTAgevmTPdHfYFUOCjo3CBdtNSXXJm46qBEllVOV254UMFPe7mK1bs5RET06GpyBCYmMDFGE5Y3bxR1k+ZkQ+COAXFVUGVkYDvCm9DN/3aFVvuotjdRQQzNtdxaQYzcYjzCZG8/8YY6fAkRNgagIssVZRRWPRuun08+JlxULLQbDfOCnmOo3grzJjMGPxSUYZwgKSvk5eDF8OXO2eDBUQ2ws/zEB2lVZWH9aOFpNqPLya7eig6KjCf1D7FbOzqCbxM+DY3s9TJ5ELxOx5aGDnl9pukIqF9IBGo9jRYv5riyJE1Q/hD/8mIB6YGeBrUNPs4qo/dXh2/Nx6WFYlkKdrOLYx4dsxKAVilhT5CzZ6huyAfvmfopTuI6FE0Etzfubh9Z3jLHCMYNZMWd37G5kCUWJQlBJh3DamPCxvYLB6wDJfJ9PcRxr+SCdA9w2zzq78INUMYXZO3G3Ed3VzQUKD8FDJL/NtppuxzvXjUQ4IgUyUuityYfdXMGHly9vB7oHIlR9oWicwMn3TaHBu1/zZmirEopFwsTCmVf1tLHr1er3WrdumqeNrj5L+PaKN3T43HxEfXM="
                      ,\\"sign\\":\\"0c41767cb1255718a7e9ae1df8942e7b\\",\\"timestamp\\":1733307019}"
                """;
        String signStr="%s&%s&UHm4wIsV5OaK3puI";

//        String context="R96tJ7cZ6EBf9QDU4LCfwtbIWcZwrtOg4lc45sDNaHcUa5BHfslE11BE3cdQgJbCnxZiTioZvQkMJfkcDk7Q2Mse8Is18ChlR4e4306ON09aALUMXE+vZCrp1s23wkILfcuEGeql8q7axk/Ie3Gm5iNYDLgxDaHTpJj5GTAgevmTPdHfYFUOCjo3CBdtNSXXJm46qBEllVOV254UMFPe7mK1bs5RET06GpyBCYmMDFGE5Y3bxR1k+ZkQ+COAXFVUGVkYDvCm9DN/3aFVvuotjdRQQzNtdxaQYzcYjzCZG8/8YY6fAkRNgagIssVZRRWPRuun08+JlxULLQbDfOCnmOo3grzJjMGPxSUYZwgKSvk5eDF8OXO2eDBUQ2ws/zEB2lVZWH9aOFpNqPLya7eig6KjCf1D7FbOzqCbxM+DY3s9TJ5ELxOx5aGDnl9pukIqF9IBGo9jRYv5riyJE1Q/hD/8mIB6YGeBrUNPs4qo/dXh2/Nx6WFYlkKdrOLYx4dsxKAVilhT5CzZ6huyAfvmfopTuI6FE0Etzfubh9Z3jLHCMYNZMWd37G5kCUWJQlBJh3DamPCxvYLB6wDJfJ9PcRxr+SCdA9w2zzq78INUMYXZO3G3Ed3VzQUKD8FDJL/NtppuxzvXjUQ4IgUyUuityYfdXMGHly9vB7oHIlR9oWicwMn3TaHBu1/zZmirEopFwsTCmVf1tLHr1er3WrdumqeNrj5L+PaKN3T43HxEfXM=";
//        System.out.println((XyhAesUtils.decrypt(context, "hvyoMQSdzL3KAR3c")));
        String push="UP46MBOknOyeh4PpBLr2jzYuKru8RGG0+Kn62zb09bU1q0hHrzKXy1R45AojYm+PH1g9xV8L9ANi5evf3M3qsUvvSV+QuoT2WW8EvMXMv1O7pdRnb1fCXDkpZCEIsyQ0x7lWS/FOGIo2MXjrsgqIECW6nXVsVQLY8fddKWSPZevVvuGIs0UzSZrMw8HSOeEJxbc7XSpfg72Om6Q3qGpAY/TJpD7f0cxsz5OFVdszlmxq1UHyqYu0a88nV4H6A2VOPFyG7/EpvyBE1pXZIUCblh4K0zLQs0VdPwZr53iKBCCNkqX+MLjkk3aPLEjpylLMQP+YcEH4ShvMCCajVKM/QdeTt96PUKyg7+XDmxLN9NJYS+fI6YoMbqjIcAYHwI6FZTrFjwg3IVoe9E8K7htZUnM3pYMXF/9oed9jBXnzjZNBUQcyPinnQJYJP7SNXyR2ZwrMCdfNvU4PwpEwhj9v4KpKMcHPR6FTIsfNRO1SBTG6rrfXA6BjklT6CXsniGk5O+f9rWmcDjXEz6x7dCHxgp1RfwZuCz/hB/YVVGQvKns7a3DwbQiYunIQH8mrkk3eOW8l1OcCRmyy1/AW1b+Miqc9d53zCwUT2pSZvaWbJT7usa2SvPKD+ajNeFMint5fpIj7IiUqXMwQ0hbLn60yCXWP1W5l4EaOEQy8umsQT8k=";
        System.out.println((XyhAesUtils.decrypt(push, "hvyoMQSdzL3KAR3c")));
        //        signStr= CommonUtils.safeMd5(String.format(signStr,"fgD3jQrUeY0wujvdscuSknMt8ShB5X7s4v8mJDKsG6",1733296257));
//        System.out.println(signStr);
    }
}
