package com.yuri.luis.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.yuri.luis.dto.AuthorDTO;

@Document
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private String text;
	private Date date;
	private AuthorDTO authorComment;
	
	public Comment() {
		
	}

	public Comment(String id,String text, Date date, AuthorDTO authorComment) {
		super();
		this.id = id;
		this.text = text;
		this.date = date;
		this.setAuthorComment(authorComment);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public AuthorDTO getAuthorComment() {
		return authorComment;
	}

	public void setAuthorComment(AuthorDTO authorComment) {
		this.authorComment = authorComment;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
