package org.params.weiX;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.params.common.AjaxResult;
import org.params.common.utils.DateUtils;
import org.params.weiX.dto.WeiXParamsDto;
import org.params.weiX.dto.WxUrlDto;
import org.params.weiX.util.ProductPlatformCryptoHelper;
import org.params.yql.utils.CommonUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping(value = "/weiX")
public class WeiXController {

    //维信制造撞库参数
    @GetMapping(value = "/hitParams")
    public String hitParams(String phone,String phoneNoCardNo,String cardNo) throws Exception {
        JSONObject object=new JSONObject();
        object.put("phoneNoCardNoMd5",CommonUtils.safeMd5(phoneNoCardNo));
        object.put("phoneNoMd5", CommonUtils.safeMd5(phone));
        object.put("cardNoMd5",CommonUtils.safeMd5(cardNo));
        object.put("phoneNo", CommonUtils.safeMd5(phone));
        return  doResp(object.toJSONString());
    }
/**
 *
 *   {"contactsInfo":[{"contactRelation":"RELA000001","contactName":"李小","contactPhoneNo":"13212341234"},{"contactRelation":"RELA000006","contactName":"张大","contactPhoneNo":"17545356203"}],"personalJobInfo":{"incomeMonth":"3","corporationTel":"19099113525","occupation":"OCCU000005","corporationName":"一般普通公司","industry":"-1"},"extensions":{"cityName":"深圳市","livingVerifyScores":"20.7232","cityCode":"371200"},"bankCardInfo":{"bindPhoneNo":"19099113525","bankCardNo":"6227009765986284433","bankName":"中国建设银行"},"registerId":"1821060133808115714","idCardImageInfo":{"idBackImage":"http://211.95.59.229:6443/http-feeder/common/ryh/file/IMTP000002/1821102117364224001/d4a9129931a440b799a54d6f8a07c914","livingBodyImage":"http://211.95.59.229:6443/http-feeder/common/ryh/file/IMTP000037/1821102117364224001/d4a9129931a440b799a54d6f8a07c914","idFrontImage":"http://211.95.59.229:6443/http-feeder/common/ryh/file/IMTP000001/1821102117364224001/d4a9129931a440b799a54d6f8a07c914"},"loanInfo":{"purposeArea":"LUCS000015"},"transactionId":"1821102117364224001","personalBasicInfo":{"idType":"CETP000001","address":"广东省深圳市福田区八卦二路12号612栋5楼","education":"EDBK000001","nation":"汉","marriage":"MAST000002","idCardNo":"371202199508261308","idValidStart":"20230826","idValidEnd":"20430826","customerName":"子马母","phoneNo":"19099113525","signOrg":"广东省深圳市福田区派出所"}}
 *
 */

//维信制造进件参数
@PostMapping(value = "/pushParams")
public String pushParams(@RequestBody JSONObject params) throws Exception {
    return  doResp(params.toJSONString());
}

    //维信制造url参数
    @GetMapping(value = "/getWebUrlParam")
    public String getWebUrlParam(String applyNo) throws Exception {
        WxUrlDto wxUrlDto=new WxUrlDto();
        wxUrlDto.setApplyNo(applyNo);
        System.out.println(JSON.toJSONString(wxUrlDto));
        return  doResp(JSONObject.toJSONString(wxUrlDto));
    }


    /**
     *        加密参数
     * @param context
     * @return
     */
    public String doResp(String context){
//        String wxSecretParam="{\n" +
//                "\"ryhPubKey\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC969ZdLrr03AGYxQOeFwUVx3MUHypC88rOAe/+jzqUD+hpI/QWYa8uoG8qIDqcgoH5yVWAYJ0jXLTG+uoY9AG1H2BTa4HiQWSd02YbiGMAUDQ47WquPx/BVw9ffVG+BbwimSs1iO+QdLSKauzq6Jij53bdqDyWcdeZuUlI+7e61QIDAQAB\",\n" +
//                "\"ryhPriKey\":\"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL3r1l0uuvTcAZjFA54XBRXHcxQfKkLzys4B7/6POpQP6Gkj9BZhry6gbyogOpyCgfnJVYBgnSNctMb66hj0AbUfYFNrgeJBZJ3TZhuIYwBQNDjtaq4/H8FXD199Ub4FvCKZKzWI75B0tIpq7OromKPndt2oPJZx15m5SUj7t7rVAgMBAAECgYAYbWUuqoRC2qo86TggJQNTLApN3/1KS7t9uCuTUP4+jXY90bFDELDcvSdXInqPZ0zK4R2la6fCZNnejua9q2KtnZiCesaCY/wzYzdfU6Q1fPd6uLGnPTWHcSG1rhIDU9wvrwUYk1HJFTm+HmRYunj02VEUll5Uq1pNinlSehHZYQJBANUJmLZV3BD8DHJCvGPOgg3T0hUGHXRahgCL0CZoqUzMruh84wfNr/YfyyjAObH76u8SSJk3fGVYMsk63+U5JfUCQQDkONPM2okGbRjRpSiNNnRt2lNjQ/UWR0QMpAv8h6AUvLd+VV1H6Ts/otIo9ehsuBd7fTFclqiUTMcevWQp3lVhAkBkJwDOJ4svyO6pAMrEOR9XFM/c5mUOgEgjFZejluyQmGFaFuw8jOw+TzAZVlVJYV9PSU8sCLPIiTBG/hCoGLfJAkAq7UFxa72QVt7vN/iLry1xKDq9FiA4Y4k6M3UJt9z1aZW+DoJwLz90tcR/7dkc8feAw8iQGMp2soZVktwPQAGhAkEAyDaL7DEn/eCCC7qPlo4pgn6/V7UQNrTR/DheWFvtZ9nh7VQdiz1DNu++3dtOTIpMGT7V36/hdtRVE8C4nAeJ+A==\",\n" +
//                "\"weiXPubKey\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHX3lvDbZvU9YraEBbNxrAdJAfWd8oIz36LY9rD/s9E8jeX8MEaQxZq9jI/dvBdMinTZX4poGx6BrS/OwBTqwEU1eCv9PRbGQSa4eJeCakJMPwCkVpoc14kFDz6J9VVSGzKY99ViUpeeNb1rvddMaxsc6jFmnlZS1U6BXeCeOQ0wIDAQAB\",\n" +
//                "\"md5Key\":\"5B3C799A19A763990B86C787F09B0CF0\",\n" +
//                "\"channelCode\":\"wxjk_ryh\",\n" +
//                "\"channelSignature\":\"IkKzWlPdHc9SnsIkCHo4772rCVvQFOnzP9JinhRINE\",\n" +
//                "\"callBackUrl\":\"http://211.95.59.229:6443/http-feeder/common/ryh/credit/resultNotify\"\n" +
//                "}";
        JSONObject params=new JSONObject();
        params.put("ryhPubKey","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC969ZdLrr03AGYxQOeFwUVx3MUHypC88rOAe/+jzqUD+hpI/QWYa8uoG8qIDqcgoH5yVWAYJ0jXLTG+uoY9AG1H2BTa4HiQWSd02YbiGMAUDQ47WquPx/BVw9ffVG+BbwimSs1iO+QdLSKauzq6Jij53bdqDyWcdeZuUlI+7e61QIDAQAB");
        params.put("ryhPriKey","MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL3r1l0uuvTcAZjFA54XBRXHcxQfKkLzys4B7/6POpQP6Gkj9BZhry6gbyogOpyCgfnJVYBgnSNctMb66hj0AbUfYFNrgeJBZJ3TZhuIYwBQNDjtaq4/H8FXD199Ub4FvCKZKzWI75B0tIpq7OromKPndt2oPJZx15m5SUj7t7rVAgMBAAECgYAYbWUuqoRC2qo86TggJQNTLApN3/1KS7t9uCuTUP4+jXY90bFDELDcvSdXInqPZ0zK4R2la6fCZNnejua9q2KtnZiCesaCY/wzYzdfU6Q1fPd6uLGnPTWHcSG1rhIDU9wvrwUYk1HJFTm+HmRYunj02VEUll5Uq1pNinlSehHZYQJBANUJmLZV3BD8DHJCvGPOgg3T0hUGHXRahgCL0CZoqUzMruh84wfNr/YfyyjAObH76u8SSJk3fGVYMsk63+U5JfUCQQDkONPM2okGbRjRpSiNNnRt2lNjQ/UWR0QMpAv8h6AUvLd+VV1H6Ts/otIo9ehsuBd7fTFclqiUTMcevWQp3lVhAkBkJwDOJ4svyO6pAMrEOR9XFM/c5mUOgEgjFZejluyQmGFaFuw8jOw+TzAZVlVJYV9PSU8sCLPIiTBG/hCoGLfJAkAq7UFxa72QVt7vN/iLry1xKDq9FiA4Y4k6M3UJt9z1aZW+DoJwLz90tcR/7dkc8feAw8iQGMp2soZVktwPQAGhAkEAyDaL7DEn/eCCC7qPlo4pgn6/V7UQNrTR/DheWFvtZ9nh7VQdiz1DNu++3dtOTIpMGT7V36/hdtRVE8C4nAeJ+A==");
        params.put("weiXPubKey","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSxl6RH1aYGvFJ0EPzaH2mDXBpkMANQ+smcLFu9gpQOCsdANmlsYiBYNEMBU/woE8LPRQDZOepj44O4ZFjgYFXEehOCafw8WiN8wqnQeJTiBsmGqlqVilp6q8hdiBz62mIqQiUWN1RuqZnRgXbq+B/1iMiC3DrwZM5g7aCLMEsvQIDAQAB");
        params.put("weiXPriKey","MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJLGXpEfVpga8UnQQ/NofaYNcGmQwA1D6yZwsW72ClA4Kx0A2aWxiIFg0QwFT/CgTws9FANk56mPjg7hkWOBgVcR6E4Jp/DxaI3zCqdB4lOIGyYaqWpWKWnqryF2IHPraYipCJRY3VG6pmdGBdur4H/WIyILcOvBkzmDtoIswSy9AgMBAAECgYAUdM1NUJ7TCD5E2V//FrscX5chP2lk7+hgLSitnGEHmSx5GFs9OqcZ0RKRqFPh55XcrSCS8IdRHI7iCBIpWi1abTPwUGewF78L4w5hevlI0Xl2nLUU01Jq95ONqEIqVNdbqdWFEMDzyNbBpRgl9FF6LHrk0xoPDXcpNAuEmDXCgQJBAMlsuW+BIMwAVqP/DEpkpN2KNHJ49uqAkJIVOPe/KUhAkqi18UGeup04x3V8I/TfV7huAkaeEpjU922Prmfrg5ECQQC6iwJKcT0+YBk0aXszulbAebwtKJ3JSp0Wf6Pjc2/F7CC8voQyfIPk6QrTkhis/Jo+ac59f29l5HfrwSB1c6htAkBCaggGnRQcT6xBrLH3wZGevpgmen4nujZxBHBhN/W7rhbta/hdg6HSf0s9EK6mahH5bMRxVmOZYvcmd+6wszYRAkA4VeLLOIeWXs891N0wMgYg+qKpwKpmL/Jg6QtFlYcb7sO0PJUP6iqq5vYuDuEE3QR39Nw3alTqIKVWvWxgDB3BAkAVMTuO+tq5qvOxgfEb4+/6S8gURTzIyrnZBUltVxV9Z7PjnTCx/YbXhIj+jIM6UFGs8UTaV08dJL/y1BtWiOK5");
        params.put("md5Key","5B3C799A19A763990B86C787F09B0CF0");
        params.put("channelCode","wxjk_ryh");
        params.put("channelSignature","IkKzWlPdHc9SnsIkCHo4772rCVvQFOnzP9JinhRINE");
        params.put("callBackUrl","http://211.95.59.229:6443/http-feeder/common/ryh/credit/resultNotify");
        WeiXParamsDto paramsDto=JSONObject.parseObject(params.toJSONString(), WeiXParamsDto.class);
        String msg=null;
        try {
            msg= ProductPlatformCryptoHelper.encrypt(context,paramsDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return msg;
    }


    public static void main(String[] args) {
        JSONObject result=new JSONObject();
        result.put("transactionId","123456");
        result.put("applyNo","123456");
        result.put("status",1);
        result.put("totalAmount",1200000L);
        result.put("avaliableAmount",1200000L);
        result.put("periodScope",12);
        BigDecimal day=new BigDecimal("0.06");
        BigDecimal mouth=new BigDecimal("30");
        BigDecimal year=new BigDecimal("365");
        result.put("yearlyRate",day.multiply(year));
        result.put("monthlyRate",day.multiply(mouth));
        result.put("dailyRate",day);
        result.put("validDate", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,DateUtils.addMonths(new Date(),3)));
        result.put("applyCompleteDate",DateUtils.getTime());
      //  log.info("回调加密前参数:{}",result);
//        String doPostData=doResp(result.toJSONString());
                String wxSecretParam="{\n" +
                "\"ryhPubKey\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC969ZdLrr03AGYxQOeFwUVx3MUHypC88rOAe/+jzqUD+hpI/QWYa8uoG8qIDqcgoH5yVWAYJ0jXLTG+uoY9AG1H2BTa4HiQWSd02YbiGMAUDQ47WquPx/BVw9ffVG+BbwimSs1iO+QdLSKauzq6Jij53bdqDyWcdeZuUlI+7e61QIDAQAB\",\n" +
                "\"ryhPriKey\":\"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL3r1l0uuvTcAZjFA54XBRXHcxQfKkLzys4B7/6POpQP6Gkj9BZhry6gbyogOpyCgfnJVYBgnSNctMb66hj0AbUfYFNrgeJBZJ3TZhuIYwBQNDjtaq4/H8FXD199Ub4FvCKZKzWI75B0tIpq7OromKPndt2oPJZx15m5SUj7t7rVAgMBAAECgYAYbWUuqoRC2qo86TggJQNTLApN3/1KS7t9uCuTUP4+jXY90bFDELDcvSdXInqPZ0zK4R2la6fCZNnejua9q2KtnZiCesaCY/wzYzdfU6Q1fPd6uLGnPTWHcSG1rhIDU9wvrwUYk1HJFTm+HmRYunj02VEUll5Uq1pNinlSehHZYQJBANUJmLZV3BD8DHJCvGPOgg3T0hUGHXRahgCL0CZoqUzMruh84wfNr/YfyyjAObH76u8SSJk3fGVYMsk63+U5JfUCQQDkONPM2okGbRjRpSiNNnRt2lNjQ/UWR0QMpAv8h6AUvLd+VV1H6Ts/otIo9ehsuBd7fTFclqiUTMcevWQp3lVhAkBkJwDOJ4svyO6pAMrEOR9XFM/c5mUOgEgjFZejluyQmGFaFuw8jOw+TzAZVlVJYV9PSU8sCLPIiTBG/hCoGLfJAkAq7UFxa72QVt7vN/iLry1xKDq9FiA4Y4k6M3UJt9z1aZW+DoJwLz90tcR/7dkc8feAw8iQGMp2soZVktwPQAGhAkEAyDaL7DEn/eCCC7qPlo4pgn6/V7UQNrTR/DheWFvtZ9nh7VQdiz1DNu++3dtOTIpMGT7V36/hdtRVE8C4nAeJ+A==\",\n" +
                "\"weiXPubKey\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHX3lvDbZvU9YraEBbNxrAdJAfWd8oIz36LY9rD/s9E8jeX8MEaQxZq9jI/dvBdMinTZX4poGx6BrS/OwBTqwEU1eCv9PRbGQSa4eJeCakJMPwCkVpoc14kFDz6J9VVSGzKY99ViUpeeNb1rvddMaxsc6jFmnlZS1U6BXeCeOQ0wIDAQAB\",\n" +
                "\"md5Key\":\"5B3C799A19A763990B86C787F09B0CF0\",\n" +
                "\"channelCode\":\"wxjk_ryh\",\n" +
                "\"channelSignature\":\"IkKzWlPdHc9SnsIkCHo4772rCVvQFOnzP9JinhRINE\",\n" +
                "\"callBackUrl\":\"http://211.95.59.229:6443/http-feeder/common/ryh/credit/resultNotify\"\n" +
                "}";
        String msg=null;
        WeiXParamsDto paramsDto=JSONObject.parseObject(wxSecretParam, WeiXParamsDto.class);
        try {
            msg= ProductPlatformCryptoHelper.encrypt(result.toJSONString(),paramsDto);
            System.out.println(msg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
