
public class Path implements Comparable<Path>{
	public City headOfPath;
	public City endOfPath;
	public int distance;
	
	public Path(City headOfPath, City endOfPath, int distance) {
		this.headOfPath = headOfPath;
		this.endOfPath = endOfPath;
		this.distance = distance;
	}
	
	public int compareTo(Path other) {
		if (this.distance < other.distance) {
			return -1;
		}
		
		else if (this.distance > other.distance) {
			return 1;
			
		}
		
		else {
			return 0;
		}
	}
	

}
