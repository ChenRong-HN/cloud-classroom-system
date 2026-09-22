package com.yanque.service.impl;

import java.util.List;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanque.common.vo.ApiPageResponse;
import com.yanque.entity.vo.CourseQueryVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.CourseUserShowListMapper;
import com.yanque.entity.CourseUserShowList;
import com.yanque.service.ICourseUserShowListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 用户端课程展示列业务层接口实现类
 *
 * @author cr
 */
@Service
public class CourseUserShowListService extends ServiceImpl<CourseUserShowListMapper,CourseUserShowList> implements ICourseUserShowListService {

    // 注入CourseUserShowList持久层接口实现类
    @Resource
    private CourseUserShowListMapper courseUserShowListMapper;

    @Override
    public ApiPageResponse<CourseUserShowList> pageList(CourseQueryVo courseQueryVo) {
        // 封装分页参数
        Page<CourseUserShowList> page = new Page<>(courseQueryVo.getPage(),courseQueryVo.getRows());
        // 封装查询条件
        LambdaQueryWrapper<CourseUserShowList> courseUserShowListLambdaQueryWrapper = Wrappers.lambdaQuery();
        courseUserShowListLambdaQueryWrapper.like(StrUtil.isNotBlank(courseQueryVo.getKeyword()),CourseUserShowList::getName,courseQueryVo.getKeyword());
        courseUserShowListLambdaQueryWrapper.eq(ObjUtil.isNotNull(courseQueryVo.getCourseTypeId()),CourseUserShowList::getCourseTypeId,courseQueryVo.getCourseTypeId());
        courseUserShowListLambdaQueryWrapper.eq(StrUtil.isNotBlank(courseQueryVo.getGradeName()),CourseUserShowList::getGradeName,courseQueryVo.getGradeName());
        courseUserShowListLambdaQueryWrapper.eq(ObjUtil.isNotNull(courseQueryVo.getChargeName()),CourseUserShowList::getCharge,courseQueryVo.getChargeName());
        courseUserShowListLambdaQueryWrapper.gt(ObjUtil.isNotNull(courseQueryVo.getPriceMin()),CourseUserShowList::getPrice,courseQueryVo.getPriceMin());
        courseUserShowListLambdaQueryWrapper.lt(ObjUtil.isNotNull(courseQueryVo.getPriceMax()),CourseUserShowList::getPrice,courseQueryVo.getPriceMax());
        // 排序方式以及排序字段
        // 注意:switch不支持null分支,必须先判空,否则前端清空排序条件(如点击"清除条件")时会抛NPE
        if (StrUtil.isNotBlank(courseQueryVo.getSortField())) {
            if (StrUtil.equalsIgnoreCase(courseQueryVo.getSortType(), "asc")) {
                switch (courseQueryVo.getSortField()) {
                    case "xl" -> courseUserShowListLambdaQueryWrapper.orderByAsc(CourseUserShowList::getSaleCount); // 销量数据
                    case "xp" -> courseUserShowListLambdaQueryWrapper.orderByAsc(CourseUserShowList::getOnlineTime); // 发布时间
                    case "pl" -> courseUserShowListLambdaQueryWrapper.orderByAsc(CourseUserShowList::getCommentCount); // 评论数数据
                    case "jg" -> courseUserShowListLambdaQueryWrapper.orderByAsc(CourseUserShowList::getPrice); // 价格数据
                    case "rq" -> courseUserShowListLambdaQueryWrapper.orderByAsc(CourseUserShowList::getViewCount); // 浏览量数据
                    default -> courseUserShowListLambdaQueryWrapper.orderByDesc(CourseUserShowList::getId); // 未知排序字段时兜底,保证分页结果稳定
                }
            } else {
                switch (courseQueryVo.getSortField()) {
                    case "xl" -> courseUserShowListLambdaQueryWrapper.orderByDesc(CourseUserShowList::getSaleCount); // 销量数据
                    case "xp" -> courseUserShowListLambdaQueryWrapper.orderByDesc(CourseUserShowList::getOnlineTime); // 发布时间
                    case "pl" -> courseUserShowListLambdaQueryWrapper.orderByDesc(CourseUserShowList::getCommentCount); // 评论数数据
                    case "jg" -> courseUserShowListLambdaQueryWrapper.orderByDesc(CourseUserShowList::getPrice); // 价格数据
                    case "rq" -> courseUserShowListLambdaQueryWrapper.orderByDesc(CourseUserShowList::getViewCount); // 浏览量数据
                    default -> courseUserShowListLambdaQueryWrapper.orderByDesc(CourseUserShowList::getId); // 未知排序字段时兜底,保证分页结果稳定
                }
            }
        } else {
            // 未指定排序字段时按主键倒序,避免无ORDER BY的分页出现记录重复或遗漏
            courseUserShowListLambdaQueryWrapper.orderByDesc(CourseUserShowList::getId);
        }
        Page<CourseUserShowList> courseUserShowListPage = page(page, courseUserShowListLambdaQueryWrapper);
        // 封装全局通用分页数据返回结果
        return ApiPageResponse.<CourseUserShowList>builder().total(courseUserShowListPage.getTotal()).rows(courseUserShowListPage.getRecords()).build();
    }
}
