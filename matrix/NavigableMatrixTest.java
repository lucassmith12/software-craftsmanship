package matrix;

import static org.junit.Assert.*;

import java.util.function.Function;

import org.junit.Test;

public class NavigableMatrixTest {

	@Test
	public void testInstance() {
		// Test input data
        int rows = 3;
        int columns = 4;
        Integer zeroValue = 0;

        Function<Indexes, Integer> valueMapper = new Function<Indexes, Integer>() {
            @Override
            public Integer apply(Indexes indexes) {
               
                return indexes.row()+3 + indexes.column()*2;
            }
        };
        NavigableMatrix<Integer> result = NavigableMatrix.instance(rows, columns, valueMapper, zeroValue);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Indexes index = new Indexes(i,j);
            	assertEquals("Matrix Entries should be equal", Integer.valueOf(result.value(index))== index.row()+3 + index.column()*2, true);
            }
        }
   
	}

	@Test
	public void testConstant() {
		// Test input data
        int rows = 3;
        int columns = 4;
        Integer zeroValue = 0;

        
        NavigableMatrix<Integer> result = NavigableMatrix.constant(rows, columns, 2, zeroValue);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Indexes index = new Indexes(i,j);
            	assertEquals("Matrix Entries should be equal", Integer.valueOf(result.value(index))== 2, true);
            }
        }

	}

	@Test
	public void testIdentity() {
		// Test input data
        int size = 4;
        Integer zeroValue = 0;
        Integer identityValue = 1;


        NavigableMatrix<Integer> result = NavigableMatrix.identity(size, zeroValue, identityValue);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    // Diagonal element should be identityValue
                    assertEquals(identityValue, result.value(new Indexes(i, j)));
                } else {
                    // Off-diagonal elements should be zeroValue
                    assertEquals(zeroValue, result.value(new Indexes(i, j)));
                }
            }
        }
	}

	@Test
	public void testFromSArrayArrayS() {
		Integer[][] inputMatrix = {
				{1, 2, 3},
				{4, 5, 6},
				{7, 8, 9}
		};
		NavigableMatrix<Integer> result = NavigableMatrix.from(inputMatrix, 0);

		for (int i = 0; i < inputMatrix.length; i++) {
			for (int j = 0; j < inputMatrix[0].length; j++) {
				assertEquals(inputMatrix[i][j], result.value(new Indexes(i,j)));
			}
		}
	}


}
