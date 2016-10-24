import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class TreeTrip2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		test2();

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

		solution(1, C, D);
	}

	public static void test2() {
		int[] C = new int[3];
		int[] D = new int[3];

		C[0] = 1;
		C[1] = 1;
		C[2] = 0;

		D[0] = 7;
		D[1] = 6;
		D[2] = 6;

		solution(3, C, D);
	}

	static Comparator<City> compareCity = new Comparator<City>() {

		@Override
		public int compare(City arg0, City arg1) {

			return arg1.a - arg0.a;
		}

	};
	
	static int solution(int K, int C[], int D[]) {

		HashMap<Integer, City> cityMap = new HashMap<Integer, City>();
		ArrayList<City> cities = new ArrayList<City>(C.length);
		for (int i = 0; i < C.length; i++) {

			City city = cityMap.get(i);
			if (city == null) {
				city = new City(i, D[i]);
				cityMap.put(i, city);
				cities.add(city);
			}

			if (C[i] == i) {
				continue;
			}

			City n = cityMap.get(C[i]);
			if (n == null) {
				n = new City(C[i], D[C[i]]);
				cityMap.put(C[i], n);
				cities.add(n);

			}

			city.children.add(n);
			n.children.add(city);
		}

		Collections.sort(cities, compareCity);
		// System.out.println(tripSet.toString());

		int count = 1;
		HashSet<City> includedSet = new HashSet<City>();
		HashSet<City> excludedSet = new HashSet<City>();
		excludedSet.addAll(cities);
		includedSet.add(cities.get(0));
		excludedSet.remove(cities.get(0));
		PriorityQueue<City> excluded = new PriorityQueue<City>(C.length,
				compareCity);
		excluded.addAll(excludedSet);
		boolean[] included = new boolean[C.length];
		included[cities.get(0).id] = true;

		boolean flag = false;
		
		HashMap<City, HashSet<City>> pathMap =new HashMap<City, HashSet<City>>();
		for (int i = 1; i < cities.size() && count < K; i++) {
			City current = cities.get(i);
			if (flag && current.a != cities.get(i - 1).a) {
				break;
			}

			if (!included[current.id]) {
				int[] min = new int[2];
				min[1] = Integer.MAX_VALUE;
				HashSet<City> path = findPath(pathMap,excluded, includedSet, current,
						min);

				if (count + path.size() <= K) {

					includedSet.addAll(path);
					for (City p : path) {
						// excluded.remove(p);
						included[p.id] = true;
					}
					count = count + path.size();
				} else {
					excluded.addAll(path);
					flag = true;
				}

			}

		}

		System.out.println("Count: " + count);
		System.out.println(includedSet.toString());
		return count;
	}

	static HashSet<City> findPath(HashMap<City, HashSet<City>> pathMap, PriorityQueue<City> excluded,
			HashSet<City> includedSet, City x, int[] min) {

		HashSet<City> path = pathMap.get(x);
		if(path==null) {
			HashSet<City> visited = new HashSet<City>();
			findPath(path, includedSet, visited, x, min);
			excluded.removeAll(path);
			pathMap.put(x, path);
		}
		
		while (excluded.isEmpty() == false && excluded.peek().a > min[1]) {
			HashSet<City> newPath = new HashSet<City>();
			findPath(newPath, includedSet,new HashSet<City>(), excluded.peek(), min);
			excluded.removeAll(newPath);
			path.addAll(newPath);
		}

		return path;
	}

	static boolean findPath(HashSet<City> path, HashSet<City> includedSet,
			HashSet<City> visited, City current, int[] min) {
		visited.add(current);
		if (includedSet.contains(current)) {
			return true;
		}
		ArrayList<City> children = current.children;
		for (City child : children) {
			if (!visited.contains(child)) {
				boolean result = findPath(path, includedSet, visited, child,
						min);
				if (result) {
					path.add(current);

					if (current.a <= min[1]) {
						min[0] = current.id;
						min[1] = current.a;
					}

					return true;
				}
			}
		}

		return false;
	}

	public static class City {
		int id;
		int a;
		ArrayList<City> children = new ArrayList<City>();

		public City(int id, int a) {
			this.id = id;
			this.a = a;
		}

		@Override
		public String toString() {
			return id + " " + a;
		}
	}

}
