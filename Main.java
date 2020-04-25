
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class Main
{
	public static void main(String[] args) {
	    
		try {
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter the number of places");
			Integer  numberOfDistricts = scan.nextInt();
			District[] districts= new District[numberOfDistricts];
			Integer counter = 0;
			do {
				
				System.out.println("Enter the name of district ...");
				String distName = scan.next();
				 
				if(isExist(distName, districts)){
					System.out.println("This place is already exists ...");
				}
				else{
					districts[counter] = new District("" + counter, distName );
					counter++;
				}
				
			} while (counter < numberOfDistricts);
			   
	
			 
			 System.out.println("Enter the number of roads");
			 Integer  numberOfRoads = scan.nextInt();
			 Road[] roads = new Road[numberOfRoads];
			 counter = 0;
			
			do{
				System.out.println("-----------------------------------------------------");
				
				System.out.println("Road#"+ counter +" - Enter details ...");
				System.out.println("    District of origin ...");
				String origin = scan.next();

				if(!isExist(origin, districts)){
				   System.out.println("This district does not exist");
				   continue;
				}
		   
				District originDistrict = find(origin, districts);

				System.out.println("    District of destination ...");
				String  destination = scan.next();

				if(!isExist(destination, districts)){
					System.out.println("This district does not exist");
					continue;
				 }
			
				 District destinationDistrict = find(destination, districts);

				System.out.println("    Distnance ...");
				Integer  distnance = scan.nextInt();

				Road newRoad = new Road(""+ counter, originDistrict, destinationDistrict, distnance);
				roads[counter] = newRoad;
				counter++;
			 }while(counter < numberOfRoads);
			 

			List<District> districtList = Arrays.asList(districts); 
			
			Route route = new Route(districtList, Arrays.asList(roads));
			
			findRoute(route, districtList, scan);
			
			System.out.println("Good Bye");

			scan.close();
		 
		 } catch(Exception e) {
			 System.out.println(e.getMessage());
		 }
	}
	
	static List<District> enterDistrict(List<District> existingList, Scanner scan){
		System.out.println("Where do you want to start your journey? ...");
		String name = scan.next();
		District dist = new District("id", name);
		return existingList;
	}
	
	static boolean isExist(String name, District[] districts ){
		
		if(districts.length == 0){
			return false;
		}
		for (District dist : districts) {
			if(dist != null){
				String nn = dist.getName();
				if (nn.equals(name)) {
					return true;
				}
			}
			
        }
		//throw new RuntimeException("Should not happen");
		return false;
	}
	
	static District find(String name, District[] districts ){
		for (District dist : districts) {
            String nn = dist.getName();
				if (nn.equals(name)) {
					return dist;
				}
        }
		throw new RuntimeException("Should not happen");
		
	}

	static Integer getIndex(String name, List<District>districtList ){
		Integer index = 0;
		for(District dist: districtList){
			String nn = dist.getName();
			if (nn.equals(name)) {
				return index;
			}
			else{
				index++;
			}
		}
		return index;
	}

	static void findRoute(Route route, List<District> districtList, Scanner scan ){
			//Get the starting district name of journey
			System.out.println("Where do you want to start your journey? ...");
			String startPoint = scan.next();
			Integer startingIndex = getIndex(startPoint, districtList);
		
			System.out.println("Where do you want to end your journey? ...");
			String endPoint = scan.next();
			Integer endingIndex = getIndex(endPoint, districtList);

			//start with the starting index-
			route.execute(districtList.get(startingIndex));

			LinkedList<District> path = route.getPath(districtList.get(endingIndex));


			String finalPath = "";
			for (District district : path) {
				 if(finalPath.isEmpty()){
					 finalPath = district.getName();
				 }
				 else{
					finalPath = finalPath + "-" + district.getName();
				 }
			}
				
			System.out.println("Shortest route of your journey from "+ startPoint +" to "+ endPoint +" is the following ...");
			System.out.println(finalPath);

			System.out.println("Do you want to start another journey? ... yes/no");
			String decision = scan.next();
			if(decision.equals("yes"))
			{
				findRoute(route, districtList, scan);
			}

	}


}

// class Road{
// 	private final String origin;
// 	private final String destination;
// 	private final Integer distance;

// 	public Road(String origin, String destination, Integer distance){
// 		this.origin = origin;
// 		this.destination = destination;
// 		this.distance = distance;
// 	}
// }