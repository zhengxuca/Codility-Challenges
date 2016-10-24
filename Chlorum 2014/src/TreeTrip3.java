import java.util.*;

public class TreeTrip3 {

    Comparator<City> compareCity = new Comparator<City>() {

        @Override
        public int compare(City arg0, City arg1) {

            if (arg1.a != arg0.a) {
                return arg1.a - arg0.a;
            } else {
                return arg1.max - arg0.max;
            }
        }

    };

    City largestCluster(int maxValue, City[] cityMap) {

        City largest = null;
        boolean[] visited = new boolean[cityMap.length];
        int maxSize = 0;
        for (int i = 0; i < cityMap.length; i++) {
            City current = cityMap[i];
            if (visited[current.id] == false && current.a == maxValue) {
                int size = largestCluster(maxValue, current, visited);

                if (size > maxSize) {
                    maxSize = size;
                    largest = current;
                }
            }

        }

        return largest;
    }

    int largestCluster(int maxValue, City current, boolean[] visited) {

        visited[current.id] = true;
        int count = 1;
        if (current.a != maxValue) {
            return 0;
        }
        LinkedList<City> children = current.children;
        for (City c : children) {
            if (visited[c.id] == false) {
                count += largestCluster(maxValue, c, visited);
            }
        }

        return count;
    }

    int solution(int K, int C[], int D[]) {

        City[] cityMap = new City[C.length];
        PriorityQueue<City> explored = new PriorityQueue<City>(C.length, compareCity);
        PriorityQueue<City> unExplored = new PriorityQueue<City>(C.length, compareCity);
        City max = null;

        for (int i = 0; i < C.length; i++) {

            if (cityMap[i] == null) {
                cityMap[i] = new City(i, D[i]);

                if (max == null) {
                    max = cityMap[i];
                } else {
                    max = (cityMap[i].a > max.a) ? cityMap[i] : max;
                }
            }

            if (C[i] == i) {
                continue;
            }

            if (cityMap[C[i]] == null) {
                cityMap[C[i]] = new City(C[i], D[C[i]]);

                if (max == null) {
                    max = cityMap[C[i]];
                } else {
                    max = (cityMap[C[i]].a > max.a) ? cityMap[C[i]] : max;
                }

            }

            cityMap[i].children.add(cityMap[C[i]]);
            cityMap[C[i]].children.add(cityMap[i]);
        }
        City largest = largestCluster(max.a, cityMap);

        buildTree(largest, new boolean[C.length]);

        int count = 0;
        boolean[] included = new boolean[C.length];
        for (City c : cityMap) {
            unExplored.add(c);
        }
        explored.add(largest);

        while (explored.isEmpty() == false) {
            City current = explored.remove();
            if (included[current.id]) {
                continue;
            }
            int result = find(current, included, explored, unExplored, K - count);
            System.out.println(current.toString() + " result:" + result);
            if (result == -1) {
                break;
            } else {
                count = count + result;
            }
        }

        System.out.println("count: " + count);
        return count;
    }

    int find(City x, boolean[] included, PriorityQueue<City> explored, PriorityQueue<City> unExplored, int max) {

        int min = x.a;
        int count = 1;
        if (count > max) {
            return -1;
        }
        HashSet<City> needed = new HashSet<City>();
        included[x.id] = true;

        needed.add(x);
        while (unExplored.isEmpty() == false && unExplored.peek().a > min) {
            City current = unExplored.remove();
            while (included[current.id] == false) {

                included[current.id] = true;

                needed.add(current);
                current = current.parent;
                min = Math.min(min, current.a);
                count++;
                if (count > max) {
                    return -1;
                }
            }
        }

        // unExplored.removeAll(needed);
        // unExplored.removeAll(exploredSet);
        // explored.removeAll(exploredSet);

        for (City c : needed) {
            LinkedList<City> children = c.children;
            for (City child : children) {
                if (included[child.id] == false) {
                    explored.add(child);

                }
            }
        }

        return count;
    }

    public void buildTree(City root, boolean[] visited) {
      
        Stack<City> st = new Stack<City>();
        st.add(root);
        while(st.isEmpty()==false) {
            if(visited[st.peek().id]) {
                City current = st.pop();
                for(City c: current.children) {
                    if(c!= current.parent) {
                        current.max= Math.max(current.max, c.max);
                    }
                }
                
            } else {
                City current = st.peek();
                visited[current.id]=true;
                for(City c: current.children) {
                    if(visited[c.id]==false) {
                        c.parent= current;
                        st.add(c);
                    }
                }
            }
        }
    }

    public int buildTree(City current, City parent, boolean[] visited) {

        visited[current.id] = true;
        current.parent = parent;
        int max = current.a;
        LinkedList<City> children = current.children;
        for (City child : children) {
            if (visited[child.id] == false) {
                int a = buildTree(child, current, visited);
                max = Math.max(max, a);
            }
        }
        current.max = max;

        return max;
    }

    public class City {
        int id;
        int a;
        LinkedList<City> children = new LinkedList<City>();
        City parent = null;
        int max = -1; // the max attractiveness of the a descendent of this city

        public City(int id, int a) {
            this.id = id;
            this.a = a;
            this.max = a;
        }

        @Override
        public String toString() {
            return "id:" + id + " a:" + a;
        }
    }

}
