package ucheike.uchechukwu;
import java.awt.Color;

public class Canvas {
	
	Color[][] board;
	
	private static int DEFAULT_WIDTH = 20;
	private static int DEFAULT_HEIGHT = 20;
	
	private int width;
	private int height;
	

	public Canvas() {
		// Board is a 2D Array of Color objects. Color objects are what Java uses to represent colors. Each cell of the array represents a pixel.
		// Think of it as like having a 2D array of integers, but the actual values in the Array can be used directly in our GUI class.
		board = new Color[DEFAULT_HEIGHT][DEFAULT_WIDTH];
		
		this.width = DEFAULT_WIDTH;
		this.height = DEFAULT_HEIGHT;
		
		this.clearBoard(); // Sets all the pixels 
	}
	
	public Canvas(int width, int height) {
		board = new Color[height][width];
		
		this.width = width;
		this.height = height;
		
		this.clearBoard();
	}
	
	public Color getCell(int row, int col) {
		return this.board[row][col];
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void setCell(int row, int col, Color color) {
		this.board[row][col] = color;
	}
	
	public void clearBoard() {
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				this.board[i][j] = Color.WHITE;
			}
		}
	}
}
