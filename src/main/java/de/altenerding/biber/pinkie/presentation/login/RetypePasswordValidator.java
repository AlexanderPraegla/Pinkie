package de.altenerding.biber.pinkie.presentation.login;

import org.apache.commons.lang3.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value="retypePasswordValidator")
public class RetypePasswordValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		// Obtain the client ID of the first field from f:attribute.
		System.out.println(component.getFamily());
		String field1Id = (String) component.getAttributes().get("password1");

		// Find the actual JSF component for the client ID.
		UIInput textInput = (UIInput) context.getViewRoot().findComponent("registerFormId:password");
		if (textInput == null)
			throw new IllegalArgumentException(String.format("Unable to find component with id %s",field1Id));
		// Get its value, the entered text of the first field.
		String passwordRepeated = (String) textInput.getValue();

		// Cast the value of the entered text of the second field back to String.
		String confirm = (String) value;

		if (StringUtils.equals(confirm, passwordRepeated)) {
			FacesMessage message = new FacesMessage("Das Passwort stimmt nicht überein");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
}
