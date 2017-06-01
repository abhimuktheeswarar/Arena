package msa.rehearsal.injector;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Abhimuktheeswarar on 21-09-2016.
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}
