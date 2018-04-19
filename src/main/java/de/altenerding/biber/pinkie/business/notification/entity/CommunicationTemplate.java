package de.altenerding.biber.pinkie.business.notification.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@NamedQueries({
		@NamedQuery(name = "CommunicationTemplate.findByType",
				query = "SELECT c FROM CommunicationTemplate c WHERE c.communicationType = :communicationType AND c.templateType = :templateType")
})
@Entity
@Table(name = "communication_template")
@IdClass(CommunicationTemplateId.class)
public class CommunicationTemplate {

	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "communication_type")
	private CommunicationType communicationType;
	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "template_type")
	private TemplateType templateType;
	@Column(columnDefinition = "varchar")
	private String body;
	@Column(columnDefinition = "varchar")
	private String subject;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;

	@PrePersist
	protected void onPersist() {
		if (createdOn == null) {
			createdOn = new Date();
		}
	}

	public CommunicationType getCommunicationType() {
		return communicationType;
	}

	public void setCommunicationType(CommunicationType communicationType) {
		this.communicationType = communicationType;
	}

	public TemplateType getTemplateType() {
		return templateType;
	}

	public void setTemplateType(TemplateType templateType) {
		this.templateType = templateType;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
}
