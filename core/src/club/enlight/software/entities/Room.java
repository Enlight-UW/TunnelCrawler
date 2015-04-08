package club.enlight.software.entities;

import java.util.*;

public class Room {
	
	//Constructors
	public Room(int roomIndex, club.enlight.software.handlers.Map map, int enteredFrom, int enemyCount, int columnCount){
		//Get the doors
		topDoor = map.getDoor(roomIndex, 0);
		rightDoor = map.getDoor(roomIndex, 1);
		bottomDoor = map.getDoor(roomIndex, 2);
		leftDoor = map.getDoor(roomIndex, 3);
		
		//Set the room dimensions
		roomWidth = 20;
		roomHeight = 16;

        isExit = map.isEnd(roomIndex);
        isLever = map.isLever(roomIndex);

		this.placeTheCharacter(enteredFrom);
        if(isExit)
            this.placeStairs();
        if(isLever)
            this.placeLever();

        this.placeEnemies(enemyCount);

        if(columnCount > roomWidth * roomHeight / 4){
            columnCount = roomWidth * roomHeight / 4;
        }
        this.placeColumns(columnCount);
	}
	
	//Fields
	club.enlight.software.handlers.Map.Door topDoor;
    club.enlight.software.handlers.Map.Door bottomDoor;
    club.enlight.software.handlers.Map.Door leftDoor;
    club.enlight.software.handlers.Map.Door rightDoor;
	
	final boolean isExit;
	final boolean isLever;
	
	final int roomWidth;
	final int roomHeight;
	
	List<GameObject> creatures = new ArrayList<GameObject>();



	//Room creation methods
	private void placeTheCharacter(int enteredFrom){
		int playerStartX;
		int playerStartY;
		switch(enteredFrom){
		case 0:
			playerStartX = roomWidth / 2;
			playerStartY = 0;
			break;
		case 1:
			playerStartX = roomWidth;
			playerStartY = roomHeight / 2;
			break;
		case 2:
			playerStartX = roomWidth / 2;
			playerStartY = roomHeight;
			break;
		case 3:
			playerStartX = 0;
			playerStartY = roomHeight / 2;
			break;
		default:
			playerStartX = roomWidth / 2;
			playerStartY = roomWidth / 2;
		}

        //add monsters here
		//creatures.add(new GameObject(playerStartX, playerStartY, GameObject.Direction.DOWN, 1));
	}

    private void placeStairs(){
    }

    private void placeLever(){
    }

	private void placeEnemies(int enemyCount){
        //TODO: Place some enemies when some exist
    }

    private void placeColumns(int columnCount){

    }
}
