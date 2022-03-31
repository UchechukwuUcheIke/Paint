package ucheike.uchechukwu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;

import javax.swing.UIManager;

class Pixel extends JButton { // Extends here means that the Pixel class has all the methods of the JButton class
	// We use a Pixel class that extends JButton so we have the functionality of a JButton while giving us a way to store its column and row
	private int row;
	private int col;
	
	public Pixel(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.col;
	}
}


public class PaintApp extends JFrame {
	// Static Variables that won't change for our code. Not too important and mainly here for variables we don't want to change in our class
	private static final long serialVersionUID = 1L;
	private static int FRAME_WIDTH = 800;
	private static int FRAME_HEIGHT = 800;
	private static Color ERASE_COLOR = Color.WHITE;
	
	// All the colors that appear in the right hand side
	// Enums are useful here because they stand in place for Color objects and are more readable
	private static Color[] canvasColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA, Color.PINK, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.BLACK};
	
	private Canvas canvas; // An instance of the Canvas class
	private Pixel[][] canvasGUI; // The pixels on the GUI that represent the cells in the canvas class
	private Color brushColor = Color.WHITE; // The color the pixel will change to when we click it.
	// Member variable that represents when we're erasing or not. While erasing, the brush color should be white and clicking a different canvas color should not change the brush color.
	private boolean isErasing = false; 
	private DrawingGrader grader;
	
	private void colorCell(int row, int col) {
		if (this.isErasing) {
			this.canvas.setCell(row, col, ERASE_COLOR);
		} else {
			this.canvas.setCell(row, col, this.brushColor);
		}
		
	}
	
	private void changeBrushColor(Color color) {
		if (!isErasing) {
			this.brushColor = color;
		}
	}
	
	private void updateCanvasGUI() {
		int width = this.canvas.getWidth();
		int height = this.canvas.getHeight();
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				this.canvasGUI[row][col].setBackground(this.canvas.getCell(row, col));
			}
		}
	}
	
	private void updateCanvasGUICell(int row, int col) {
		this.canvasGUI[row][col].setBackground(this.canvas.getCell(row, col));
	}
	
	
	private void initGUI() {
		JPanel canvasPanel = new JPanel();
		canvasPanel.setLayout(new GridLayout(canvas.getWidth(), canvas.getHeight()));
		canvasPanel.setPreferredSize(new Dimension(650, 650));
		this.add(canvasPanel, BorderLayout.CENTER);
		
		for (int row = 0; row < canvas.getHeight(); row++) {
			for (int col = 0; col < canvas.getWidth(); col++) {
				Pixel newPixel = new Pixel(row, col);
				canvasGUI[row][col] = newPixel;
				canvasPanel.add(newPixel);
				newPixel.setBorderPainted(true);
				newPixel.setFocusPainted(false);
				newPixel.setBackground(Color.WHITE);
				newPixel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int row = newPixel.getRow();
						int col = newPixel.getColumn();
						colorCell(row, col);
						updateCanvasGUICell(row, col);
					}
				});
			}
		}
		
		JPanel titlePanel = new JPanel();
		this.add(titlePanel, BorderLayout.PAGE_START);
		titlePanel.setPreferredSize(new Dimension(800, 50));
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 30);
		JLabel titleText = new JLabel("Paint!");
		titlePanel.add(titleText);
		titleText.setFont(font);
		
		JPanel colorsPanel = new JPanel();
		colorsPanel.setLayout(new GridLayout(10, 3));
		this.add(colorsPanel, BorderLayout.LINE_END);
		colorsPanel.setPreferredSize(new Dimension(150, 500));
		
		for(int i = 0; i < canvasColors.length; i++) {
			Color color = this.canvasColors[i];
			JButton colorButton = new JButton();
			colorButton.setBackground(color);
			colorButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					changeBrushColor(color);
				}
			});
			colorsPanel.add(colorButton);
		}
		
		 
		JPanel toolsPanel = new JPanel();
		this.add(toolsPanel, BorderLayout.PAGE_END);
		toolsPanel.setPreferredSize(new Dimension(800, 100));
		
		JButton paintButton = new JButton("Paint");
		paintButton.setBackground(Color.ORANGE);
		paintButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isErasing = false;
			}
		});
		toolsPanel.add(paintButton);
		
		JButton eraseButton = new JButton("Erase");
		eraseButton.setBackground(Color.PINK);
		eraseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isErasing = true;
			}
		});
		toolsPanel.add(eraseButton);
		
		JButton clearButton = new JButton("Clear");
		clearButton.setBackground(Color.CYAN);
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.clearBoard();
				updateCanvasGUI();
			}
		});
		toolsPanel.add(clearButton);
		
		JButton gradeButton = new JButton("Grade");
		gradeButton.setBackground(Color.GREEN);
		gradeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gradeButton.setText(Float.toString(100 * grader.gradeDrawing(canvas)) + "%");
			}
		});
		
		JButton saveButton = new JButton("Save");
		saveButton.setBackground(Color.BLUE);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grader.setReference(canvas);
				gradeButton.setText("Grade");
			}
		});
		toolsPanel.add(saveButton);
		
		JButton loadButton = new JButton("Load");
		loadButton.setBackground(Color.LIGHT_GRAY);
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas = grader.getReference(); 
				updateCanvasGUI();
			}
		});
		toolsPanel.add(loadButton);
		
		toolsPanel.add(gradeButton);
		
	}
	
	public PaintApp() {
		canvas = new Canvas();
		canvasGUI = new Pixel[canvas.getHeight()][canvas.getWidth()];
		grader = new DrawingGrader();
		
		initGUI();
		this.setBackground(Color.WHITE);
		this.setTitle("Paint!");
		this.setVisible(true);
		this.setResizable(false);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String className = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(className);
		} catch (Exception e) {}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new PaintApp();
			}
		});
	}

}


