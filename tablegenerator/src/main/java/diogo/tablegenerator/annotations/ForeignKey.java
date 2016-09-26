package diogo.tablegenerator.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import diogo.tablegenerator.processor.ColumnNames;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ForeignKey {
	String name();
	Class<?> references();
	String foreignKey() default ColumnNames.ID_NAME;
}