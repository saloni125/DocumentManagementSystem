package com.comviva.DocumentManagementSystem.bean;

import java.util.Arrays;

import javax.persistence.*;

@Entity
@Table(name = "docs")
public class Document {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Integer docId;
	private String docName;
	private String docType;
	private String docCategory;
	
	

	@ManyToOne(targetEntity=User.class,cascade=CascadeType.ALL)
	@JoinColumn(name="userId",insertable=false,updatable=false)
	private User user;
	
	@Column(name = "userId")
    private int userId;
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int i) {
		this.userId = i;
	}
	@Column(name = "doc_category")
	public String getDocCategory() {
		return docCategory;
	}

	public void setDocCategory(String docCategory) {
		this.docCategory = docCategory;
	}
	@Lob
	private byte[] data;

	@Column(name = "doc_id", nullable = false)
	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public Document(String docName, String docType, User user, byte[] data) {
		super();
		this.docName = docName;
		this.docType = docType;
		this.user = user;
		this.data = data;
	}

	@Column(name = "doc_name")
	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}
	@Column(name = "doc_type")
	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@Column(name = "data")
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	/*
	 * @Override public String toString() { return "Document [doc_id=" + docId +
	 * ", docName=" + docName + ", docType=" + docType + ", data=" +
	 * Arrays.toString(data) + "]"; }
	 */

	/*
	 * public Document(String docName, String docType, byte[] data) { super();
	 * this.docName = docName; this.docType = docType; this.data = data; }
	 */
	
	public Document()
	{
		
	}

	public Document(String docName, String docType,
			byte[] data) {
		super();
		this.docName = docName;
		this.docType = docType;
		this.data = data;
	}

	@Override
	public String toString() {
		return "Document [docId=" + docId + ", docName=" + docName + ", docType=" + docType + ", docCategory="
				+ docCategory + ", user=" + user + ", userId=" + userId + ", data=" + Arrays.toString(data) + "]";
	}
	

}
