package com.wzz.oa.biz.impl;

import com.wzz.oa.biz.ClaimVoucherBiz;
import com.wzz.oa.dao.ClaimVoucherDao;
import com.wzz.oa.dao.ClaimVoucherItemDao;
import com.wzz.oa.dao.DealRecordDao;
import com.wzz.oa.dao.EmployeeDao;
import com.wzz.oa.entity.ClaimVoucher;
import com.wzz.oa.entity.ClaimVoucherItem;
import com.wzz.oa.entity.DealRecord;
import com.wzz.oa.entity.Employee;
import com.wzz.oa.global.Contant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("claimVoucherBiz")
public class ClaimVoucherBizImpl implements ClaimVoucherBiz {
    @Autowired
    ClaimVoucherDao claimVoucherDao;

    @Autowired
    ClaimVoucherItemDao claimVoucherItemDao;

    @Autowired
    DealRecordDao dealRecordDao;

    @Autowired
    EmployeeDao employeeDao;

    // 被声明式事物封装，具有原子性
    @Override
    public void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        // 业务逻辑编写
        claimVoucher.setCreateTime(new Date());
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);

        // 保存到数据库
        claimVoucherDao.insert(claimVoucher);
        for(ClaimVoucherItem item : items){
            item.setClaimVoucherId(claimVoucher.getId());   // 报销单id在插入到数据库时已经自动生成
            claimVoucherItemDao.insert(item);
        }
    }

    @Override
    public ClaimVoucher get(int id) {
        return claimVoucherDao.select(id);
    }

    @Override
    public List<ClaimVoucherItem> getItems(int cvid) {
        return claimVoucherItemDao.selectByClaimVoucher(cvid);
    }

    @Override
    public List<DealRecord> getRecords(int cvid) {
        return dealRecordDao.selectByClaimVoucher(cvid);
    }

    @Override
    public List<ClaimVoucher> getForSelf(String sn) {
        return claimVoucherDao.selectByCreateSn(sn);
    }

    @Override
    public List<ClaimVoucher> getForDeal(String sn) {
        return claimVoucherDao.selectByNextDealSn(sn);
    }

    @Override
    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucherDao.update(claimVoucher);

        List<ClaimVoucherItem> olds = claimVoucherItemDao.selectByClaimVoucher(claimVoucher.getId());
        // 1.删除不需要的条目
        for (ClaimVoucherItem old : olds) {
            boolean isHave = false;
            for (ClaimVoucherItem item: items) {
                if(item.getId() == old.getId()) {
                    isHave = true;
                    break;
                }
            }
            if(!isHave)     // 旧的条目不在新的条目中则删除
                claimVoucherItemDao.delete(old.getId());
        }
        for (ClaimVoucherItem item: items) {
            item.setClaimVoucherId(claimVoucher.getId());
            if(item.getId() != null && item.getId() > 0) {
                // 2.修改已有的条目
                claimVoucherItemDao.update(item);
            }else{
                // 3.添加新增的条目
                claimVoucherItemDao.insert(item);
            }
        }

    }

    @Override
    public void submit(int id) {
        // 提交报销单操作
        ClaimVoucher claimVoucher = claimVoucherDao.select(id);
        Employee employee = employeeDao.select(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_SUBMIT);
        // 将待处理人变为创建人同部门的部门经理
        claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(
                employee.getDepartmentSn(), Contant.POST_FM).get(0).getSn());
        claimVoucherDao.update(claimVoucher);

        // 添加一条处理记录
        DealRecord dealRecord = new DealRecord();
        dealRecord.setDealWay(Contant.DEAL_SUBMIT);
        dealRecord.setDealSn(employee.getSn());
        dealRecord.setClaimVoucherId(id);
        dealRecord.setDealResult(Contant.CLAIMVOUCHER_SUBMIT);
        dealRecord.setComment("无");
        dealRecord.setDealTime(new Date());
        dealRecordDao.insert(dealRecord);
    }

    @Override
    public void deal(DealRecord dealRecord) {
        ClaimVoucher claimVoucher = claimVoucherDao.select(dealRecord.getClaimVoucherId());
        Employee employee = employeeDao.select(dealRecord.getDealSn());

        dealRecord.setDealTime(new Date());

        // 处理通过
        if(dealRecord.getDealWay().equals(Contant.DEAL_PASS)){
            // 直接通过
            if(claimVoucher.getTotalAmount() <= Contant.LIMIT_CHECK || employee.getPost().equals(Contant.POST_GM)) {
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_APPROVED);
                Employee e = employeeDao.selectByDepartmentAndPost(null, Contant.POST_CASHIER).get(0);
                claimVoucher.setNextDealSn(e.getSn());

                dealRecord.setDealResult(Contant.CLAIMVOUCHER_APPROVED);
            }else{   // 需要复审
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_RECHECK);
                Employee e = employeeDao.selectByDepartmentAndPost(null, Contant.POST_GM).get(0);
                claimVoucher.setNextDealSn(e.getSn());

                dealRecord.setDealResult(Contant.CLAIMVOUCHER_RECHECK);
            }
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_BACK)){  // 处理打回
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_BACK);
            claimVoucher.setNextDealSn(claimVoucher.getCreateSn());

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_BACK);
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_REJECT)){  // 处理拒绝
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_TERMINATED);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_TERMINATED);
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_PAID)){  // 处理打款
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_PAID);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_PAID);
        }

        claimVoucherDao.update(claimVoucher);
        dealRecordDao.insert(dealRecord);

    }
}
