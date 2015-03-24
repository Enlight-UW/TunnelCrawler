package club.enlight.software.handlers;

import java.util.*;
import club.enlight.software.entities.*;

public class Room {
	
	//Constructors
	public Room(int roomIndex, Map map, int enteredFrom, int enemyCount){
		//Get the doors
		topDoor = map.getDoor(roomIndex, 0);
		rightDoor = map.getDoor(roomIndex, 1);
		bottomDoor = map.getDoor(roomIndex, 2);
		leftDoor = map.getDoor(roomIndex, 3);
		
		//Set the room dimensions
		roomWidth = 20;
		roomHeight = 16;

        isExit = map.isExit(roomIndex);
        isLever = true;

		this.placeTheCharacter(enteredFrom);
        this.placeEnemies(enemyCount);
	}
	
	//Fields
	Map.Door topDoor;
    Map.Door bottomDoor;
    Map.Door leftDoor;
    Map.Door rightDoor;
	
	final boolean isExit;
	final boolean isLever;
	
	final int roomWidth;
	final int roomHeight;
	
	List<Creature> creatures = new ArrayList<Creature>();
	
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
		creatures.add(new Creature(playerStartX, playerStartY, Creature.Direction.DOWN, 1));
	}
	
	private void placeEnemies(int enemyCount){

    }
}
