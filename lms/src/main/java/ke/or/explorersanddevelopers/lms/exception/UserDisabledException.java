package ke.or.explorersanddevelopers.lms.exception;

public class UserDisabledException extends RuntimeException{
    private static final long serialVersionUID = -2358194362372724783L;

    public UserDisabledException(String message){
        super(message);
    }
}
