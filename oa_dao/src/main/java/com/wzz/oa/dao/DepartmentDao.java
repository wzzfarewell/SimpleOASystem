package com.wzz.oa.dao;

import com.wzz.oa.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门数据层交互接口
 */
//@Repository("departmentDao")
public interface DepartmentDao {
    /**
     * 向数据库插入一条部门信息
     * @param department
     */
    void insert(Department department);

    /**
     * 更新数据库中指定的部门信息
     * @param department
     */
    void update(Department department);

    /**
     * 删除数据库中指定的部门信息
     * @param sn
     */
    void delete(String sn);

    /**
     * 返回数据库指定的部门对象
     * @param sn
     * @return
     */
    Department select(String sn);

    /**
     * 返回数据库中所有部门对象的List
     * @return
     */
    List<Department> selectAll();
}
