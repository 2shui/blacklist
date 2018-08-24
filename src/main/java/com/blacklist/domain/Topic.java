package com.blacklist.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 主题
 *
 * @author 
 * @version 1.0
 * @createDate 2016-9-09 14:12:35
 */
@Entity
@Table(name = "it_topic")
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主题Id
     */
    private Long id;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 主题状态
     */
    private Integer status;

    /**
     * 公司名称
     */
    private String company;

    /**
     * 创建时间
     */
    private Date createTime;
    @Transient
    private String shortPath;

    /**
     * 简述
     * */
    private String sketch;
    
    /**
     * 描述
     */
    private String intro;

    /**
     * 联系方式
     */
    private String contact;

    /**
     * 浏览数
     */
    private Integer accessNum;
    
    /**
     * 点赞数
     */
    private Integer eulogyNum;
    
    /**
     * 驳斥数
     */
    private Integer refuteNum;
    
    /**
     * 辟谣 1-未辟谣 2-辟谣中 3-已辟谣
     * */
    private Integer refute;


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "company")
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "sketch")
	public String getSketch() {
		return sketch;
	}

	public void setSketch(String sketch) {
		this.sketch = sketch;
	}

	@Column(name = "intro")
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	@Column(name = "contact")
	public String getContact() {
		return contact;
	}

	@Column(name = "refute")
	public Integer getRefute() {
		return refute;
	}

	public void setRefute(Integer refute) {
		this.refute = refute;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "accessNum")
	public Integer getAccessNum() {
		return accessNum;
	}

	public void setAccessNum(Integer accessNum) {
		this.accessNum = accessNum;
	}

	@Column(name = "eulogyNum")
	public Integer getEulogyNum() {
		return eulogyNum;
	}

	public void setEulogyNum(Integer eulogyNum) {
		this.eulogyNum = eulogyNum;
	}

	@Column(name = "refuteNum")
	public Integer getRefuteNum() {
		return refuteNum;
	}

	public void setRefuteNum(Integer refuteNum) {
		this.refuteNum = refuteNum;
	}

	@Transient
	public String getShortPath() {
		return shortPath;
	}

	public void setShortPath(String shortPath) {
		this.shortPath = shortPath;
	}

}
