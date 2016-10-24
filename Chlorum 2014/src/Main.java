public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        test1();

    }

    public static void test1() {
        int[] C = new int[8];
        int[] D = new int[8];
        C[0] = 1;
        D[0] = 6;
        C[1] = 3;
        D[1] = 2;
        C[2] = 0;
        D[2] = 7;
        C[3] = 3;
        D[3] = 5;
        C[4] = 2;
        D[4] = 6;
        C[5] = 4;
        D[5] = 5;
        C[6] = 4;
        D[6] = 2;
        C[7] = 6;
        D[7] = 1;

        TreeTrip3 trip = new TreeTrip3();
        trip.solution(5, C, D);
    }

    public static void test2() {
        int[] C = new int[6];
        int[] D = new int[6];

        C[0] = 3;
        C[1] = 0;
        C[2] = 0;
        C[3] = 4;
        C[4] = 5;
        C[5] = 5;

        D[0] = 5;
        D[1] = 5;
        D[2] = 5;
        D[3] = 1;
        D[4] = 2;
        D[5] = 5;
        TreeTrip3 trip = new TreeTrip3();
        trip.solution(6, C, D);
    }
}
