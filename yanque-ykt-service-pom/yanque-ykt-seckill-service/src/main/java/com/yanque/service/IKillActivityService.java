package com.yanque.service;

import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.BasicPageVo;
import com.yanque.entity.KillActivity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 秒杀活动信息业务层接口
 *
 * @author cr
 */
public interface IKillActivityService extends IService<KillActivity> {

    ApiPageResponse<KillActivity> pagelist(BasicPageVo basicPageVo);

    void publish(Long killActivityId);
}
