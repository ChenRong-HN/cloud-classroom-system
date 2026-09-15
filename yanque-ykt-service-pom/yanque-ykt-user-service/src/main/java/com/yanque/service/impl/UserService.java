package com.yanque.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yanque.common.constant.RedisConstant;
import com.yanque.common.vo.ApiResponse;
import com.yanque.entity.Login;
import com.yanque.entity.UserAccount;
import com.yanque.entity.UserBaseInfo;
import com.yanque.entity.vo.UserReqVo;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import com.yanque.feign.client.UaaLoginFeignClient;
import com.yanque.service.IUserAccountService;
import com.yanque.service.IUserBaseInfoService;
import jakarta.annotation.Resource;
import org.apache.seata.spring.annotation.GlobalTransactional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.yanque.mapper.UserMapper;
import com.yanque.entity.User;
import com.yanque.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanque.tool.BitStatesConstant;

/**
 * 会员登录账号业务层接口实现类
 *
 * @author cr
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    // 注入User持久层接口实现类
    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UaaLoginFeignClient uaaLoginFeignClient;

    @Resource
    private IUserBaseInfoService userBaseInfoService;

    @Resource
    private IUserAccountService userAccountService;

    @Override
    @GlobalTransactional
    public void register(UserReqVo userReqVo) {
        // 首先判断用户是否已经存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjUtil.isNotNull(userReqVo.getEmail()), User::getEmail, userReqVo.getEmail());
        Long count = userMapper.selectCount(queryWrapper);
        // 通过断言判断 用户不存在（count == 0）否则抛出异常
        Assert.equals(count ,0L, () -> new BusinessException(BusinessErrorType.USER_ACCOUNT_EXISTS));
        // 进行数据校验
        String registerValidateCodeKey = RedisConstant.MAIL_REGISTRY_CODE_KEY.concat(userReqVo.getEmail());
        String registerValidateCodeValue = (String) redisTemplate.opsForValue().get(registerValidateCodeKey);
        Assert.notNull(registerValidateCodeValue, () -> new BusinessException(BusinessErrorType.EMAIL_VALIDATE_CODE_EXPIRED));
        Assert.equals(userReqVo.getEmailCode(), registerValidateCodeValue, () -> new BusinessException(BusinessErrorType.EMAIL_VALIDATE_CODE_ERROR));

        // 构建Login对象
        Login login = Login.builder().username(userReqVo.getEmail())
                .password(userReqVo.getPassword())
                .accountNonExpired(1)
                .accountNonLocked(1)
                .credentialsNonExpired(1)
                .enabled(1)
                .type(1)
                .build();
        ApiResponse<Long> apiResponse = uaaLoginFeignClient.save(login);
        Assert.isTrue(ObjUtil.isNotNull(apiResponse) && apiResponse.getSuccess(),()->new BusinessException(BusinessErrorType.REMOTE_SERVICE_ERROR));

        Long now = System.currentTimeMillis();
        // 构建User对象
        User user = User.builder().email(userReqVo.getEmail()).nickName(userReqVo.getEmail())
                .bitState(BitStatesConstant.addState(BitStatesConstant.OP_REGISTED, BitStatesConstant.OP_AUTHED_EMAIL))
                .createTime(now).updateTime(now).loginId(apiResponse.getData()).secLevel(1L).build();
        userMapper.insert(user);

        // 构建UserInfo对象
        UserBaseInfo userBaseInfo = UserBaseInfo.builder().regChannel(userReqVo.getRegChannel()).level(1L).id(user.getId())
                .createTime(now).updateTime(now).growScore(0L).build();
        // 构建UserAccount对象
        UserAccount userAccount = UserAccount.builder().id(user.getId()).createTime(now).updateTime(now).frozenAmount(0L)
                .usableAmount(0L).password("123456").build();
        // 保存数据
        userBaseInfoService.save(userBaseInfo);
        userAccountService.save(userAccount);

        // 删除验证码
        redisTemplate.delete(registerValidateCodeKey);
    }
}
