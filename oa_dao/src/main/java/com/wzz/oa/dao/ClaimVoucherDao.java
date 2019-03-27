package com.wzz.oa.dao;

import com.wzz.oa.entity.ClaimVoucher;
import com.wzz.oa.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("claimVoucherDao")
public interface ClaimVoucherDao {

    void insert(ClaimVoucher claimVoucher);

    void update(ClaimVoucher claimVoucher);

    void delete(int id);

    ClaimVoucher select(int id);

    /**
     * 获取创建人的所有报销单
     * @param csn
     * @return
     */
    List<ClaimVoucher> selectByCreateSn(String csn);

    /**
     * 获取处理人的所有可处理报销单
     * @param ndsn
     * @return
     */
    List<ClaimVoucher> selectByNextDealSn(String ndsn);

}
