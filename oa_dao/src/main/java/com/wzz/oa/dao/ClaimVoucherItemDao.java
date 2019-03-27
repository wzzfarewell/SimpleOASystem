package com.wzz.oa.dao;

import com.wzz.oa.entity.ClaimVoucher;
import com.wzz.oa.entity.ClaimVoucherItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("claimVoucherItemDao")
public interface ClaimVoucherItemDao {

    void insert(ClaimVoucherItem claimVoucherItem);

    void update(ClaimVoucherItem claimVoucherItem);

    void delete(int id);

    /**
     * 根据报销单编号查询所有报销单条目
     * @param cvid
     * @return
     */
    List<ClaimVoucherItem> selectByClaimVoucher(int cvid);

}
