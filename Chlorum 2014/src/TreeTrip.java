import java.util.*;

public class TreeTrip {

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

		solution(8, C, D);
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

		solution(2, C, D);
	}

	static HashMap<Integer, City> cityMap = new HashMap<Integer, City>();
	static Comparator<City> compareCity = new Comparator<City>() {

		@Override
		public int compare(City arg0, City arg1) {

			return arg1.a - arg0.a;
		}

	};

	static int solution(int K, int C[], int D[]) {

		PriorityQueue<City> excluded = new PriorityQueue<City>(C.length,
				compareCity);

		PriorityQueue<City> explored = new PriorityQueue<City>(C.length,
				compareCity);

		for (int i = 0; i < C.length; i++) {

			City city = cityMap.get(i);
			if (city == null) {
				city = new City(i, D[i]);
				cityMap.put(i, city);
				excluded.add(city);
			}

			if (C[i] == i) {
				continue;
			}

			City n = cityMap.get(C[i]);
			if (n == null) {
				n = new City(C[i], D[C[i]]);
				cityMap.put(C[i], n);
				excluded.add(n);
			}

			city.children.add(n);
			n.children.add(city);
		}

		// System.out.println(tripSet.toString());

		for (int i = 0; i < C.length; i++) {
			City city = cityMap.get(i);
			System.out.println(city.id + " " + city.children.toString());
			System.out.println();
		}

		explored.add(excluded.remove());

		int count = 0;
		HashSet<City> tripSet = new HashSet<City>();
		while (explored.isEmpty() == false) {
			City current = explored.remove();
			// System.out.println(current.toString());

			if (excluded.isEmpty() || current.a >= excluded.peek().a) {
				tripSet.add(current);
				count++;
				if (count == K) {

					break;
				}
			} else {
				HashSet<City> added = find(excluded, tripSet, current);

				if (added.size() <= K - count) {

					tripSet.addAll(added);
					explored.removeAll(added);
					excluded.removeAll(added);
					for (City c : added) {
						ArrayList<City> children = c.children;
						for (City child : children) {
							if (tripSet.contains(child) == false) {
								explored.add(child);
								excluded.add(child);
							}
						}

					}
					count = count + added.size();

				} else {
					excluded.add(current);
				}

				continue;

			}
			ArrayList<City> children = current.children;
			for (City c : children) {
				if (tripSet.contains(c) == false) {
					explored.add(c);
					excluded.remove(c);
				}
			}

		}

		System.out.println(tripSet.toString());
		System.out.println(explored.toString() + " " + explored.size());
		return tripSet.size();
	}

	static HashSet<City> find(PriorityQueue<City> excluded,
			HashSet<City> tripSet, City city) {
		HashSet<City> added = new HashSet<City>();

		find(added, new HashSet<City>(), tripSet, city, city);
		return added;
	}

	static boolean find(HashSet<City> added, HashSet<City> visited,
			HashSet<City> tripSet, City city, City current) {

		visited.add(current);
		boolean flag = false;
		if (current.a > city.a && tripSet.contains(current) == false) {
			added.add(current);
			flag = true;
		}

		ArrayList<City> children = current.children;
		for (City child : children) {
			if (visited.contains(child) == false) {

				boolean result = find(added, visited, tripSet, city, child);
				if (result) {
					flag = true;
				}
			}
		}

		if (current.a <= city.a && flag && tripSet.contains(current) == false) {
			added.add(current);
		}
		return flag;
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
