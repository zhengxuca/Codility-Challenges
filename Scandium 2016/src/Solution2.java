import java.util.Arrays;

public class Solution2 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // int[] A = { 2,2,2,3,3,2,2,2,2,7,2 };
        // int[] A = { 2,2, 3, 3,2, 2,2, 7, 2, 2,2,2 };
        int[] A = { 2, 2, 3, 3, 2, 2, 7, 2, 2, 2, 2, 2 };
        Solution2 sol = new Solution2();
        System.out.println(sol.solution(A));
    }

    /**
     * Find the index of the first odd from the left of the starting position
     * 
     * @param A
     * @param start
     */
    private int findOddIndex(int[] A, int start) {
        while (start >= 0 && A[start] == 0) {
            start--;
        }
        return start;
    }

    public String solution(int[] A) {
        int odds = 0;
        for (int i = 0; i < A.length; i++) {
            A[i] = A[i] & 1;
            if (A[i] == 1) {
                odds++;
            }
        }

        if (odds == 1) {
            for (int i = 0; i < A.length; i++) {
                if (A[i] == 1) {
                    if (i > (A.length - 1 - i)) {
                        return "0," + (i - (A.length - 1 - i) - 1);
                    } else if (i < (A.length - 1 - i)) {
                        return (i + 1) + "," + (A.length - 1 - i);
                    } else {
                        return "NO SOLUTION";
                    }
                }
            }
        }

        if ((odds & 1) == 0) {
            // the number of odd numbers is even.
            return "0," + (A.length - 1);
        }

        // the number of even nums before the left most odd
        int left = leftEven(A);
        //the number of even numbers after the right most odd
        int right = rightEven(A);
        int rightIndex = A.length - 1 - right;
        if (left >= right) {
            /**
             * Ex.
             * 222232233722
             * left (4 evens) is greater than right (2 even numbers)
             */
            int oddIndex = this.findOddIndex(A, rightIndex - 1);
            int diff = left - right;
            int x = left - diff;
            int remainingEvensCount = rightIndex - oddIndex - 1;
            int y = oddIndex;
            if (remainingEvensCount > 0) {
                if (x >= remainingEvensCount) {
                    x = x - remainingEvensCount;
                    remainingEvensCount = 0;
                } else {
                    x = 0;
                }
            }
            if (remainingEvensCount > right) {
                y += remainingEvensCount - right;
            }
            return x + "," + y;
        } else {
            int oddIndex = this.findOddIndex(A, rightIndex - 1);
            int middleSize = rightIndex - oddIndex - 1;
            int x = -1, y = -1;
            if (middleSize >= right) {
                x = 0;
                int diff = middleSize - right;
                y = oddIndex + diff;
            } else if (left + middleSize >= right) {
                y = oddIndex;
                x = left;
                int diff = left + middleSize - right;
                if (x >= diff) {
                    x -= diff;
                } else {
                    int temp = x;
                    x = 0;
                    diff -= temp;
                    y += diff;

                }
            } else {
                x = left + 1;
                int diff = right - left;
                y = rightIndex + diff;
            }
            return x + "," + y;
        }
    }

    private int leftEven(int[] A) {
        for (int i = 0; i < A.length; i++) {
            if (A[i] == 1) {
                return i;
            }
        }
        return -1;
    }

    private int rightEven(int[] A) {
        for (int i = A.length - 1; i >= 0; i--) {
            if (A[i] == 1) {
                return A.length - 1 - i;
            }
        }
        return -1;
    }
}
