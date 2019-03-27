package com.wzz.oa.biz.impl;

import com.wzz.oa.biz.DepartmentBiz;
import com.wzz.oa.biz.EmployeeBiz;
import com.wzz.oa.dao.DepartmentDao;
import com.wzz.oa.dao.EmployeeDao;
import com.wzz.oa.entity.Department;
import com.wzz.oa.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("employeeBiz")
public class EmployeeBizImpl implements EmployeeBiz {
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public void add(Employee employee) {
        employee.setPassword("000000");   // 业务规则，设置默认密码
        employeeDao.insert(employee);
    }

    @Override
    public void edit(Employee employee) {
        employeeDao.update(employee);
    }

    @Override
    public void remove(String sn) {
        employeeDao.delete(sn);
    }

    @Override
    public Employee get(String sn) {
        return employeeDao.select(sn);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDao.selectAll();
    }
}
