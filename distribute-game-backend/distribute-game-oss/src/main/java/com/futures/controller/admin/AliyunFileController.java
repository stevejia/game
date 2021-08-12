package com.futures.controller.admin;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.file.api.FileService;
import com.gongyu.snowcloud.framework.file.api.UploadFileRequest;
import com.gongyu.snowcloud.framework.file.impl.aliyun.AliyunOssConfig;
import com.gongyu.snowcloud.framework.file.impl.aliyun.AliyunStsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("aliyunFile")
@Api(tags = "阿里云文件上传")
@Slf4j
public class AliyunFileController {
    @Autowired
    @Qualifier("aliyunFileService")
    private FileService fileService;
    @Autowired
    private AliyunOssConfig aliyunOssConfig;
    @Autowired
    private AliyunStsConfig aliyunStsConfig;

    @ApiOperation(value = "上传图片", notes = "基础定义中data：返回上传图片可访问URL")
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse upload(@ApiParam(name = "文件对象", required = true) MultipartFile file) {
        try {
            if (null == file || StringUtils.isEmpty(file.getOriginalFilename())) {
                throw new BizException("请选择要上传的文件");
            }
            return BaseResponse.success(uploadFile(StringUtils.isEmpty(aliyunOssConfig.getOssDir())?"root":aliyunOssConfig.getOssDir(), file));
        } catch (Exception e) {
            log.error("upload File Error=" + e.getMessage(), e);
            return BaseResponse.error(e.getMessage());
        }
    }

    private String uploadFile(String fileGroupName, MultipartFile file) throws IOException {
        UploadFileRequest uploadFileRequest = new UploadFileRequest();
        uploadFileRequest.setInputStream(file.getInputStream());
        uploadFileRequest.setFileName(file.getOriginalFilename());
        uploadFileRequest.setGroupName(fileGroupName);
        return fileService.uploadFile(uploadFileRequest);
    }


    @ApiOperation(value = "批量上传图片", notes = "基础定义中data：返回上传图片可访问URL的列表")
    @RequestMapping(value = "batchUpload", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse batchUpload( @ApiParam(name = "文件对象列表", required = true) MultipartFile[] files) {
        log.info(">>>>>>>参数fileGroupName:{}, MultipartFile[] size", files.length);
        try {
            if (ArrayUtils.isEmpty(files)) {
                throw new BizException("请选择要上传的文件列表");
            }
            //判断file数组不能为空并且长度大于0
            String[] fileUrls = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                if (null != file && StringUtils.isNotEmpty(file.getOriginalFilename())) {
                    log.info(">>>>>>>>file:{}, fileName:{}", file, file.getOriginalFilename());
                    fileUrls[i] = uploadFile(StringUtils.isEmpty(aliyunOssConfig.getOssDir())?"root":aliyunOssConfig.getOssDir(), file);
                } else {
                    log.info(">>>>>>>>param error file:{}, fileName:{}", file, file.getOriginalFilename());
                    fileUrls[i] = "";
                }
            }
            return BaseResponse.success(fileUrls);
        } catch (Exception e) {
            log.error("batchUpload File Error=" + e.getMessage(), e);
            return BaseResponse.error(e.getMessage());
        }
    }

    @RequestMapping(value = "getAuth", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public BaseResponse getAuth(){
        String roleSessionName = "session-name";
        String policy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:*\"\n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:*\" \n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        try {
            // 添加endpoint（直接使用STS endpoint，前两个参数留空，无需添加region ID）
            DefaultProfile.addEndpoint("", "", "Sts", aliyunStsConfig.getEndpoint());
            // 构造default profile（参数留空，无需添加region ID）
            IClientProfile profile = DefaultProfile.getProfile("", aliyunStsConfig.getAccessKeyId(), aliyunStsConfig.getAccessKeySecret());
            // 用profile构造client
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setMethod(MethodType.POST);
            request.setRoleArn(aliyunStsConfig.getRoleArn());
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy); // Optional
            final AssumeRoleResponse response = client.getAcsResponse(request);
            log.info("Expiration: " + response.getCredentials().getExpiration());
            log.info("Access Key Id: " + response.getCredentials().getAccessKeyId());
            log.info("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
            log.info("Security Token: " + response.getCredentials().getSecurityToken());
            log.info("RequestId: " + response.getRequestId());

            return BaseResponse.success(response);
        } catch (ClientException e) {
            log.error("getAuth error=" + e.getMessage(), e);
            log.error("Failed：");
            log.error("Error code: " + e.getErrCode());
            log.error("Error message: " + e.getErrMsg());
            log.error("RequestId: " + e.getRequestId());
        }
        return BaseResponse.error("获取配置信息失败");
    }
}
