package com.wzz.oa.biz;

import com.wzz.oa.entity.Department;
import com.wzz.oa.entity.Employee;

import java.util.List;

/**
 * 员工业务逻辑接口
 */
public interface EmployeeBiz {
    /**
     * 增加一条员工信息
     * @param employee
     */
    void add(Employee employee);

    /**
     * 编辑一条员工信息
     * @param employee
     */
    void edit(Employee employee);

    /**
     * 删除一条员工信息
     * @param sn
     */
    void remove(String sn);

    /**
     * 根据员工编号获取部门对象
     * @param sn
     * @return
     */
    Employee get(String sn);

    /**
     * 获取所有员工对象
     * @return
     */
    List<Employee> getAll();
}
