package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.entity.SysDept;

public interface SysDeptFpi {
    /**
     * 查询部门
     * @param page
     * @param deptName
     * @return
     */
    IPage<SysDept> queryDept(IPage page, String deptName);

    /**
     * 添加部门
     * @param sysDept
     */
    void saveDept(SysDept sysDept);

    /**
     * 编辑部门
     * @param sysDept
     */
    void modifyDept(SysDept sysDept);

    /**
     * 删除部门
     * @param id
     * @return
     */
    boolean deleteDept(Long id);

}
