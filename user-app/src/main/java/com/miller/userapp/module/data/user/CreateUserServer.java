package com.miller.userapp.module.data.user;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.entity.user.UserEntity;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.cache.remote.redis.RedisService;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.constants.PaymentConstant;
import com.miller.userapp.module.data.pay.db.UserAccountSql;
import com.miller.userapp.module.data.user.db.*;
import com.miller.userapp.module.home.captcha.flow.UserSendVerificationCodeFlow;
import com.miller.userapp.module.home.captcha.request.UserSendVerificationCodeRequest;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.home.login.response.UserLoginResponseDTO;
import com.miller.userapp.module.pay.card.stripe.flow.CreatePaymentMethodFlow;
import com.miller.userapp.module.pay.card.stripe.flow.GetPaymentMethodsFlow;
import com.miller.userapp.module.pay.card.stripe.request.CreatePaymentMethodRequestDTO;
import com.miller.userapp.module.pay.card.stripe.request.GetPaymentMethodsRequestDTO;
import com.miller.userapp.module.person.address.create.request.AddressRequestDTO;
import com.miller.userapp.module.person.address.create.response.AddressResponseDTO;
import com.miller.service.util.AutoSignUtils;
import com.miller.userapp.util.DBUtils;
import com.miller.userapp.util.RequestUtils;
import com.panda.delivery.app.server.common.util.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.*;

public class CreateUserServer {
    private static final String[] prefixTels = {"165", "167", "170", "171", "187", "190", "192", "139", "159", "195", "137"};
    private String saveOrUpdateUrl = BusinessConstant.DOMAIN + "/api/app/user/v1/address/edit";
    private SqlSession sqlSession;
    private AccountSql accountSql;
    private UserSql userSql;
    private DeviceLoginInfoSql deviceLoginInfoSql;
    private IntegralSql integralSql;
    private UserLogSql userLogSql;
    private UserRegInfoSql userRegInfoSql;
    private UserAccountSql userAccountSql;
    private Map<String, Object> headers;
    private RedisService redisService;
    private Long userId;
    private CreateUserEntity createUserEntity;

    private String deviceId = "auto-test-device";

    public CreateUserServer(SqlSession sqlSession, CreateUserEntity createUserEntity) {
        this.sqlSession = sqlSession;
        this.createUserEntity = createUserEntity;
        initSql();
    }

    public void initSql() {
        accountSql = new AccountSql(sqlSession);
        deviceLoginInfoSql = new DeviceLoginInfoSql(sqlSession);
        integralSql = new IntegralSql(sqlSession);
        userLogSql = new UserLogSql(sqlSession);
        userRegInfoSql = new UserRegInfoSql(sqlSession);
        userSql = new UserSql(sqlSession);
        userAccountSql = new UserAccountSql(sqlSession);
        headers = new HashMap<>();
        redisService = RedisService.getRedisServiceInstance();
        redisService.connectionSlave("r-3nscqny4art27v9hrzpd.redis.rds.aliyuncs.com", 6379, "YNKAthEbNF3XoK8E");
        redisService.set("message-server:IMG_CAPTCHA:28d33b2425c344c581a4520f3c8c98f9", 32, 60L);
    }

    /**
     * 1.创建CreateUserEntity 数据
     * a.loginPassword为登陆密码，默认12345678
     * b.payPassword为支付密码，默认123456
     * c.balance为账户余额，默认1_000_000
     * 2.autoCreateUser 开始创建用户账号，isAuto 为true则自动创建用户，false则根据tel来创建
     * 3.发送短信验证接口 （redis自动插入图形校验值32，根据手机号码前3后4模糊匹配验证码
     * 4.登陆接口 （获取userId）
     * 5.根据userId修改user相关表数据，如登陆密码，支付密码，账户余额
     * 6.绑定一个默认地址，type为1为创建地址
     * 7.在countryCode=SG 创建一张银行卡
     */
    public static void main(String[] args) {
        String createUserJson = """
                {
                    "loginPassword":"12345678",
                    "payPassword":"123456",
                    "balance":1000000,
                    "address":{
                        "addressRemark": "",
                	    "postcode": "310000",
                	    "longitude": "120.22185",
                	    "buildingName": "星耀中心",
                	    "countryCode": "86",
                	    "isDefault": "0",
                	    "addTag": 2,
                	    "address": "China, Zhejiang, Hangzhou, Binjiang District, 072, 东北方向160米星耀中心",
                	    "houseNum": "1288",
                	    "latitude": "30.20074",
                	    "contacts": "手机号",
                	    "type": 1,
                	    "telephone": "19240377998",
                	    "gender": 1
                    }
                    
                }
                """;
        CreateUserEntity createUserData = JSON.parseObject(createUserJson, CreateUserEntity.class);
        CreateUserServer createUserServer = new CreateUserServer(DBUtils.getDBOfPandaTest(), createUserData);
        String result = createUserServer.autoCreateUser(true, null);
        System.out.println("result is : " + result);
//        createUserServer.autoCreateUser(false,"19225568102");
    }

    /**
     * @param isAuto true为自动创建用户，false则根据tel来创建
     * @param tel
     * @return
     */
    public String autoCreateUser(Boolean isAuto, String tel) {
        if (isAuto) {
            int count = 0;
            while (true) {
                count++;
                int size = prefixTels.length;
                int random = new Random().nextInt(size);
                String autoTel = prefixTels[random] + String.valueOf(new Random().nextInt(10_000_000, 100_000_000));
                if (checkUser(autoTel)) {
                    return createUserData(autoTel) + "";
                }
                if (count > 1000) {
                    break;
                }
            }
            return "没有账号可创建了";

        } else {
            if (StringUtils.isEmpty(tel) || tel.length() != 11) {
                return "请输入正确手机号！";
            }
            Optional<String> prefixTel = Arrays.stream(prefixTels).filter(tel::startsWith).findAny();
            if (!prefixTel.isPresent()) {
                return "请输入{165,167,170,171,187,190,192,139,159,195,137}开头手机号！";
            }
            if (checkUser(tel)) {
                return createUserData(tel) + "";
            } else {
                return tel + "账号已存在";
            }
        }
    }

    public boolean checkUser(String tel) {
        UserEntity user = userSql.getUser(tel);
        if (Objects.nonNull(user)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 发送手机验证码
     *
     * @param tel
     */
    public void sendVerificationCode(String tel) {
        UserSendVerificationCodeRequest request = new UserSendVerificationCodeRequest();
        UserSendVerificationCodeRequest.CaptchaCheckDTO captchaCheckDTO = new UserSendVerificationCodeRequest.CaptchaCheckDTO();
        UserSendVerificationCodeRequest.CaptchaCheckDTO.ImageCaptchaCheckDTO imageCaptchaCheckDTO = new UserSendVerificationCodeRequest.CaptchaCheckDTO.ImageCaptchaCheckDTO();
        imageCaptchaCheckDTO.setCheckCode("32");
        captchaCheckDTO.setCaptchaType(2);
        captchaCheckDTO.setImageCheckInfo(imageCaptchaCheckDTO);
        request.setCaptchaCheckInfo(captchaCheckDTO);
        request.setAreaCode("86");
        request.setCaptchaToken("28d33b2425c344c581a4520f3c8c98f9");
        //需要在redis存值，不然图形校验不通过
        System.out.println("value: " + redisService.get("message-server:IMG_CAPTCHA:28d33b2425c344c581a4520f3c8c98f9"));
        request.setScene(102);
        request.setSendType(0);
        request.setPhoneNumber(tel);
        request.setDeviceId(UUID.randomUUID().toString());
        UserSendVerificationCodeFlow.sendVerificationCode(request);
    }

    public Integer getCaptchaCode(String tel) {
        Integer verifyCode = userLogSql.getCaptcha(tel);
        return verifyCode;
    }

    /**
     * 登陆账号
     *
     * @param tel
     * @param verificationCode
     */
    public void userLogin(String tel, String verificationCode) {
        UserLoginRequestDTO request = new UserLoginRequestDTO();
        request.setAreaCode("86");
        request.setDistinctId(deviceId);
        request.setCityName("%E6%9D%AD%E5%B7%9E%E5%B8%82");
        request.setType(1);
        request.setAccount(tel);
        request.setVerification(verificationCode);

        UserLoginResponseDTO userLoginResponseDTO = UserLoginFlow.loginReturnBodyObject(request);

        // 获取token
        var token = userLoginResponseDTO.getResult().getAccessToken();
        // 获取token
        headers.put("Content-Type", "application/json");
        headers.put("countrycode", "SG");
        headers.put("authorization", token);
        userId = userLoginResponseDTO.getResult().getUserId();
    }

    /**
     * 修改用户密码（初始值12345678）,修改余额值（初始值1_000_000)
     *
     * @param userId
     */
    public void updatePWDAndBalance(Long userId) {
        String salt = PasswordUtil.genSalt(10);
//        String pwd = PasswordUtil.encrypt(MD5Util.string2MD5(createUserEntity.getLoginPassword()),salt);//12345678
        //新库直接调用，不用上面方法了
        String pwd = new SimpleHash("MD5", MD5Util.string2MD5(createUserEntity.getLoginPassword()), salt).toString();
        String pwdBalance = MD5Util.string2MD5(createUserEntity.getPayPassword());
        int balance = createUserEntity.getBalance();
        userSql.updatePassword(userId, salt, pwd); //用户登陆密码
        userAccountSql.update(userId, balance, pwdBalance);//余额支付密码
        accountSql.update(userId, balance);

    }

    /**
     * 创建收货地址，公司地址
     *
     * @param tel
     */
    public void createAddress(String tel) {
//        String addressJson = """
//                {
//                	"addressRemark": "",
//                	"postcode": "310000",
//                	"longitude": "120.22185",
//                	"buildingName": "星耀中心",
//                	"countryCode": "86",
//                	"isDefault": "0",
//                	"addTag": 2,
//                	"address": "China, Zhejiang, Hangzhou, Binjiang District, 072, 东北方向160米星耀中心",
//                	"houseNum": "1288",
//                	"latitude": "30.20074",
//                	"contacts": "手机号",
//                	"type": 1,
//                	"telephone": "468885658",
//                	"gender": 1
//                }
//                """;
//        AddressRequestDTO addressRequestDTO = JSON.parseObject(addressJson,AddressRequestDTO.class);
        AddressRequestDTO addressRequestDTO = createUserEntity.getAddress();
        addressRequestDTO.setTelephone(tel);
        AutoSignUtils.signHandler(RequestUtils.getHeaders(), JSON.toJSONString(addressRequestDTO));

        HttpUtils.sendPostRequestReturnJavaObject(saveOrUpdateUrl, null, headers, JSON.toJSONString(addressRequestDTO), null, AddressResponseDTO.class);
    }

    /**
     * 绑定一张测试卡，stripe通道
     */
    public void createPaymentCard() {
        GetPaymentMethodsRequestDTO getPaymentMethodsRequestDTO = new GetPaymentMethodsRequestDTO();
        GetPaymentMethodsFlow.getPaymentMethods(getPaymentMethodsRequestDTO, headers); //注册stipe用户信息
        CreatePaymentMethodRequestDTO createPaymentMethodRequestDTO = new CreatePaymentMethodRequestDTO();
        createPaymentMethodRequestDTO.setCvc("737");
        createPaymentMethodRequestDTO.setExpMonth("09");
        createPaymentMethodRequestDTO.setExpYear("2050");
        createPaymentMethodRequestDTO.setPostalCode("310000");
        createPaymentMethodRequestDTO.setCardNumber(PaymentConstant.CARDNUMBER);
        CreatePaymentMethodFlow.createPaymentMethod(createPaymentMethodRequestDTO, headers);
    }


    /**
     * 创建用户及后续地址、卡等创建
     * 主入口
     *
     * @param tel
     */
    private Long createUserData(String tel) {
        System.out.println("手机号为：" + tel);

        sendVerificationCode(tel);
        Integer code = getCaptchaCode(tel);
        userLogin(tel, String.valueOf(code));
        System.out.println("userId：" + userId);
        UserEntity user;
        int count = 0;
        //后续动作
        while (true) {
            count++;
//            user = userSql.getUser(tel);
            user = userSql.getUser(userId);
            if (Objects.isNull(user)) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
            if (count > 60) break;//5分钟后退出


        }
        System.out.println("user：" + JSON.toJSON(user));
//        if(Objects.isNull(user)){
//            throw  new RuntimeException("创建用户失败");
//        }
        updatePWDAndBalance(userId);
        createAddress(tel);
        createPaymentCard();
        return userId;
    }
/*
    private void insertUser(String tel){
        Long createTime  = Instant.now().toEpochMilli();
        String salt = PasswordUtil.genSalt(10);
        String pwd = PasswordUtil.encrypt("12345678",salt);
        UserEntity user = new UserEntity();
        user.setUserName(tel);
        user.setUserPassword(pwd);
        user.setUserSalt(salt);
        user.setUserTelphone(tel);
        user.setCreateTime(createTime);
        user.setRegIp("103.177.249.129");
        user.setRoleId(0L);
        user.setBs("杭州市");
        user.setUserPushToken("c3e829abbcc7fbfab67e3b4d2be76333");
        user.setCountry("中国");
        user.setCallingCode("86");
        user.setCityName("杭州市");
        user.setRegisterSource(11);
        System.out.println("user: "+ JSON.toJSON(user));
        userSql.insert(user);
    }
    private void insertUserLog(UserEntity user){
        Long createTime  = Instant.now().toEpochMilli();
        UserLogEntity userLog = new UserLogEntity();
        userLog.setContentMask("来自自动插入数据创建的账号");
        userLog.setUserId(user.getUserId());
        userLog.setUserType(1);
        userLog.setTelephone(user.getUserTelphone());
        userLog.setUserName(user.getUserName());
        userLog.setLoginIp(user.getRegIp());
        userLog.setTelephoneMask(user.getUserTelphoneMask());
        userLog.setUserNameMask(user.getUserNameMask());
        userLog.setCreateTime(createTime);
        userLogSql.insert(userLog);
    }
    private void insertIntegral(Long userId){
        Long createTime  = Instant.now().toEpochMilli();
        IntegralEntity integral = new IntegralEntity();
        integral.setUserId(userId);
        integral.setIntergral(1_000_000L);
        integral.setCreateTime(createTime);
        integralSql.insert(integral);
    }
    private void insertAccount(Long userId){
        AccountEntity account = new AccountEntity();
        account.setUserId(userId);
        account.setAccountBalance(1_000_000);
        accountSql.insert(account);
    }
    private void insertUserRegInfo(Long userId){
        Long createTime  = Instant.now().toEpochMilli();
        UserRegInfoEntity userRegInfo = new UserRegInfoEntity();
        userRegInfo.setUserId(userId);
        userRegInfo.setPostCode("310051");
        userRegInfo.setLocation("中华人民共和国浙江省杭州市Jiangling Rd  江陵路310051地铁滨和路站");
        userRegInfo.setRegIp("103.177.249.129");
        userRegInfo.setLatitude("30.20108");
        userRegInfo.setLongitude("120.22146");
        userRegInfo.setPlatform("IOS_USER");
        userRegInfo.setDeviceId("A0AF937A-4804-4FC8-8C06-31611200D251");
        userRegInfo.setLocationL1("China");
        userRegInfo.setLocationL2("Zhejiang");
        userRegInfo.setLocationL3("Binjiang Qu");
        userRegInfo.setAppLanguage("CN");
        userRegInfo.setCreateTime(createTime);
        userRegInfoSql.insert(userRegInfo);
    }
    private void insertUserAccount(Long userId){
        UserAccountEntity userAccount = new UserAccountEntity();
        userAccount.setUserId(userId);


    }
    private void insertDeviceLoginInfo(Long userId){
        Long createTime  = Instant.now().toEpochMilli();
        DeviceLoginInfoEntity deviceLoginInfo = new DeviceLoginInfoEntity();
        deviceLoginInfo.setUserId(userId);
        deviceLoginInfo.setDeviceId("A0AF937A-4804-4FC8-8C06-31611200D251");
        deviceLoginInfo.setCreateTime(createTime);
        deviceLoginInfo.setUpdateTime(createTime);
        deviceLoginInfoSql.insert(deviceLoginInfo);
    }*/
}
