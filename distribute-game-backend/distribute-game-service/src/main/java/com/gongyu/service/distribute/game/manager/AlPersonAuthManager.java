package com.gongyu.service.distribute.game.manager;

import com.alibaba.fastjson.JSON;
import com.gongyu.service.distribute.game.model.dto.AuthPersonResultDto;
import com.gongyu.service.distribute.game.model.dto.PersonAuthDto;
import com.gongyu.service.distribute.game.model.dto.PersonAuthRespDto;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.io.IOException;


/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/7/8 11:44
 */
@Slf4j
@Service
public class AlPersonAuthManager {
    //https://market.aliyun.com/products/57000002/cmapi026109.html?spm=5176.2020520132.101.8.599372182m8Sxb#sku=yuncode2010900004
//    @Value("${appCode}")
    private String appCode = "e39ad940fbba4f77a08e621d438462b6";
    private String url = "https://eid.shumaidata.com/eid/check";
    //AppKey：203838058     AppSecret：IXuJOySO09m9c53IY4V26DmWD2OMYCAN

    public PersonAuthDto convertReqParam(String name,String idCard){
        PersonAuthDto param = new PersonAuthDto();
        param.setName(name);
        param.setIdCard(idCard);
        return param;
    }

    public AuthPersonResultDto checkPersonAuth(String idCard, String name) throws ServiceException, IOException, CloneNotSupportedException {
    	AuthPersonResultDto respDto = new AuthPersonResultDto();
    	respDto.setName(name);
    	respDto.setIdCard(idCard);
    	respDto.setRes("1");
    	return respDto;
//        String postUrl = url + "?idcard=" + idCard + "&name=" + name;
////        url = String.format(url,idCard,name);
//
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        HttpPost post = new HttpPost(postUrl);
//        @Cleanup CloseableHttpResponse execute = null;
//        post.setHeader("Authorization","APPCODE " + appCode);
//        try {
//            log.info("checkPersonAuth req param:{}",postUrl);
//            execute = httpClient.execute(post);
//            HttpEntity resp = execute.getEntity();
//
//            String respStr = EntityUtils.toString(resp);
//            log.info("checkPersonAuth respStr:{}",respStr);
//            PersonAuthRespDto respDto = JSON.parseObject(respStr, PersonAuthRespDto.class);
//            if(!respDto.checkRespStatus()){
//                throw new ServiceException(respDto.getMessage());
//            }
//            return respDto.getResult();
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new ServiceException("请求失败");
//        } finally {
//            if(null != post){
//                post.clone();
//            }
//        }

    }

}
