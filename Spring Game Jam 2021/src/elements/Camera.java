package elements;

public class Camera {

	public int x;
	public int y;

	public boolean moving = false;

	public int moveDirection = 0;

	public int prevY, prevX;
	private int movespeed = 20;

	public Camera(int x, int y) {

		this.x = x;
		this.y = y;

		prevY = y;
		prevX = x;

	}

	public void moveUp() {

		if (moving) {
			return;
		}
		
		moving = true;
		moveDirection = 1;
		prevY = y;
		prevX = x;

	}

	public void moveDown() {

		if (moving) {
			return;
		}
		
		
		moving = true;
		moveDirection = 2;
		prevY = y;
		prevX = x;

	}

	public void moveRight() {

		if (moving) {
			return;
		}
		
		moving = true;
		moveDirection = 3;
		prevY = y;
		prevX = x;

	}

	
	public void moveLeft() {

		if (moving) {
			return;
		}
		
		moving = true;
		moveDirection = 4;
		prevY = y;
		prevX = x;

	}

	
	public void move() {

		if (moveDirection == 1) {
			y += movespeed;
			if (y == prevY + 1080) {
				moving = false;
			}
		} else if (moveDirection == 2) {
			y -= movespeed;
			if (y == prevY - 1080) {
				moving = false;
			}
		} else if (moveDirection == 3) {
			x += movespeed;
			if (x == prevX + 1920) {
				moving = false;
			}
		} else if (moveDirection == 4) {
			x -= movespeed;
			if (x == prevX - 1920) {
				moving = false;
			}
		}

	}

}
