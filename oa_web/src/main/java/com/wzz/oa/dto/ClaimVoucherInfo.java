package com.wzz.oa.dto;

import com.wzz.oa.entity.ClaimVoucher;
import com.wzz.oa.entity.ClaimVoucherItem;

import java.util.List;

/**
 * 要收集的数据 用户提交的信息
 */
public class ClaimVoucherInfo {
    private ClaimVoucher claimVoucher;
    private List<ClaimVoucherItem> items;

    public ClaimVoucher getClaimVoucher() {
        return claimVoucher;
    }

    public void setClaimVoucher(ClaimVoucher claimVoucher) {
        this.claimVoucher = claimVoucher;
    }

    public List<ClaimVoucherItem> getItems() {
        return items;
    }

    public void setItems(List<ClaimVoucherItem> items) {
        this.items = items;
    }
}
