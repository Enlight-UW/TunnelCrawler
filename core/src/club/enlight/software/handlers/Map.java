package club.enlight.software.handlers;

import java.util.*;

public class Map{

	public static enum Door{
		INVALID_DOOR,
		NO_DOOR,
		OPEN_DOOR,
		UNLOCKED_DOOR,
		LOCKED_DOOR,
		EXIT_DOOR,
	}

	////////////////////////////////////////////////////////////////////////////////
	//Constructor
	////////////////////////////////////////////////////////////////////////////////
	public Map(int width, int height, int roomCount, int goalDistance){
		//mapWidth MUST be odd
		mapWidth = width;
		mapHeight = height;
		mapCount = width * height;

		//Initialize the indexes
		indexTL = 0;
		indexTR = mapWidth - 1;
		indexBL = (mapHeight - 1) * mapWidth;
		indexBR = mapCount - 1;

		maxD = 4;
		goalDist = goalDistance;
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

			//Pick a starting point
			do{
				startIndex = random.nextInt(mapCount);
			} while (mapDCount[startIndex] == 0);

			this.pickEndIndex();

			List<Integer> noDoors = new ArrayList<Integer>();
			List<Integer> openDoors = new ArrayList<Integer>();

			for(int i = 0; i < 4; i++){
				if(mapDList[endIndex][i] == Door.NO_DOOR)
					noDoors.add(i);
				else if(mapDList[endIndex][i] == Door.OPEN_DOOR)
					openDoors.add(i);
			}

			int exitDoor;
			if(noDoors.size() > 0){
				exitDoor = noDoors.get(random.nextInt(noDoors.size()));
			} else {
				exitDoor = random.nextInt(openDoors.size());
				exitDoor = openDoors.get(exitDoor);
			}
			
			this.setDoor(endIndex, exitDoor, Door.EXIT_DOOR);

			mapIsGood = this.connectRooms();
			}

			//Set all of the open doors in the end room to be locked or unlocked
			final double unlockedChance = 0.15;
			boolean locked = random.nextDouble() >= unlockedChance;
			for(int i = 0; i < maxD; i++){
				if(mapDList[endIndex][i] == Door.OPEN_DOOR){
					if(locked)
						this.setDoor(endIndex, i, Door.LOCKED_DOOR);
					else this.setDoor(endIndex, i, Door.UNLOCKED_DOOR);
				}
			}

			do {
				this.pickLeverIndex();
			} while (!this.canGetToLever());

			//this.printMapASCII();
		}


		////////////////////////////////////////////////////////////////////////////////
		//Fields
		////////////////////////////////////////////////////////////////////////////////
		//maxD, map, and index variables
		private final int mapCount;
		private final int mapWidth;
		private final int mapHeight;

		private final int indexTL;
		private final int indexTR;
		private final int indexBL;
		private final int indexBR;

		private int startIndex;
		private int endIndex;
		private int leverIndex;

		private final int maxD;
		private double goalDist;
		private final int mapRoomCount;
		private final int mustRemoveCount;

		private int[] mapDCount;
		private Door[][] mapDList;

		private Random random = new Random();

		//Debug scanner
		//Scanner s = new Scanner(System.in);

		////////////////////////////////////////////////////////////////////////////////
		//Map Construction Methods
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
				//Finds the first non-removed room to start the check at
				int sIndex = 0;
				while(true){
					if(mapDCount[sIndex] > 0)
						break;
					sIndex++;
				}
				//Get a list off all the rooms that were found
				List<Integer> rooms = this.checkRooms(sIndex);

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
		private List<Integer> checkRooms(int sIndex){
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

			//Makes a list of all valid end points and finds the farthest one
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

		private void pickLeverIndex(){
			//Gets a list of all the valid lever rooms
			List<Integer> validLevers = this.checkRooms(startIndex);

			//Lists of levers far enough away from the start or end
			List<Integer> vLStartDist = new ArrayList<Integer>();
			List<Integer> vLEndDist = new ArrayList<Integer>();

			//Farthest point from the start just in case there isn't any far enough
			int farthestFromS = 0;
			int farthestDist = 0;

			//Adds rooms to the vL lists and find the farthest point
			for(int i = 0; i < validLevers.size(); i++){
				int sDist = this.distance(startIndex, validLevers.get(i));

				if(sDist >= goalDist){
					vLStartDist.add(validLevers.get(i));
				}
				if(sDist > farthestDist){
					farthestFromS = validLevers.get(i);
					farthestDist = sDist;
				}
				if(this.distance(endIndex, validLevers.get(i)) >= goalDist){
					vLEndDist.add(validLevers.get(i));
				}
			}

			//Set the lever index
			if(vLStartDist.size() == 0){
				leverIndex = farthestFromS;
			} else {
				//Get a list of indexes that are on both
				List<Integer> vLBothDist = new ArrayList<Integer>();
				for(int i = 0; i < vLStartDist.size(); i++){
					if(vLEndDist.contains(vLStartDist.get(i)))
						vLBothDist.add(vLStartDist.get(i));
				}

				//Make an array of truly valid lever indexes
				int[] actuallyValidLI;
				if(vLBothDist.size() == 0){
					actuallyValidLI = new int[vLStartDist.size()];
					for(int i = 0; i < vLStartDist.size(); i++){
						actuallyValidLI[i] = vLStartDist.get(i);
					}
				} else {
					actuallyValidLI = new int[vLBothDist.size()];
					for(int i = 0; i < vLBothDist.size(); i++){
						actuallyValidLI[i] = vLBothDist.get(i);
					}
				}

				leverIndex = actuallyValidLI[random.nextInt(actuallyValidLI.length)];
			}
		}
		private boolean canGetToLever(){
			boolean endIsOpen = false;
			for(int i = 0; i < maxD; i++){
				if(mapDList[endIndex][i] == Door.UNLOCKED_DOOR)
					endIsOpen = true;
			}

			if(endIsOpen)
				return true;
			else {
				List<Integer> foundRooms = this.checkRooms(startIndex);

				if(foundRooms.contains(leverIndex))
					return true;
				else{
					System.out.println("The lever index was unreachable at " + leverIndex);
					this.printMapASCII();
					return false;
				}
			}
		}

		//Sets the door to true at the specified index and door
		//Also sets the door that would relate to that to true
		private void setDoor(int index, int doorNumber, Door doorType) {
			switch(doorNumber)
			{
			case 0:
				mapDList[index][0] = doorType;
				if(doorType == Door.OPEN_DOOR)
					mapDCount[index - mapWidth]++;
				else if(doorType == Door.NO_DOOR || doorType == Door.INVALID_DOOR || doorType == Door.EXIT_DOOR){
					if(mapDCount[index - mapWidth] > 0)
						mapDCount[index - mapWidth]--;
				}
				mapDList[index - mapWidth][2] = doorType;
				break;
			case 1:
				mapDList[index][1] = doorType;
				if(doorType == Door.OPEN_DOOR)
					mapDCount[index + 1]++;
				else if(doorType == Door.NO_DOOR || doorType == Door.INVALID_DOOR || doorType == Door.EXIT_DOOR)
					mapDCount[index + 1]--;
				mapDList[index + 1][3] = doorType;
				break;
			case 2:
				mapDList[index][2] = doorType;
				if(doorType == Door.OPEN_DOOR)
					mapDCount[index + mapWidth]++;
				else if(doorType == Door.NO_DOOR || doorType == Door.INVALID_DOOR || doorType == Door.EXIT_DOOR)
					mapDCount[index + mapWidth]--;
				mapDList[index + mapWidth][0] = doorType;
				break;
			case 3:
				mapDList[index][3] = doorType;
				if(doorType == Door.OPEN_DOOR)
					mapDCount[index - 1]++;
				else if(doorType == Door.NO_DOOR || doorType == Door.INVALID_DOOR || doorType == Door.EXIT_DOOR)
					mapDCount[index - 1]--;
				mapDList[index - 1][1] = doorType;
				break;
			}
		}

		////////////////////////////////////////////////////////////////////////////////
		//Map Access Methods
		////////////////////////////////////////////////////////////////////////////////

		public Door[] getRoom(int roomIndex){
			return mapDList[roomIndex];
		}
		public Door[] getRoom(int roomRow, int roomColumn){
			return mapDList[roomRow * mapWidth + roomColumn];
		}

		public Door getDoor(int roomIndex, int doorIndex){
			return mapDList[roomIndex][doorIndex];
		}
		public Door getDoor(int roomRow, int roomColumn, int doorIndex){
			return mapDList[roomRow * mapWidth + roomColumn][doorIndex];
		}

		public int getMapWidth(){ return mapWidth; }

		public boolean isStart(int index){
			if(index == startIndex)
				return true;
			else return false;
		}

		public boolean isEnd(int index){
			if(index == endIndex)
				return true;
			else return false;
		}

		public boolean isLever(int index){
			if(index == leverIndex)
				return true;
			else return false;
		}

		////////////////////////////////////////////////////////////////////////////////
		//Print Methods
		////////////////////////////////////////////////////////////////////////////////

		//Debug prints
		//    private void printMapDList(){
		//    	for(int i = 0; i < mapDList.length; i++){
		//    		System.out.println(mapDList[i][0]);
		//    		System.out.println(mapDList[i][1]);
		//    		System.out.println(mapDList[i][2]);
		//    		System.out.println(mapDList[i][3]);
		//    		System.out.println();
		//    	}
		//    }

		//Console print
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
						break;
					case UNLOCKED_DOOR:
						printGrid[row * 2][column * 2 + 1] = 'O';
						break;
					case EXIT_DOOR:
						printGrid[row * 2][column * 2 + 1] = 'E';
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
						break;
					case UNLOCKED_DOOR:
						printGrid[row * 2 + 1][column * 2] = 'O';
						break;
					case EXIT_DOOR:
						printGrid[row * 2 + 1][column * 2] = 'E';
					}
				}
			}

			//Fill all the empty rooms with X's
			for(int i = 0; i < mapCount; i++){
				if(mapDCount[i] <= 0){
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

			//Put the start, end, and lever in
			int sR = (startIndex / mapWidth) * 2 + 1;
			int sC = (startIndex % mapWidth) * 2 + 1;
			int eR = (endIndex / mapWidth) * 2 + 1;
			int eC = (endIndex % mapWidth) * 2 + 1;
			int lR = (leverIndex / mapWidth) * 2 + 1;
			int lC = (leverIndex % mapWidth) * 2 + 1;
			printGrid[sR][sC] = 'S';
			printGrid[eR][eC] = 'E';
			printGrid[lR][lC] = 'L';

			//Print the printGrid
			for(int row = 0; row < mapHeight * 2 + 1; row++){
				System.out.println(printGrid[row]);
			}
		}

	}//end of class Map
