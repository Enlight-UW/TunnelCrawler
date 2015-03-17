
import java.util.*;

enum Door{
	INVALID_DOOR,
	NO_DOOR,
	OPEN_DOOR,
	UNLOCKED_DOOR,
	LOCKED_DOOR,
}

public class Map{

	////////////////////////////////////////////////////////////////////////////////
	//Constructor
	////////////////////////////////////////////////////////////////////////////////
	Map(int width, int height, int roomCount, int endDistance){
		//mapWidth MUST be odd
		mapWidth = width;
		mapHeight = height;
		mapCount = width * height;

		//Initialize the indexes
		indexTL = 0;
		indexTR = mapWidth - 1;
		indexBL = (mapHeight - 1) * mapWidth;
		indexBR = mapCount - 1;

		goalDist = endDistance;
		maxD = 4;
		mapRoomCount = roomCount;
		mustRemoveCount = mapCount - mapRoomCount;

		//Set the size of the field arrays
		mapDCount = new int[mapCount];
		mapDList = new Door[mapCount][maxD];

		//Set up the map
		boolean mapIsGood = false;
		while(!mapIsGood){
			boolean mapIsK = false;
			while(!mapIsK)
			{
				this.setDoorsToDefault();
				this.setBorderDoors();
				this.setDoorCountGrid();
				this.borderCountReduce();
				this.assignDoors();
				mapIsK = this.removeRooms();
			}
			mapIsGood = this.connectRooms();
		}

		//Pick a starting point
		do{
			startIndex = random.nextInt(mapCount);
		} while (mapDCount[startIndex] == 0);

		this.pickEndIndex();
		//:D
		
		//Set all of the open doors in the end room to be locked
		for(int i = 0; i < maxD; i++){
			if(mapDList[endIndex][i] == Door.OPEN_DOOR){
				this.setDoor(endIndex, i, Door.LOCKED_DOOR);
			}
		}

		//TODO: Print out the map
		this.printMapASCII();
	}

	////////////////////////////////////////////////////////////////////////////////
	//Fields
	////////////////////////////////////////////////////////////////////////////////
	//maxD, map, and index variables
	final int mapCount;
	final int mapWidth;
	final int mapHeight;

	final int indexTL;
	final int indexTR;
	final int indexBL;
	final int indexBR;

	int startIndex;
	int endIndex;
	int leverIndex;

	double goalDist;
	final int maxD;
	final int mapRoomCount;
	final int mustRemoveCount;

	private int[] mapDCount;
	private Door[][] mapDList;

	Random random = new Random();

	Scanner s = new Scanner(System.in);


	////////////////////////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////////////////////////

	private void setDoorsToDefault(){
		for(int i = 0; i < mapCount; i++){
			for(int j = 0; j < maxD; j++){
				mapDList[i][j] = Door.NO_DOOR;
			}
		}
	}

	private void setBorderDoors(){
		//Set the top and bottom rows to INVALD_DOOR
		for(int i = 0; i < mapWidth; i++){
			mapDList[i][0] = Door.INVALID_DOOR;
			mapDList[mapCount - i - 1][2] = Door.INVALID_DOOR;
		}
		//Set the left and right columns to INVALID_DOOR
		for(int i = 0; i < mapHeight; i++){
			mapDList[i*mapWidth][3] = Door.INVALID_DOOR;
			mapDList[i*mapWidth + mapWidth - 1][1] = Door.INVALID_DOOR;
		}
	}

	private void setDoorCountGrid(){
		//Set top row
		for(int i = 0; i < mapWidth; i += 2){
			int temp = 0;
			for(int j = 0; j < maxD; j++)
			{
				temp += random.nextInt(2);
			}
			mapDCount[i] = temp;
		}

		//Set advanced rows
		for(int i = mapWidth; i < mapCount; i++){
			if(mapDCount[i - mapWidth] == 0){
				int temp = 0;
				for(int j = 0; j < maxD; j++){
					temp += random.nextInt(2);
				}
				mapDCount[i] = temp;
			}
		}
	}

	private void borderCountReduce(){
		for(int i = indexTL; i <= indexTR; i++){
			if(mapDCount[i] > 3)
				mapDCount[i] = 3;
		}
		//Bottom row
		for(int i = indexBL; i <= indexBR; i++){
			if(mapDCount[i] > 3)
				mapDCount[i] = 3;
		}
		//Left column
		for(int i = indexTL; i <= indexBL; i+=mapWidth){
			if(mapDCount[i] > 3)
				mapDCount[i] = 3;
		}
		//Right column
		for(int i = indexTR; i <= indexBR; i+=mapWidth){
			if(mapDCount[i] > 3)
				mapDCount[i] = 3;
		}
		//Four corners
		if(mapDCount[indexTL] > 2)
			mapDCount[indexTL] = 2;
		if(mapDCount[indexTR] > 2)
			mapDCount[indexTR] = 2;
		if(mapDCount[indexBL] > 2)
			mapDCount[indexBL] = 2;
		if(mapDCount[indexBR] > 2)
			mapDCount[indexBR] = 2;
	}

	private void assignDoors(){
		for(int index = 0; index < mapCount; index+=2){
			List<Integer> availableD = new ArrayList<Integer>();
			for(int i = 0; i < maxD; i++){
				if(mapDList[index][i] == Door.NO_DOOR){
					availableD.add(i);
				}
			}

			int dCount = 0;
			while(availableD.size() > 0 && dCount < mapDCount[index]){
				int listSelect = random.nextInt(availableD.size());
				int doorSelect = availableD.get(listSelect);
				availableD.remove(listSelect);
				this.setDoor(index, doorSelect, Door.OPEN_DOOR);
				dCount++;
			}
		}
	}

	private boolean removeRooms(){
		//Count how many rooms already are removed
		int removedCount = 0;
		for(int i = 0; i < mapCount; i++){
			if(mapDCount[i] == 0)
				removedCount++;
		}

		//If too many rooms are removed, redo everything cuz fucked
		//		int needToRemove = mapCount - mapRoomCount - removedCount;
		if(removedCount > mustRemoveCount)
			return false;
		//If exactly enough rooms are removed, say is k
		else if(removedCount == mustRemoveCount)
			return true;
		//If not enough rooms are removed...
		else
		{
			//Make a list of the indexes of non-removed rooms
			List<Integer> openRooms = new ArrayList<Integer>();
			for(int index = 0; index < mapCount; index++){
				if(mapDCount[index] > 0)
					openRooms.add(index);
			}

			//While rooms still need to be removed
			while(removedCount < mustRemoveCount){
				if(openRooms.size() == 0){
					return false;
				}
				//Select a random room from the list to remove
				int remID = random.nextInt(openRooms.size());
				int remIndex = openRooms.get(remID);

				//Find all of remIndex's neighbors
				List<Integer> remNeighbors = this.remNeighborList(remIndex);
				int remNeighborCount = remNeighbors.size();

				boolean removable = true;
				if(remNeighborCount > 1)
					removable = false;
				else if(remNeighborCount == 1)
				{
					int neighborsNeighborCount = this.remNeighborCount(remNeighbors.get(0));
					if(neighborsNeighborCount > 0)
						removable = false;
				}

				if(removable){
					mapDCount[remIndex] = 0;
					for(int d = 0; d < maxD; d++){
						if(mapDList[remIndex][d] == Door.OPEN_DOOR)
							this.setDoor(remIndex, d, Door.NO_DOOR);
					}
					removedCount++;
				}

				//Remove the room from the list (need to do either way)
				openRooms.remove(remID);
			}

			//Say is k
			return true;
		}
	}
	private int remNeighborCount(int index){
		int remNeighborCount = 0;

		boolean up = mapDList[index][0] == Door.INVALID_DOOR;
		boolean down = mapDList[index][2] == Door.INVALID_DOOR;
		boolean left = mapDList[index][3] == Door.INVALID_DOOR;
		boolean right = mapDList[index][1] == Door.INVALID_DOOR;

		if(!up){
			if(mapDCount[index - mapWidth] == 0)
				remNeighborCount++;
		}
		if(!down){
			if(mapDCount[index + mapWidth] == 0)
				remNeighborCount++;
		}
		if(!left){
			if(mapDCount[index - 1] == 0)
				remNeighborCount++;
		}
		if(!right){
			if(mapDCount[index + 1] == 0)
				remNeighborCount++;
		}

		if(!up && !right){
			if(mapDCount[index - mapWidth + 1] == 0)
				remNeighborCount++;
		}
		if(!down && !right){
			if(mapDCount[index + mapWidth + 1] == 0)
				remNeighborCount++;
		}
		if(!down && !left){
			if(mapDCount[index + mapWidth - 1] == 0)
				remNeighborCount++;
		}
		if(!up && !left){
			if(mapDCount[index - mapWidth - 1] == 0)
				remNeighborCount++;
		}

		return remNeighborCount;
	}
	private List<Integer> remNeighborList(int index){
		List<Integer> remNeighbors = new ArrayList<Integer>();

		boolean up = mapDList[index][0] != Door.INVALID_DOOR;
		boolean right = mapDList[index][1] != Door.INVALID_DOOR;
		boolean down = mapDList[index][2] != Door.INVALID_DOOR;
		boolean left = mapDList[index][3] != Door.INVALID_DOOR;

		if(up && mapDCount[index - mapWidth] == 0)
			remNeighbors.add(index - mapWidth);
		if(down && mapDCount[index + mapWidth] == 0)
			remNeighbors.add(index + mapWidth);
		if(left && mapDCount[index - 1] == 0)
			remNeighbors.add(index - 1);
		if(right && mapDCount[index + 1] == 0)
			remNeighbors.add(index + 1);

		if(up && right && mapDCount[index - mapWidth + 1] == 0)
			remNeighbors.add(index - mapWidth + 1);
		if(down && right && mapDCount[index + mapWidth + 1] == 0)
			remNeighbors.add(index + mapWidth + 1);
		if(down && left && mapDCount[index + mapWidth - 1] == 0)
			remNeighbors.add(index + mapWidth - 1);
		if(up && left && mapDCount[index - mapWidth - 1] == 0)
			remNeighbors.add(index - mapWidth - 1);

		return remNeighbors;
	}	

	private boolean connectRooms(){

		//While the map is not continuous, connect rooms (to make it so
		boolean mapIsContinuous = false;
		while(!mapIsContinuous){

			//Get a list off all the rooms that were found
			List<Integer> rooms = this.checkRooms();

			//If all of the rooms were found, then the map is continuous.
			if(rooms.size() == mapRoomCount){
				mapIsContinuous = true;
			} else {
				//Until a connection is made, look through every room until a
				//possible connection is found
				boolean roomConnected = false;
				int tryRoom = 0;
				while(!roomConnected){
					if(mapDCount[tryRoom] > 0 && !rooms.contains(tryRoom)){
						for(int d = 0; d < maxD; d++){
							if(mapDList[tryRoom][d] == Door.NO_DOOR){
								int oppositeRoom = 0;
								switch(d){
								case 0:
									oppositeRoom = tryRoom - mapWidth;
									break;
								case 1:
									oppositeRoom = tryRoom + 1;
									break;
								case 2:
									oppositeRoom = tryRoom + mapWidth;
									break;
								case 3:
									oppositeRoom = tryRoom - 1;
									break;
								}
								if(rooms.contains(oppositeRoom)){
									mapDCount[tryRoom]++;
									this.setDoor(tryRoom, d, Door.OPEN_DOOR);
									d = maxD;
									roomConnected = true;
								}
							}
						}
					}
					tryRoom++;
					if(tryRoom >= mapCount)
						return false;
				}
			}
		}

		return true;
	}
	private List<Integer> checkRooms(){
		//Finds the first non-removed room to start the check at
		int sIndex = 0;
		while(true){
			if(mapDCount[sIndex] > 0)
				break;
			sIndex++;
		}

		//Adds all non-removed rooms to a list, then remove the starting room
		List<Integer> allRooms = new ArrayList<Integer>();
		for(int i = 0; i < mapCount; i++){
			if(mapDCount[i] > 0){
				allRooms.add(i);
			}
		}
		allRooms.remove(allRooms.indexOf(sIndex));

		List<Integer> foundRooms = new ArrayList<Integer>();
		foundRooms.add(sIndex);

		//Make a queue for what rooms to check then add the starting room
		Queue<Integer> checkList = new LinkedList<Integer>();
		checkList.add(sIndex);

		//While there are rooms to check, check the rooms
		while(checkList.size() > 0){
			//get the first room in the queue
			int index = checkList.remove();

			//Check all four doors to see if there are rooms that it connects
			//to and haven't been checked yet
			for(int d = 0; d < maxD; d++){
				if(mapDList[index][d] == Door.OPEN_DOOR){
					int foundRoomIndex = 0;

					switch(d){
					case 0:
						foundRoomIndex = index - mapWidth;
						break;
					case 1:
						foundRoomIndex = index + 1;
						break;
					case 2:
						foundRoomIndex = index + mapWidth;
						break;
					case 3:
						foundRoomIndex = index - 1;
						break;
					}

					if(allRooms.contains(foundRoomIndex)){
						foundRooms.add(foundRoomIndex);
						allRooms.remove(allRooms.indexOf(foundRoomIndex));
						checkList.add(foundRoomIndex);
					}
				}//End of if(mapDList[index][d]...
			}//End of for
		}//End of while

		return foundRooms;
	}

	private void pickEndIndex(){
		List<Integer> validEnds = new ArrayList<Integer>();
		int farthestPoint = 0;
		int farthestDist = 0;

		//Makes a list of all valid end points
		for(int i = 0; i < mapCount; i++){
			if(mapDCount[i] > 0){
				int dist = this.distance(startIndex, i);

				if(dist >= goalDist){
					validEnds.add(i);
				}
				if(dist > farthestDist){
					farthestPoint = i;
					farthestDist = dist;
				}
			}
		}

		//If there are no valid end points, the end point is the farthest point
		//from the start
		if(validEnds.size() == 0){
			endIndex = farthestPoint;
		} else {
			//Stores the list of end points in an array with their distances
			int[][] endList = new int[2][validEnds.size()];

			for(int i = 0; i < validEnds.size(); i++){
				endList[0][i] = validEnds.get(i);
				endList[1][i] = this.distance(startIndex, endList[0][i]);
			}

			//Picks a random end point weighted by their distance
			int distSum = 0;
			for(int i = 0; i < endList[0].length; i++){
				distSum += endList[1][i];
			}

			int[] weightedEndList = new int[distSum];
			int indexCount = 0;
			for(int eP = 0; eP < endList[0].length; eP++){
				for(int dC = 0; dC < endList[1][eP]; dC++){
					weightedEndList[indexCount] = endList[0][eP];
					indexCount++;
				}
			}

			int wELI = random.nextInt(distSum);
			endIndex = weightedEndList[wELI];
		}
	}
	private int distance(int index1, int index2){
		int x1 = index1 % mapWidth;
		int y1 = index1 / mapWidth;
		int x2 = index2 % mapWidth;
		int y2 = index2 / mapWidth;

		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);

		return dx + dy;
	}

	//Sets the door to true at the specified index and door
	//Also sets the door that would relate to that to true
	private void setDoor(int index, int doorNumber, Door doorType)
	{
		switch(doorNumber)
		{
		case 0:
			mapDList[index][0] = doorType;
			if(doorType == Door.OPEN_DOOR)
				mapDCount[index - mapWidth]++;
			else if(doorType == Door.NO_DOOR || doorType == Door.INVALID_DOOR)
				mapDCount[index - mapWidth]--;
			mapDList[index - mapWidth][2] = doorType;
			break;
		case 1:
			mapDList[index][1] = doorType;
			if(doorType == Door.OPEN_DOOR)
				mapDCount[index + 1]++;
			else if(doorType == Door.NO_DOOR || doorType == Door.INVALID_DOOR)
				mapDCount[index + 1]--;
			mapDList[index + 1][3] = doorType;
			break;
		case 2:
			mapDList[index][2] = doorType;
			if(doorType == Door.OPEN_DOOR)
				mapDCount[index + mapWidth]++;
			else if(doorType == Door.NO_DOOR || doorType == Door.INVALID_DOOR)
				mapDCount[index + mapWidth]--;
			mapDList[index + mapWidth][0] = doorType;
			break;
		case 3:
			mapDList[index][3] = doorType;
			if(doorType == Door.OPEN_DOOR)
				mapDCount[index - 1]++;
			else if(doorType == Door.NO_DOOR || doorType == Door.INVALID_DOOR)
				mapDCount[index - 1]--;
			mapDList[index - 1][1] = doorType;
			break;
		}
	}



	////////////////////////////////////////////////////////////////////////////////
	//Print Methods
	////////////////////////////////////////////////////////////////////////////////
	private void printIndexData(){
		System.out.println(indexTL);
		System.out.println(indexTR);;
		System.out.println(indexBL);
		System.out.println(indexBR);
	}

	private void printMapData(){
		for(int index = 0; index < mapCount; index++)
		{
			System.out.print("Door " + index + ": " + mapDCount[index] + " ");
			for(int d = 0; d < maxD; d++)
			{
				System.out.print(mapDList[index][d] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}

	private void printMapASCII(){
		char[][] printGrid = new char[mapHeight * 2 + 1][mapWidth * 2 + 1];

		//Fill the bottom most row with -'s
		for(int column = 0; column < mapWidth * 2 + 1; column++){
			printGrid[mapHeight*2][column] = '-';
		}

		//Fill the rightmost row with |'s
		for(int row = 0; row < mapHeight * 2 + 1; row++){
			printGrid[row][mapWidth * 2] = '|';
		}

		//Do all the top doors
		for(int row = 0; row < mapHeight; row++){
			for(int column = 0; column < mapWidth; column++){
				switch(mapDList[row*mapWidth + column][0]){
				case INVALID_DOOR:
				case NO_DOOR:
					printGrid[row * 2][column * 2 + 1] = '-';
					break;
				case OPEN_DOOR:
					printGrid[row * 2][column * 2 + 1] = ' ';
					break;
				case LOCKED_DOOR:
					printGrid[row * 2][column * 2 + 1] = '@';
				}
			}
		}

		//Do all the left doors
		for(int row = 0; row < mapHeight; row++){
			for(int column = 0; column < mapWidth; column++){
				switch(mapDList[row*mapWidth + column][3]){
				case INVALID_DOOR:
				case NO_DOOR:
					printGrid[row * 2 + 1][column * 2] = '|';
					break;
				case OPEN_DOOR:
					printGrid[row * 2 + 1][column * 2] = ' ';
					break;
				case LOCKED_DOOR:
					printGrid[row * 2 + 1][column * 2] = '@';
				}
			}
		}

		//Fill all the empty rooms with |'s
		for(int i = 0; i < mapCount; i++){
			if(mapDCount[i] == 0){
				int x = (i % mapWidth) * 2 + 1;
				int y = (i / mapWidth) * 2 + 1;
				printGrid[y][x] = 'X';
			}
		}

		//Do all the +'s
		for(int row = 0; row < mapHeight * 2 + 1; row +=2){
			for(int column = 0; column < mapWidth * 2 + 1; column += 2){
				printGrid[row][column] = '+';
			}
		}

		//Put the start and end in
		int sR = (startIndex / mapWidth) * 2 + 1;
		int sC = (startIndex % mapWidth) * 2 + 1;
		int eR = (endIndex / mapWidth) * 2 + 1;
		int eC = (endIndex % mapWidth) * 2 + 1;
		printGrid[sR][sC] = 'S';
		printGrid[eR][eC] = 'E';

		//Print the printGrid
		for(int row = 0; row < mapHeight * 2 + 1; row++){
			System.out.println(printGrid[row]);
		}
	}

}//end of class Map
