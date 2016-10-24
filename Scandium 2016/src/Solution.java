
public class Solution {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // int[] A = { 4, 5, 3, 7, 2 };
        int[] A = { 2,2,2,3,2 };
        Solution sol = new Solution();
        System.out.println(sol.solution(A));
    }

    public String solution(int[] A) {
        boolean[] isOdd = new boolean[A.length];
        int odds = 0;
        for (int i = 0; i < A.length; i++) {
            isOdd[i] = isOdd(A[i]);
            if (isOdd[i]) {
                odds++;
            }
        }

        if (odds == 1) {
            for (int i = 0; i < A.length; i++) {
                if (isOdd[i]) {
                    if (i > (A.length - 1 - i)) {
                        return "0," + (i - (A.length - 1 - i) - 1);
                    } else if (i < (A.length - 1 - i)) {
                        return (i + 1) + "," + (A.length - 1 - i) ;
                    } else {
                        return "NO SOLUTION";
                    }
                }
            }
        }

        if (isOdd(odds) == false) {
            // the number of odd numbers is even.
            return "0," + (A.length - 1);
        } else {

            int[] evenRight = new int[A.length];
            int[] evenLeft = new int[A.length];
            populateLeft(A, evenLeft);
            populateRight(A, evenRight);

            boolean hasAnswer = false;
            int x = Integer.MAX_VALUE;
            int y = Integer.MAX_VALUE;
            for (int i = 0; i < A.length; i++) {
                if (isOdd(A[i])) {
                    int left = (i == 0) ? 0 : evenLeft[i - 1];
                    int right = (i == A.length - 1) ? 0 : evenRight[i + 1];
                    if (left > right) {
                        int[] result = getMoveLeft(i - 1, A, left - right);
                        if (result[0] < x) {
                            x = result[0];
                            y = result[1];
                        } else if (result[0] == x) {
                            y = Math.min(y, result[1]);
                        }
                        hasAnswer = true;
                        break;
                    }
                }
            }

            for (int i = A.length - 1; i >= 0; i--) {
                if (isOdd(A[i])) {
                    int left = (i == 0) ? 0 : evenLeft[i - 1];
                    int right = (i == A.length - 1) ? 0 : evenRight[i + 1];
                    if (right > left) {
                        int[] result = getMoveRight(i + 1, A, right - left);
                        if (result[0] < x) {
                            x = result[0];
                            y = result[1];
                        } else if (result[0] == x) {
                            y = Math.min(y, result[1]);
                        }
                        hasAnswer = true;
                    }
                }
            }

            if (hasAnswer) {
                return x + "," + y;
            } else {
                return "NO SOLUTION";
            }
        }
    }

    private int[] getMoveRight(int index, int[] A, int evenCount) {
        int[] result = new int[2];
        boolean unPairOdd = false;
        int i = index;
        while (i < A.length && evenCount > 0) {
            if (isOdd(A[i])) {
                if (unPairOdd == false) {
                    unPairOdd = true;
                } else {
                    unPairOdd = false;
                    evenCount--;
                }
            } else {
                evenCount--;
            }
            i++;
        }
        result[0] = index;
        result[1] = i - 1;
        return result;
    }

    private int[] getMoveLeft(int index, int[] A, int evenCount) {
        int[] result = new int[2];
        boolean unPairOdd = false;
        int i = index;
        while (i >= 0 && evenCount > 0) {
            if (isOdd(A[i])) {
                if (unPairOdd == false) {
                    unPairOdd = true;
                } else {
                    unPairOdd = false;
                    evenCount--;
                }
            } else {
                evenCount--;
            }
            i--;
        }
        result[0] = i + 1;
        result[1] = index;
        return result;
    }

    private void populateRight(int[] A, int[] evenCount) {
        boolean unPairOdd = false;
        int index = -1;// index of last odd.
        for (int i = A.length - 1; i >= 0; i--) {
            if (isOdd(A[i])) {
                if (unPairOdd == false) {
                    unPairOdd = true;
                    evenCount[i] = 0;
                    index = i;
                } else {
                    evenCount[i] = evenCount[i + 1] + 1 + ((index + 1 <A.length) ? evenCount[index + 1] : 0);
                    unPairOdd = false;
                }
            } else {
                evenCount[i] = (i == A.length - 1) ? 1 : evenCount[i + 1] + 1;
            }
        }
    }

    private void populateLeft(int[] A, int[] evenCount) {
        boolean unPairOdd = false;
        int index = -1;
        for (int i = 0; i < A.length; i++) {
            if (isOdd(A[i])) {
                if (unPairOdd == false) {
                    unPairOdd = true;
                    evenCount[i] = 0;
                    index = i;
                } else {
                    evenCount[i] = evenCount[i - 1] + 1 + ((index - 1 >= 0) ? evenCount[index - 1] : 0);
                    unPairOdd = false;
                }
            } else {
                evenCount[i] = (i == 0) ? 1 : evenCount[i - 1] + 1;
            }
        }
    }

    private boolean isOdd(int x) {
        return (x & 1) == 1;
    }
}
