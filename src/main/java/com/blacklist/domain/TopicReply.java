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
 * 回复
 *
 * @author
 * @version 1.0
 * @createDate 2016-9-19 14:12:47
 */
@Entity
@Table(name = "it_topic_reply")
public class TopicReply implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8058622767273979322L;
	
	/**
     * 评论Id
     */
    private Long id;
    
    /**
     * 主题ID
     */
    private Long topicId;
    
    /**
     * IP
     */
    private String ip;
    
    /**
     * IP所在地
     */
    private String citySN;
    
    /**
     * 时间
     */
    private Date time;
    
    /**
     * 评论内容
     */
    private String intro;
    
    /**
     * 点赞数
     */
    private Integer upNum;
    
    /**
     * 反对数
     */
    private Integer downNum;
    
    /**
     * 状态
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

	@Column(name = "topic_id")
	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	@Column(name = "ip")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "citySN")
	public String getCitySN() {
		return citySN;
	}

	public void setCitySN(String citySN) {
		this.citySN = citySN;
	}

	@Column(name = "create_time")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Column(name = "intro")
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	@Column(name = "up_num")
	public Integer getUpNum() {
		return upNum;
	}

	public void setUpNum(Integer upNum) {
		this.upNum = upNum;
	}

	@Column(name = "down_num")
	public Integer getDownNum() {
		return downNum;
	}

	public void setDownNum(Integer downNum) {
		this.downNum = downNum;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
    
}
