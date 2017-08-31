package com.blacklist.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "it_water")
public class Water implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    private Long id;
    /**
     * 站内地址
     * */
    private String url;
    /**
     * 源地址
     * */
    private String referrer;
    private String title;
    private String titleDesc;
    private Date createTime;
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

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "referrer")
	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "access_num")
	public Integer getAccessNum() {
		return accessNum;
	}

	public void setAccessNum(Integer accessNum) {
		this.accessNum = accessNum;
	}

	@Column(name = "title_desc")
	public String getTitleDesc() {
		return titleDesc;
	}

	public void setTitleDesc(String titleDesc) {
		this.titleDesc = titleDesc;
	}


}
