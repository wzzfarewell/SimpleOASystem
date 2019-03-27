package com.wzz.oa.biz;

import com.wzz.oa.entity.ClaimVoucher;
import com.wzz.oa.entity.ClaimVoucherItem;
import com.wzz.oa.entity.DealRecord;

import java.util.List;

public interface ClaimVoucherBiz {
    /**
     * 保存报销单（包括报销单条目）
     * @param claimVoucher
     * @param items
     */
    void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

    /**
     * 根据报销单id获取报销单
     * @param id
     * @return
     */
    ClaimVoucher get(int id);

    /**
     * 根据报销单id获取报销单条目
     * @param cvid
     * @return
     */
    List<ClaimVoucherItem> getItems(int cvid);

    /**
     * 根据报销单id获取报销单处理记录
     * @param cvid
     * @return
     */
    List<DealRecord> getRecords(int cvid);

    /**
     * 获取自己的报销单
     * @param sn 员工编号
     * @return
     */
    List<ClaimVoucher> getForSelf(String sn);

    /**
     * 获取待处理的报销单
     * @param sn 员工编号
     * @return
     */
    List<ClaimVoucher> getForDeal(String sn);

    /**
     * 修改报销单（包括报销单条目）
     * @param claimVoucher
     * @param items
     */
    void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

    /**
     * 提交报销单
     * @param id
     */
    void submit(int id);

    /**
     * 审核报销单
     * @param dealRecord
     */
    void deal(DealRecord dealRecord);
}
