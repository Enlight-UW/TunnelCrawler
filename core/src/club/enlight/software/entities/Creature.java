package club.enlight.software.entities;

public class Creature {
    public static enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

	//Constructor
	public Creature(int startX, int startY, Direction startFacing, double creatureVelocity) {
		xPosition = startX;
		yPosition = startY;
		facing = startFacing;
		velocity = creatureVelocity;
	}
	
	//Fields
	private int xPosition;
	private int yPosition;
	private Direction facing;
	private double velocity;
	
	//Methods
	public int getXPosition() { return xPosition; }
	public int getYPosition() { return yPosition; }
	public Direction getDirection() { return facing; };
	
	public void moveForward(double dt){
		switch(facing){
		case UP:
			this.moveUp(dt);
			break;
		case DOWN:
			this.moveDown(dt);
			break;
		case LEFT:
			this.moveLeft(dt);
			break;
		case RIGHT:
			this.moveRight(dt);
			break;
		}
	}
	public void moveBackward(double dt){
		switch(facing){
		case UP:
			this.moveDown(dt);
			break;
		case DOWN:
			this.moveUp(dt);
			break;
		case LEFT:
			this.moveRight(dt);
			break;
		case RIGHT:
			this.moveLeft(dt);
			break;
		}
	}
	
	public void moveUp(double dt){	yPosition -= velocity * dt;	}
	public void moveDown(double dt){ yPosition += velocity * dt; }
	public void moveLeft(double dt){ xPosition -= velocity * dt; }
	public void moveRight(double dt){ xPosition += velocity * dt; }
	
	public void faceUp(){ facing = Direction.UP; }
	public void faceDown(){ facing = Direction.DOWN; }
	public void faceLeft(){ facing = Direction.LEFT; }
	public void faceRight(){ facing = Direction.RIGHT; }
	
	public void turnLeft(){
		switch(facing){
		case UP:
			this.faceLeft();
			break;
		case DOWN:
			this.faceRight();
			break;
		case LEFT:
			this.faceDown();
			break;
		case RIGHT:
			this.faceUp();
			break;
		}
	}
	public void turnRight(){
		switch(facing){
		case UP:
			this.faceRight();
			break;
		case DOWN:
			this.faceLeft();
			break;
		case LEFT:
			this.faceUp();
			break;
		case RIGHT:
			this.faceDown();
			break;
		}
	}
	public void turnAround(){
		switch(facing){
		case UP:
			this.faceDown();
			break;
		case DOWN:
			this.faceUp();
			break;
		case LEFT:
			this.faceRight();
			break;
		case RIGHT:
			this.faceLeft();
			break;
		}
	}
}