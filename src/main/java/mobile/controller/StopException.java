package mobile.controller;

/**
 * 当发生不可继续的情况时抛出此异常可跳过后续所有测试用例的执行
 */
public class StopException extends RuntimeException {
	private static final long serialVersionUID = -6148648905166778571L;

	public StopException() {
		super();
	}

	public StopException(String message, Throwable cause) {
		super(message, cause);
	}

	public StopException(Throwable cause) {
		super(cause);
	}

}
