package net.thumbtack.school.tasks1_10.matrix;

import java.util.*;

public class MatrixNonSimilarRows {
    private final Set<int[]> matrix;

    public MatrixNonSimilarRows(int[][] matrix) {
        this.matrix = new HashSet<>();
        Set<Set<Integer>> setOfSets = toSetOfSets(matrix);
        Set<Integer> set_row;
        for (int[] row : matrix) {
            set_row = new HashSet<>();
            for (int v : row) {
                set_row.add(v);
            }
            if (setOfSets.contains(set_row)) {
                this.matrix.add(row);
                setOfSets.remove(set_row);
            }
        }
    }

    private Set<Set<Integer>> toSetOfSets(int[][] matrix) {
        //Находим множества непохожих строк
        Set<Set<Integer>> setOfSets = new HashSet<>();
        Set<Integer> set_row;
        for (int[] row : matrix) {
            set_row = new HashSet<>();
            for (int v : row) {
                set_row.add(v);
            }
            setOfSets.add(set_row);
        }
        return setOfSets;
    }

    public Set<int[]> getNonSimilarRows() {
        return matrix;
    }
}
