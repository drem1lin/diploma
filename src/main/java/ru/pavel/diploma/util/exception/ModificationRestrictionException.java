package ru.pavel.diploma.util.exception;

public class ModificationRestrictionException extends ru.pavel.diploma.util.exception.ApplicationException {
    public static final String EXCEPTION_MODIFICATION_RESTRICTION = "exception.user.modificationRestriction";

    public ModificationRestrictionException() {
        super(ru.pavel.diploma.util.exception.ErrorType.VALIDATION_ERROR, EXCEPTION_MODIFICATION_RESTRICTION);
    }
}