package com.example.demo.util;

import cn.hutool.core.util.HexUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 付讯签名认证
 * @param
 * @return
* @author Fengping.Pan
 * @date 2021/9/1 16:46
 */
public class SignUtils {

    private static final Logger logger = LoggerFactory.getLogger(SignUtils.class);

    public static void main(String[] args) {

        FuxunBaseRequest fuxunBaseRequest = new FuxunBaseRequest();
        //获取美团账户信息
//        FuxunAccount fuxunAccount = meituanAccountStore.getMeituanAccount(accountCode);
//        fuxunBaseRequest.setMethod(getMethod());
        fuxunBaseRequest.setVersion("1.0");
        fuxunBaseRequest.setTimestamp(System.currentTimeMillis()/1000);
        fuxunBaseRequest.setNonce("10000");
        fuxunBaseRequest.setPartnerId("geely");
        fuxunBaseRequest.setAppId("aaffbefb-75c8-44bc-8da2-8749d138885c");
//        "{\"hotelId\":24935,\"ratePlanId\":10,\"roomTypeId\":4,\"clientId\":null,\"checkInAt\":\"2021-09-03\",\"checkOutAt\":\"2021-09-04\",\"priceCalendar\":null,\"payType\":3,\"priceType\":0,\"roomNum\":1,\"customerNum\":null,\"breakfastQty\":null,\"currency\":null,\"cacheRateId\":null,\"otherPayload\":null}"
//        FuxunHotelListRequest request=new FuxunHotelListRequest();
//        request.setHotelIds(Lists.newArrayList("24935"));
//        FuxunHotelListRequest.Paginate paginate = new FuxunHotelListRequest.Paginate();
//        paginate.setPageNum(1);
//        paginate.setPageLimit(1);
//        request.setPaginate(paginate);
//        GetRoomPriceContractRequest request=new GetRoomPriceContractRequest();
//        request.setClientId("41");
//        request.setCheckInAt("2021-09-03");
//        request.setCheckOutAt("2021-09-04");
//        request.setHotelId(24935L);
//        PreCheckRoomRequest request=new PreCheckRoomRequest();
//        request.setCheckInAt("2021-09-03");
//        request.setCheckOutAt("2021-09-04");
//        request.setHotelId(24935L);
//        request.setRoomNum(1);
//        request.setRatePlanId(10L);
//        request.setPayType(3);
//        request.setPriceType(0);
//        request.setClientId(41);
//        request.setCustomerNum(1);
//        request.setPriceCalendar(Lists.newArrayList());
//        PriceCalendar p=new PriceCalendar();
//        p.setDate("2021-09-03");
//        p.setAmount(new BigDecimal("1"));
//        request.getPriceCalendar().add(p);
////        request.setBreakfastQty(1);
//        request.setRoomTypeId(4L);
//        FuxunRoomTYpeRequest request=new FuxunRoomTYpeRequest() ;
//        request.setHotelId("24935");
        FuxunOrderQueryRequest request=new FuxunOrderQueryRequest();
        request.setReservationNumbers(Lists.newArrayList("FR20210906102214160666"));
        //具体业务数据
        fuxunBaseRequest.setData(JSONUtil.toJsonStr(request));
        System.out.println(createSign(fuxunBaseRequest, "TzcvPamdDs4W5QuU2HQb"));
    }
    /**
     * 生成签名
     * @param request 入参
     * @param secretKey 秘钥
     * @return
     * @author Fengping.Pan
     * @date 2021/9/1 16:45
     */
    public static String createSign(FuxunBaseRequest request, String secretKey) {
        Map<String, Object> params;
        String jsonData =JSONUtil.toJsonStr(request);
        params = JSONUtil.toBean(jsonData, Map.class);
        Map<String, String> needVerify = new HashMap<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if(entry.getValue() != null){
                needVerify.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        List<Map.Entry<String, String>> entryList = new ArrayList<>(needVerify.entrySet());
        //排序
        entryList.sort(Comparator.comparing(Map.Entry::getKey));
        StringBuilder buffer = new StringBuilder();
        for (Map.Entry<String, String> entry : entryList) {
            buffer.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        String secret = hmacSha1(buffer.substring(1), secretKey);
        if(StringUtils.isEmpty(secret)){
            return null;
        }
        return secret;
    }

    /**
     * HMAC-SHA1算法加密
     * @param inStr 待加密字符串
     * @param secretKey 秘钥
     * @return
     * @author Fengping.Pan
     * @date 2021/9/1 16:47
     */
    public static String hmacSha1(String inStr, String secretKey) {
        try {
            SecretKeySpec signKey = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signKey);
            byte[] rawHmac = mac.doFinal(inStr.getBytes());
            String hex = HexUtil.encodeHexStr(rawHmac);
            return new String(Base64.encodeBase64(hex.getBytes(StandardCharsets.UTF_8))).trim();
        } catch (Exception e) {
            return null;
        }
    }

}
