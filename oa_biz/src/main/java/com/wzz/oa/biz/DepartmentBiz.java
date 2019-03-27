package com.wzz.oa.biz;

import com.wzz.oa.entity.Department;

import java.util.List;

/**
 * 部门业务逻辑接口
 */
public interface DepartmentBiz {
    /**
     * 增加一条部门信息
     * @param department
     */
    void add(Department department);

    /**
     * 编辑一条部门信息
     * @param department
     */
    void edit(Department department);

    /**
     * 删除一条部门信息
     * @param sn
     */
    void remove(String sn);

    /**
     * 根据部门编号获取部门对象
     * @param sn
     * @return
     */
    Department get(String sn);

    /**
     * 获取所有部门对象
     * @return
     */
    List<Department> getAll();
}
