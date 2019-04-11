package se.lexicon.emil.CompanyManager.exceptions;

public class EntityNotFoundException extends IllegalArgumentException {

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
