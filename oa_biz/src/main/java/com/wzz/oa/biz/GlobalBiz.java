package com.wzz.oa.biz;

import com.wzz.oa.entity.Employee;

public interface GlobalBiz {
    /**
     * 用户登录功能
     * @param sn
     * @param password
     * @return
     */
    Employee login(String sn, String password);

    /**
     * 修改密码功能
     * @param employee
     */
    void changePassword(Employee employee);
}
