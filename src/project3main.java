import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class project3main {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(new File(args[0]));;
	    PrintStream output = new PrintStream(args[1]);
		
		PriorityQueue<City> citiesPQ = new PriorityQueue<>();
		PriorityQueue<City> pq = new PriorityQueue<>();
		ArrayList<City> ccitiesAL = new ArrayList<City>();
		ArrayList<City> dcitiesAL = new ArrayList<City>();
		ArrayList<City> shortestPath = new ArrayList<>();
		int timeOfMecnun = 0;
		int tax = 0;

		
		// input reading
		int time = input.nextInt();
		int numberOfCities = input.nextInt();
		String nameOfCityOfMecnun = input.next();
		String nameOfCityOfLeyla = input.next();
		int numberOfCcities = Integer.valueOf(nameOfCityOfLeyla.substring(1));
		int numberOfDcities = (numberOfCities - numberOfCcities);
		
		// create cities
		for (int i = 0; i < (numberOfCcities); i++) {
			String name = ("c"+ (i+1));
			  	if (name.equals(nameOfCityOfMecnun)) {
					City cityOfMecnun = new City(name, 0);
					ccitiesAL.add(cityOfMecnun);
				}
				
			  	else if (name.equals(nameOfCityOfLeyla)) {
					City cityOfLeyla = new City(name, 0);
					ccitiesAL.add(cityOfLeyla);
				}
				
				else {
					ccitiesAL.add(new City(name, 0));
				}
			  
		}
		
		for (int i = 0; i < (numberOfDcities); i++) {
			String name = ("d"+ (i+1));
			City city = new City(name, 0);
			dcitiesAL.add(city);

			  
		}
		
		// create paths
		while (input.hasNextLine()) {
			String line = input.nextLine();

			
			  Scanner lineScanner = new Scanner(line);

			  
			  if(!line.isEmpty()) {
				  if(lineScanner.hasNext()) {
					  String nameOfCurrentCity = lineScanner.next();

					  
						  if (nameOfCurrentCity.startsWith("c")){
							  City currentcity = ccitiesAL.get((Integer.valueOf(nameOfCurrentCity.substring(1))) -1);
							  while (lineScanner.hasNext()) {
							  if (!nameOfCurrentCity.equals(nameOfCityOfLeyla)) {
								  City othercity = ccitiesAL.get((Integer.valueOf(lineScanner.next().substring(1))) -1);
								  int distance = lineScanner.nextInt();
								  currentcity.neighbors.add(new Path(currentcity, othercity, distance));

							  }
							  
							  else {
								  String s = lineScanner.next();
								  int distance = lineScanner.nextInt();
								  if (s.startsWith("d")) {
									  City othercity = dcitiesAL.get((Integer.valueOf(s.substring(1))) -1);
									  currentcity.neighbors.add(new Path(currentcity, othercity, distance));
									  othercity.neighbors.add(new Path(othercity, currentcity, distance));
								  }
								
								  
								  
								  

								  
							  }
							  }
						  }
						  
						  else {
							  City currentcity = dcitiesAL.get((Integer.valueOf(nameOfCurrentCity.substring(1))) -1);
							  while (lineScanner.hasNext()) {
							  City othercity = dcitiesAL.get((Integer.valueOf(lineScanner.next().substring(1))) -1);

							  int distance = lineScanner.nextInt();
							  currentcity.neighbors.add(new Path(currentcity, othercity, distance));
							  othercity.neighbors.add(new Path(othercity, currentcity, distance));

							  
						  }
				  }
					  
				  }
					  
					  lineScanner.close();
					  
				  }
			  
			  else {
				  continue;
			  }
			  
		}
		input.close();
		
		City cityOfMecnun = ccitiesAL.get((Integer.valueOf(nameOfCityOfMecnun.substring(1)))-1);
		City cityOfLeyla = ccitiesAL.get((Integer.valueOf(nameOfCityOfLeyla.substring(1)))-1);

		cityOfMecnun.pathDistance = 0;
		citiesPQ.add(cityOfMecnun);
		
		
		// mecnun's road to leyla
		while(!citiesPQ.isEmpty()) {
			City currentCity = citiesPQ.poll();
			
			for (int i = 0; i < currentCity.neighbors.size(); i ++) {
				City city = currentCity.neighbors.get(i).endOfPath;
				
				if ((currentCity.pathDistance + currentCity.neighbors.get(i).distance) < city.pathDistance) {
					citiesPQ.remove(city);
					city.parent = currentCity;
					city.pathDistance = (currentCity.pathDistance + currentCity.neighbors.get(i).distance);
					citiesPQ.add(city);
				}
				
			}
		}
		
		
		
		for (City city = cityOfLeyla; city != null; city = city.parent) { 
			shortestPath.add(city);
		}
		
		
			timeOfMecnun = cityOfLeyla.pathDistance;
			Collections.reverse(shortestPath);
			if (shortestPath.get(0).cityName.equals(nameOfCityOfMecnun) && shortestPath.get((shortestPath.size())-1).cityName.equals(nameOfCityOfLeyla)) {
				for (int i = 0; i < shortestPath.size(); i ++) {
					output.print(shortestPath.get(i).cityName + " ");
				}
				
				output.println();
				cityOfLeyla.pathDistance = Integer.MAX_VALUE;
				dcitiesAL.add(cityOfLeyla);
				
				
				// honeymoon road
				for (City city1: dcitiesAL) {
					if(city1.visited == 0) {
						
						
						city1.pathDistance = 0;
					
						pq.add(city1);
			
						while(!pq.isEmpty()) {
							City c = pq.remove();
							
							c.visited = 1;
							for (Path path : c.neighbors) {
								
								City x = path.endOfPath;
								
								
								if(x.visited == 1) {
									
									continue;
								}
								
								if (path.distance < x.pathDistance) {
									
									x.minimumPath = path;
									x.pathDistance = path.distance;
									
									
									if (pq.contains(x)) {
										pq.remove(x);
									}
									
									pq.add(x);
								}
							}
						}
					}
				}
				
				// calculate the honeymoon road tax
				for (City city : dcitiesAL) {
					if (city.minimumPath != null) {
						
						Path p = city.minimumPath;
						tax += p.distance;
						
					}
				}
				
				if (timeOfMecnun <= time) {
					int ctrl = 0;
					for (City city: dcitiesAL) {
						if(city.neighbors.isEmpty()) {
							ctrl +=1;
						}
					}
					if (ctrl == 0) {
						output.println(tax*2);
					}
					
					else {
						output.println("-2");
					}
				}
		
				else {
					output.println("-1");
				}
			}
			
			
			else {
				output.println("-1");
				output.println("-1");
				}
		
		
		
		
	}
}
