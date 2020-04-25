
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class Main
{
	public static void main(String[] args) {
	    
		try {
			Scanner scan = new Scanner(System.in);

			List<Vertex> districtList  = new ArrayList<>();
			districtList = enterDistrict(districtList, scan);
			
			List<Edge> roadList  = new ArrayList<>();
			roadList = enterRoad(roadList, districtList, scan);
			Route route = new Route(districtList, roadList);
			findRoute(route, districtList, scan);
			
			System.out.println("");
			System.out.println("");
			System.out.println("##########################################################");
			System.out.println("Good Bye");

			scan.close();
		 
		 } catch(Exception e) {
			 System.out.println(e.getMessage());
		 }
	}
	
	static List<Vertex> enterDistrict(List<Vertex> existingList, Scanner scan){
		System.out.println("------------------------------------------------------------------------");
		System.out.println("Enter the name of district ... [N = No]");
		System.out.println("------------------------------------------------------------------------");

		String name = scan.next();
		name = name.toLowerCase();
		if(!name.equals("n")){
			if(isExist(name, existingList)){
				System.out.println("This place is already exists ...");
				enterDistrict(existingList, scan);
			}
			else{
				Integer count = existingList.size();
				Vertex dist = new Vertex("" + count, name);
				existingList.add(dist);
				enterDistrict(existingList, scan);
			}
		}

		return existingList;
	}
	
	static List<Edge> enterRoad(List<Edge> existingRoads, List<Vertex> districts, Scanner scan){
		Integer counter = existingRoads.size() + 1;
		System.out.println("------------------------------------------------------------------------");
		System.out.println("Enter road#"+ counter +": start, end, distance   [N = No] ...");
		System.out.println("i.e. dhaka, rajshahi, 3");
		System.out.println("------------------------------------------------------------------------");

		String roadDetails = scan.next();
		roadDetails = roadDetails.toLowerCase();
		if(!roadDetails.equals("n")){
				String[] inputArray = roadDetails.split(",");
				
				String origin = inputArray[0];

				if(!isExist(origin, districts)){
				   System.out.println("This district does not exist");
				   enterRoad(existingRoads, districts, scan);
				}
		   
				Vertex originDistrict = find(origin, districts);

				String  destination = inputArray[1];

				if(!isExist(destination, districts)){
					System.out.println("This district does not exist");
					enterRoad(existingRoads, districts, scan);
				 }
			
				Vertex destinationDistrict = find(destination, districts);

			
				Integer distnance = Integer.parseInt(inputArray[2]);

				Edge newRoad = new Edge(""+ counter, originDistrict, destinationDistrict, distnance);
				existingRoads.add(newRoad);
				enterRoad(existingRoads, districts, scan);
		}

		return existingRoads;
	}

	static boolean isExist(String name, List<Vertex> districts ){

		if(districts.size() == 0){
			return false;
		}
		for (Vertex dist : districts) {
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

	static Vertex find(String name, List<Vertex> districts ){
		for (Vertex dist : districts) {
            String nn = dist.getName();
				if (nn.equals(name)) {
					return dist;
				}
        }
		throw new RuntimeException("Should not happen");		
	}

	static Integer getIndex(String name, List<Vertex>districtList ){
		Integer index = 0;
		for(Vertex dist: districtList){
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

	static void findRoute(Route route, List<Vertex> districtList, Scanner scan ){
			//Get the starting district name of journey
			System.out.println("Where do you want to start your journey? ...");
			String startPoint = scan.next();
			Integer startingIndex = getIndex(startPoint, districtList);
		
			System.out.println("Where do you want to end your journey? ...");
			String endPoint = scan.next();
			Integer endingIndex = getIndex(endPoint, districtList);

			LinkedList<Vertex> path = null;
			//start with the starting index-
			try {
				route.execute(districtList.get(startingIndex));
				 path = route.getPath(districtList.get(endingIndex));
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("Please start again ..");
				findRoute(route, districtList, scan);
			}
			


			String finalPath = "";
			for (Vertex district : path) {
				 if(finalPath.isEmpty()){
					 finalPath = district.getName();
				 }
				 else{
					finalPath = finalPath + "-" + district.getName();
				 }
			}
			
			System.out.println("=============================================================================");
			System.out.println("Shortest route of your journey from "+ startPoint +" to "+ endPoint +" is the following ...");
			System.out.println(finalPath);
			System.out.println("=============================================================================");

			System.out.println("Do you want to start another journey? ...  [Y = Yes, N = No]");
			String decision = scan.next();
			decision = decision.toLowerCase();
			if(decision.equals("y"))
			{
				findRoute(route, districtList, scan);
			}
	}
}
