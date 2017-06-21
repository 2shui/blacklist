package com.blacklist.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author 
 */
@Entity
@Table(name = "mini_program_remind")
public class ProgramRemind implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;
    /**
     * 日期
     */
    private String time;
    /**
     * 是否农历 
     */
    private boolean isLunar;
    /**
     * 是否每年提醒
     */
    private boolean isEveryYear;
    /**
     * 提醒时间 week-提前7天 day-当天提醒
     */
    private String remindTime;
    /**
     * 小程序openId
     */
    private String openId;
    /**
     * unionId
     */
    private String unionId;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 状态 1-正常 2-删除
     */
    private Integer status;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	@Column(name = "time")
	public String getTime() {
		return time;
	}

	@Column(name = "is_lunar")
	public boolean getIsLunar() {
		return isLunar;
	}

	@Column(name = "is_every_year")
	public boolean getIsEveryYear() {
		return isEveryYear;
	}

	@Column(name = "remind_time")
	public String getRemindTime() {
		return remindTime;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setIsLunar(boolean isLunar) {
		this.isLunar = isLunar;
	}

	public void setIsEveryYear(boolean isEveryYear) {
		this.isEveryYear = isEveryYear;
	}

	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "open_id")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Column(name = "union_id")
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
