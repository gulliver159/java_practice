package net.thumbtack.school.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PathsOfRecordingValidator.class)
@Target( {ElementType.TYPE} )
@Retention(RetentionPolicy.RUNTIME)
public @interface PathsOfRecording {
    String message() default "One of the audio or video links must be included";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
