package com.jeebase.system.controlSys.planManage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import java.io.Serializable;
import java.sql.Date;


@TableName("mes_advice")
public class MesAdviceEntity implements Serializable {

        private static final long serialVersionUID = 1L;

        @TableId(value = "id", type = IdType.AUTO)
        private Integer id;

        @TableField("state")
        private String state;

        @TableField("create_user")
        private String createUser;

        @TableField("create_time")
        private Date createTime;

        @TableField("plan_total")
        private int planTotal;

        @TableField("advice_order")
        private int adviceOrder;

        public String getState() {
                return state;
        }

        public void setState(String state) {
                this.state = state;
        }

        public Date getCreateTime() {
                return createTime;
        }

        public void setCreateTime(Date createTime) {
                this.createTime = createTime;
        }

        public String getCreateUser() {
                return createUser;
        }

        public void setCreateUser(String createUser) {
                this.createUser = createUser;
        }

        public int getAdviceOrder() {
                return adviceOrder;
        }

        public void setAdviceOrder(int adviceOrder) {
                this.adviceOrder = adviceOrder;
        }

        public int getPlanTotal() {
                return planTotal;
        }

        public void setPlanTotal(int planTotal) {
                this.planTotal = planTotal;
        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }
}
