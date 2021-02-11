package net.thumbtack.school.validators;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PathsOfRecordingValidator implements ConstraintValidator<PathsOfRecording, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        String pathToAudio = (String) new BeanWrapperImpl(obj)
                .getPropertyValue("pathToAudio");

        String pathToVideo = (String) new BeanWrapperImpl(obj)
                .getPropertyValue("pathToVideo");

        if (pathToAudio == null && pathToVideo == null) {
            return false;
        }

        return true;
    }

}
