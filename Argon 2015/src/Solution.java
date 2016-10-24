public class Solution {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        test3();
        // test2();
    }

    static void test1() {
        int[] A = { 1, 1, 0, 1, 0, 0, 1, 1 };
        System.out.println(solution(A));
    }

    static void test2() {
        int[] A = { 0, 1, 1, 0, 0, 1 };
        System.out.println(solution(A));
    }

    // /
    static void test3() {
        int[] A = { 0, 1, 1, 0 };
        System.out.println(solution(A));
    }

    public static int solution(int[] A) {
        int[] left = new int[A.length];
        for (int i = 0; i < A.length; i++) {
            left[i] = -1;
        }
        int[] prefixZeros = new int[A.length];

        int last = -1;
        for (int i = 0; i < A.length; i++) {
            if (A[i] == 0) {
                last = i;
                break;
            }
        }
        if (last == -1) {
            return 0;
        }
        prefixZeros[last] = 1;
        left[last] = last;
        for (int i = last + 1; i < A.length; i++) {
            prefixZeros[i] = (A[i] == 0) ? prefixZeros[i - 1] + 1 : prefixZeros[i - 1];
            int temp = left[last];
            while (temp >= 0 && prefixZeros[i] - ((temp - 1 == -1) ? 0 : prefixZeros[temp - 1]) > (i - temp + 1) / 2) {
                left[i] = temp;
                last = i;
                temp--;
            }

        }
        int[] right = new int[A.length];
        for (int i = 0; i < A.length; i++) {
            right[i] = -1;
        }
        int[] prefixOnes = new int[A.length];
        last = -1;
        for (int i = A.length - 1; i >= 0; i--) {
            if (A[i] == 1) {
                last = i;
                break;
            }
        }
        if (last == -1) {
            return 0;
        }
        prefixOnes[last] = 1;
        right[last] = last;
        for (int i = last - 1; i >= 0; i--) {
            prefixOnes[i] = (A[i] == 1) ? prefixOnes[i + 1] + 1 : prefixOnes[i + 1];
            int temp = right[last];
            while (temp < A.length
                    && prefixOnes[i] - ((temp + 1 == A.length) ? 0 : prefixOnes[temp + 1]) > (temp - i + 1) / 2) {
                right[i] = temp;
                last = i;
                temp++;
            }
        }

        int max_length = 0;
        for (int i = 0; i < A.length - 1; i++) {
            if (left[i] != -1 && right[i + 1] != -1) {
               // System.out.println(left[i] + " " + i + " " + (i + 1) + " " + right[i + 1]);
                max_length = Math.max(max_length, right[i + 1] - left[i] + 1);
            }
        }
        return max_length;
    }
}
