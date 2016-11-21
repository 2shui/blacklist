package com.blacklist.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 博客文章
 *
 * @author 
 * @version 1.0
 * @createDate 2016-11-11 14:28:22
 */
@Entity
@Table(name = "blog_article")
public class BlogArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    private Long id;

    /**
     * 标题
     * */
    private String title;
    
    /**
     * 内容
     */
    private String content;

    /**
     * 作者
     */
    private String author;
    
    /**
     * 外部链接
     * */
    private String source;
    
    /**
     * 创建时间
     * */
    private Date createTime;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "author")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	@Column(name = "source")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


}
