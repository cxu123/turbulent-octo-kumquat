package ResultCode;

public class ResultCode {
	private int resultcode;
	private String resultMessage;

	public ResultCode() {
		super();
	}
	public ResultCode(int code, String message) {
		super();
		this.resultcode = code;
		this.resultMessage = message;
	}

	public int getResultCode() {
		return resultcode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultCode(int code) {
		this.resultcode = code;
	}

	public void setResultMessage(String message) {
		this.resultMessage = message;
	}
}
