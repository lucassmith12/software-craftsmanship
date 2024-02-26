package matrix;

public final class InconsistentZeroException extends Exception {

	private static final long serialVersionUID = -2630634235253292084L;
	
	private final String thisZero;
	private final String otherZero;
	
	public InconsistentZeroException(String thisZ, String otherZ) {
		thisZero = thisZ;
		otherZero = otherZ;
	}
	
	public String getThisZero() {
		return thisZero;
	}
	public String getOtherZero() {
		return otherZero;
	}
	public static <I,T> T requireMatching(Matrix<I,T> thisMatrix, Matrix<I,T> otherMatrix) {
		if(!thisMatrix.zero().equals(otherMatrix.zero())) {
			throw new IllegalArgumentException(new InconsistentZeroException (thisMatrix.zero().toString(), otherMatrix.zero().toString()));
		}
		return thisMatrix.zero();
	}
}
