
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.realms.manager.Manager;
import acme.realms.manager.ManagerRepository;

@Validator
public class ManagerValidator extends AbstractValidator<ValidManager, Manager> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidManager annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Manager manager, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (manager == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else {
			{
				boolean uniqueManager;
				Manager existingManager;

				existingManager = this.repository.findManagerByIdentifier(manager.getIdentifier());
				uniqueManager = existingManager == null || existingManager.equals(manager);

				super.state(context, uniqueManager, "identifier", "acme.validation.manager.duplicated-identifier.message");
			}
			{

				boolean correctIdentifier;

				correctIdentifier = manager.getIdentifier() == "" || manager.getIdentifier().charAt(0) == manager.getIdentity().getName().charAt(0) && manager.getIdentifier().charAt(1) == manager.getIdentity().getSurname().charAt(0);

				super.state(context, correctIdentifier, "identifier", "acme.validation.manager.correct-pattern");
			}
			{
				boolean correctExperience;

				correctExperience = manager.getYearsOfExperience() == null || manager.getYearsOfExperience() >= 0 && manager.getYearsOfExperience() <= 75;
				super.state(context, correctExperience, "yearsOfExperience", "acme.validation.manager.correct-experience");
			}
			{
				boolean pastDate;
				Date present = MomentHelper.getBaseMoment();

				pastDate = manager.getBirthDate().before(present);
				super.state(context, pastDate, "birthDate", "acme.validation.manager.past-birth-date");
			}
		}

		result = !super.hasErrors(context);

		return result;

	}
}
