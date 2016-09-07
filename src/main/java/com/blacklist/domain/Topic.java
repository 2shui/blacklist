package com.blacklist.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 主题
 *
 * @author 12465
 * @version 1.0
 * @createDate 2016/7/18 14:04
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

	@Column(name = "createTime")
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

}
