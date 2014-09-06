package org.springone2gx.ast;

import org.codehaus.groovy.transform.GroovyASTTransformationClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jbaruch
 * @since 06/09/2014
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@GroovyASTTransformationClass(classes = MessageAdderGroovyAstTransformation.class)
public @interface Messenger {
    boolean shout() default false;
}
