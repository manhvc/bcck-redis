package vn.edu.uit.ms.qltt.cuoiky.exception;

public class BaseException extends RuntimeException {
    private static final long serialVersionUID = -128216908108589678L;

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
