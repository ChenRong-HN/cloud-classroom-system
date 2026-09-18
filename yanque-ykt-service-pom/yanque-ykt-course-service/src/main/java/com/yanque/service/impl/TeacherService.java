package com.yanque.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanque.common.constant.PageConstant;
import com.yanque.common.vo.ApiPageResponse;
import com.yanque.common.vo.BasicPageVo;
import com.yanque.exp.BusinessErrorType;
import com.yanque.exp.BusinessException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yanque.mapper.TeacherMapper;
import com.yanque.entity.Teacher;
import com.yanque.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 老师业务层接口实现类
 *
 * @author cr
 */
@Service
public class TeacherService extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    // 注入Teacher持久层接口实现类
    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public ApiPageResponse<Teacher> pageList(BasicPageVo basicPageVo) {
        String key = basicPageVo.getKeyword();
        if (ObjUtil.isNull(basicPageVo.getPage()))
            basicPageVo.setPage(1L);
        long page = basicPageVo.getPage();

        // 构建分页参数对象
        Page<Teacher> teacherPage = new Page<>(page, PageConstant.DEFAULT_PAGE_DATA_COUNT);

        // 构建查询条件参数对象
        LambdaQueryWrapper<Teacher> queryWrapper = Wrappers.<Teacher>lambdaQuery().like(StrUtil.isNotBlank(key), Teacher::getName, key);

        teacherPage = page(teacherPage, queryWrapper);
        return ApiPageResponse.<Teacher>builder().total(teacherPage.getTotal()).rows(teacherPage.getRecords()).build();
    }

    @Override
    public boolean save(Teacher teacher) {

        // 校验该讲师是否已存在
        long count = count(Wrappers.<Teacher>lambdaQuery().eq(StrUtil.isNotBlank(teacher.getName()), Teacher::getName, teacher.getName()));
        Assert.equals(count, 0L, () -> new BusinessException(BusinessErrorType.TEACHER_EXISTS));
        return super.save(teacher);
    }
}
