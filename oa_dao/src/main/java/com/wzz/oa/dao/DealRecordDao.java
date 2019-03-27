package com.wzz.oa.dao;

import com.wzz.oa.entity.ClaimVoucherItem;
import com.wzz.oa.entity.DealRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("dealRecordDao")
public interface DealRecordDao {

    void insert(DealRecord dealRecord);

    /**
     * 根据报销单编号查询所有处理记录
     * @param cvid
     * @return
     */
    List<DealRecord> selectByClaimVoucher(int cvid);

}
