import java.util.ArrayList;
import java.util.List;

public class City implements Comparable<City>{
	
	public String cityName;
	public City parent;
	public int pathDistance = Integer.MAX_VALUE;
	public int visited;
	public List<Path> neighbors;
	public Path minimumPath;
	
	public City(String cityName, int visited) {
		this.cityName = cityName;
		this.visited = visited;
		this.neighbors = new ArrayList<>();
	}
	
	public int compareTo(City anotherCity) {
		if (this.pathDistance < anotherCity.pathDistance) {
			return -1;
		}
		
		else if (this.pathDistance > anotherCity.pathDistance) {
			return 1;
		}
	
		else {
			if (this.cityName.startsWith("c") && anotherCity.cityName.startsWith("d")) {
				return -1;
			}
			
			else if (this.cityName.startsWith("d") && anotherCity.cityName.startsWith("c")) {
				return 1;
			}
			
			else {
			
				if(Integer.valueOf(this.cityName.substring(1)) < Integer.valueOf(anotherCity.cityName.substring(1))) {
					return -1;
				}
				else {
					return 1;
					
				}
		}
	}

}
}
