package iti.gp.navigation;

import android.renderscript.Float2;


public class MATRIX {
	public float[][] getData() {
		return data;
	}

	private final int M; // number of rows
	private final int N; // number of columns
	private final float[][] data; // M-by-N array

	// create M-by-N MATRIX of 0's
	public MATRIX(int M, int N) {
		this.M = M;
		this.N = N;
		data = new float[M][N];
	}

	// create MATRIX based on 2d array
	public MATRIX(float[][] data) {
		M = data.length;
		N = data[0].length;
		this.data = new float[M][N];
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++)
				this.data[i][j] = data[i][j];
	}

	// copy constructor
	private MATRIX(MATRIX A) {
		this(A.data);
	}

	// create and return a random M-by-N MATRIX with values between 0 and 1
	public static MATRIX random(int M, int N) {
		MATRIX A = new MATRIX(M, N);
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++)
				A.data[i][j] = (float) Math.random();
		return A;
	}

	// create and return the N-by-N identity MATRIX
	public static MATRIX identity(int N) {
		MATRIX I = new MATRIX(N, N);
		for (int i = 0; i < N; i++)
			I.data[i][i] = 1;
		return I;
	}

	// swap rows i and j
	private void swap(int i, int j) {
		float[] temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}

	// create and return the transpose of the invoking MATRIX
	public MATRIX transpose() {
		MATRIX A = new MATRIX(N, M);
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++)
				A.data[j][i] = this.data[i][j];
		return A;
	}

	// return C = A + B
	public MATRIX plus(MATRIX B) {
		MATRIX A = this;
		if (B.M != A.M || B.N != A.N)
			throw new RuntimeException("Illegal MATRIX dimensions.");
		MATRIX C = new MATRIX(M, N);
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++)
				C.data[i][j] = A.data[i][j] + B.data[i][j];
		return C;
	}

	// return C = A - B
	public MATRIX minus(MATRIX B) {
		MATRIX A = this;
		if (B.M != A.M || B.N != A.N)
			throw new RuntimeException("Illegal MATRIX dimensions.");
		MATRIX C = new MATRIX(M, N);
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++)
				C.data[i][j] = A.data[i][j] - B.data[i][j];
		return C;
	}

	// does A = B exactly?
	public boolean eq(MATRIX B) {
		MATRIX A = this;
		if (B.M != A.M || B.N != A.N)
			throw new RuntimeException("Illegal MATRIX dimensions.");
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++)
				if (A.data[i][j] != B.data[i][j])
					return false;
		return true;
	}

	// return C = A * B
	public MATRIX times(MATRIX B) {
		MATRIX A = this;
		if (A.N != B.M)
			throw new RuntimeException("Illegal MATRIX dimensions.");
		MATRIX C = new MATRIX(A.M, B.N);
		for (int i = 0; i < C.M; i++)
			for (int j = 0; j < C.N; j++)
				for (int k = 0; k < A.N; k++)
					C.data[i][j] += (A.data[i][k] * B.data[k][j]);
		return C;
	}

	// return x = A^-1 b, assuming A is square and has full rank
	public MATRIX solve(MATRIX rhs) {
		if (M != N || rhs.M != N || rhs.N != 1)
			throw new RuntimeException("Illegal MATRIX dimensions.");

		// create copies of the data
		MATRIX A = new MATRIX(this);
		MATRIX b = new MATRIX(rhs);

		// Gaussian elimination with partial pivoting
		for (int i = 0; i < N; i++) {

			// find pivot row and swap
			int max = i;
			for (int j = i + 1; j < N; j++)
				if (Math.abs(A.data[j][i]) > Math.abs(A.data[max][i]))
					max = j;
			A.swap(i, max);
			b.swap(i, max);

			// singular
			if (A.data[i][i] == 0.0)
				throw new RuntimeException("MATRIX is singular.");

			// pivot within b
			for (int j = i + 1; j < N; j++)
				b.data[j][0] -= b.data[i][0] * A.data[j][i] / A.data[i][i];

			// pivot within A
			for (int j = i + 1; j < N; j++) {
				float m = A.data[j][i] / A.data[i][i];
				for (int k = i + 1; k < N; k++) {
					A.data[j][k] -= A.data[i][k] * m;
				}
				A.data[j][i] = (float)0.0;
			}
		}

		// back substitution
		MATRIX x = new MATRIX(N, 1);
		for (int j = N - 1; j >= 0; j--) {
			float t = (float) 0.0;
			for (int k = j + 1; k < N; k++)
				t += A.data[j][k] * x.data[k][0];
			x.data[j][0] = (b.data[j][0] - t) / A.data[j][j];
		}
		return x;

	}

	// print MATRIX to standard output
	public void show() {
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++)
				System.out.printf("%9.4f ", data[i][j]);
			System.out.println();
		}
	}

	// //////////////////inverse//////////////
	public float determinantOfMinor(int theRowHeightY, int theColumnWidthX) {
		int x1 = theColumnWidthX == 0 ? 1 : 0; /* always either 0 or 1 */
		int x2 = theColumnWidthX == 2 ? 1 : 2; /* always either 1 or 2 */
		int y1 = theRowHeightY == 0 ? 1 : 0; /* always either 0 or 1 */
		int y2 = theRowHeightY == 2 ? 1 : 2; /* always either 1 or 2 */

		return (data[y1][x1] * data[y2][x2]) - (data[y1][x2] * data[y2][x1]);
	}

	// (B) Determinant is now: (Note the minus sign!)

	float determinant() {
		return (data[0][0] * determinantOfMinor(0, 0))
				- (data[0][1] * determinantOfMinor(0, 1))
				+ (data[0][2] * determinantOfMinor(0, 2));
	}

	// (C) And the inverse is now:

	public MATRIX inverse() {
		float det = determinant();

		/*
		 * Arbitrary for now. This should be something nicer... * if ( ABS(det)
		 * < 1e-2 ) { memset( theOutput, 0, sizeof theOutput ); return false; }
		 */
		float oneOverDeterminant = (float)1.0 / det;
		float[][] theOutput = new float[3][3];
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 3; x++) {
				/*
				 * Rule is inverse = 1/det * minor of the TRANSPOSE matrix. *
				 * Note (y,x) becomes (x,y) INTENTIONALLY here!
				 */

				theOutput[y][x] = determinantOfMinor(x, y) * oneOverDeterminant;

				/* (y0,x1) (y1,x0) (y1,x2) and (y2,x1) all need to be negated. */
				if (1 == ((x + y) % 2))
					theOutput[y][x] = -theOutput[y][x];
			}
		MATRIX r = new MATRIX(theOutput);
		return r;
	}

}
