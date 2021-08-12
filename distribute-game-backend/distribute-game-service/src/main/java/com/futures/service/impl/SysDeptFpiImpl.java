package com.futures.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.entity.SysDept;
import com.futures.model.entity.SysUser;
import com.futures.service.SysDeptFpi;
import com.futures.service.SysDeptService;
import com.futures.service.SysUserService;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Component
public class SysDeptFpiImpl implements SysDeptFpi {
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public IPage<SysDept> queryDept(IPage page, String deptName) {
        QueryWrapper<SysDept> sysDeptQueryWrapper = new QueryWrapper<>();
        sysDeptQueryWrapper = sysDeptQueryWrapper.eq("deleted",0).eq("parent_id",0);
        if(!StringUtils.isEmpty(deptName)){
            sysDeptQueryWrapper = sysDeptQueryWrapper.like("name",deptName);
        }
        sysDeptQueryWrapper = sysDeptQueryWrapper.orderByDesc("createDate");
        IPage<SysDept> deptPage = sysDeptService.page(page, sysDeptQueryWrapper);
        if(deptPage.getRecords() != null && deptPage.getRecords().size()>0){
            for(SysDept sysDept:deptPage.getRecords()){
                List<SysDept> depts = getChildDeptList(sysDept.getId());
                if(null!=depts){
                    sysDept.setDeptList(depts);
                }
            }
        }
        return deptPage;
    }

    private List<SysDept> getChildDeptList(Long id) {
        List<SysDept> depts = sysDeptService.list(new QueryWrapper<SysDept>().eq("parent_id",id));
        if(depts!=null){
            for(SysDept sysDept:depts){
                List<SysDept> list = getChildDeptList(sysDept.getId());
                if(null!=list) {
                    sysDept.setDeptList(list);
                }
            }
        }
        return depts;
    }

    @Override
    @Transactional(rollbackFor = {BizException.class,Exception.class, RuntimeException.class})
    public void saveDept(SysDept sysDept) {
        if(StringUtils.isEmpty(sysDept.getName())){
            throw new BizException("部门名称不能为空");
        }
        if (sysDeptService.exists("name", sysDept.getName())) {
            throw new BizException("不允许重复添加部门");
        }
        if(sysDept.getParentId()==null){
            sysDept.setParentId(0L);
        }
        sysDept.setCreateDate(new Date());
        sysDept.setUpdateDate(new Date());
        sysDeptService.save(sysDept);
    }

    @Override
    @Transactional(rollbackFor = {BizException.class, Exception.class, RuntimeException.class})
    public void modifyDept(SysDept sysDept) {
        if(sysDeptService.exists(new QueryWrapper<SysDept>().eq("deleted",0).eq("name", sysDept.getName()).ne("id",sysDept.getId()))) {
            throw new BizException("已存在该部门，不能重复添加");
        }
        sysDeptService.updateById(sysDept);
    }

    @Override
    @Transactional(rollbackFor = {BizException.class, Exception.class, RuntimeException.class})
    public boolean deleteDept(Long id) {
        List<SysDept> deptList = sysDeptService.list(new QueryWrapper<SysDept>().eq("deleted", 0).eq("parent_id", id));
        if(null!=deptList && deptList.size()>0){
            throw new BizException("该部门下还有子级部门，不能删除");
        }
        List<SysUser> userList = sysUserService.list(new QueryWrapper<SysUser>().eq("deleted", 0).eq("dept_id", id));
        if(null!=userList && userList.size()>0){
            throw new BizException("该部门下还有人员，不能删除");
        }
        return sysDeptService.removeById(id);
    }
}
