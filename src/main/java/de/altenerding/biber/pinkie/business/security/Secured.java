package de.altenerding.biber.pinkie.business.security;

import de.altenerding.biber.pinkie.business.members.entity.PermissionType;

import javax.enterprise.util.Nonbinding;
import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static de.altenerding.biber.pinkie.business.members.entity.PermissionType.UNSECURED;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Secured {
    @Nonbinding
    PermissionType value() default UNSECURED;
}
