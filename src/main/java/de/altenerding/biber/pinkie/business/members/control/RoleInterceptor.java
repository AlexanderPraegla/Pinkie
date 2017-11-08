package de.altenerding.biber.pinkie.business.members.control;

import de.altenerding.biber.pinkie.business.login.control.Authenticator;
import de.altenerding.biber.pinkie.business.members.entity.Access;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

@Access
@Interceptor
public class RoleInterceptor implements Serializable {

	private Authenticator authenticator;

	@AroundInvoke
	public Object intercept(InvocationContext ic) throws Exception {

		Access annotation = ic.getMethod().getAnnotation(Access.class);

		if (annotation == null) {
			return ic.proceed();
		}

		if (authenticator.authenticateRole(annotation.role())) {
			return ic.proceed();
		}

		throw new Exception("Not allowed to perform this action");
	}

	@Inject
	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}
}
