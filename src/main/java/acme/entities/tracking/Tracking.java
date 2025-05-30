
package acme.entities.tracking;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidScore;
import acme.constraints.ValidOptionalLongText;
import acme.constraints.ValidShortText;
import acme.constraints.tracking.ValidTracking;
import acme.entities.claim.Claim;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidTracking
@Table(indexes = {
	@Index(columnList = "claim_id"), //
	@Index(columnList = "claim_id, lastUpdateMoment"), //
	@Index(columnList = "claim_id, resolutionPercentage")
})
public class Tracking extends AbstractEntity {

	// Serialisation version --------------------------------------------------
	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidMoment(past = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				lastUpdateMoment;

	@Mandatory
	@ValidShortText
	@Automapped
	private String				step;

	@Mandatory
	@ValidScore
	@Automapped
	private Double				resolutionPercentage;

	@Mandatory
	@Valid
	@Automapped
	private TrackingStatus		indicator;

	@Optional
	@ValidOptionalLongText
	@Automapped
	private String				resolution;

	@Mandatory
	@Automapped
	private boolean				draftMode;

	// Relationships ----------------------------------------------------------

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Claim				claim;
}
