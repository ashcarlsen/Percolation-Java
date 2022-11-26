public class PathCompressingUnionFind {
    public int[] id;
    private int[] sz;

    public PathCompressingUnionFind(int startSize) {
        id = new int[startSize];
        sz = new int[startSize];
        for(int i = 0; i < startSize; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    public int findBaseParent(int p) {
        int temp = p;
        while (id[temp] != temp) { temp = id[temp];}
        id[p] = temp; // Path Compression
        sz[temp] = 2; // Change the size after path compression
        return temp;
    }

    public int find(int spot) {
        return findBaseParent(spot);
    }

    public void union(int p, int q) {
        int i = findBaseParent(p);
        int j = findBaseParent(q);
        if (sz[i] < sz[j]) {
            id[i] = j; sz[j] += sz[i];
        }
        else  {
            id[j] = i; sz[i] += sz[j];
        }
    }

    public boolean connected(int spot1, int spot2) {
        return findBaseParent(spot1) == findBaseParent(spot2);
    }

    public void printIDArray() {
        for (int i = 0; i < id.length; i++) {
            System.out.print(this.id[i] + " ");
        }
    }


    /*public static void main(String[] args) {
        int numOfSpots = 10;
        PathCompressingUnionFind q = new PathCompressingUnionFind(numOfSpots);
        q.union(3, 8);
        q.union(5, 2);
        q.union(2, 3);
        q.union(9, 1);
        q.union(7, 4);
        q.union(3, 9);
        q.union(8, 6);
        q.find(1);
        System.out.print("Spot Numbers: " + "\t");
        for (int i = 0; i < numOfSpots; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.print("Parent: " + "\t\t");
        for (int i = 0; i < numOfSpots; i++) {
            System.out.print(q.id[i] + " ");
        }
    }*/
}
