package de.altenerding.biber.pinkie.business.converter;

import de.altenerding.biber.pinkie.business.global.boundary.BaseService;
import de.altenerding.biber.pinkie.business.global.entity.BaseStringIdEntity;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;

@ManagedBean
@ApplicationScoped
@SuppressWarnings({ "rawtypes", "unchecked" }) // We don't care about BaseStringIdEntity's actual type here.
@Named(value = "baseStringIdEntityConverter")
public class BaseStringIdEntityConverter implements Converter {

	@EJB
	private BaseService baseService;

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof BaseStringIdEntity) {
			return ((BaseStringIdEntity) value).getId();
		} else {
			throw new ConverterException(new FacesMessage(String.format("%s is not a valid User", value)));
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}

		try {
			Class<?> type = component.getValueExpression("value").getType(context.getELContext());
			return baseService.findByStringId((Class<BaseStringIdEntity>) type, value);
		} catch (NumberFormatException e) {
			throw new ConverterException(new FacesMessage(String.format("%s is not a valid ID of BaseStringIdEntity", value)), e);
		}
	}

}
