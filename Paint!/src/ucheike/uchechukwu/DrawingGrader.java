package ucheike.uchechukwu;
import java.awt.Color;

public class DrawingGrader {
	
	private Canvas reference;
	private boolean hasReference = false;
	
	public void setReference(Canvas reference) {
		this.reference = copyCanvas(reference);
		hasReference = true;
	}
	
	public Canvas getReference() {
		return copyCanvas(this.reference);
	}

	public float gradeDrawing(Canvas submission) {
		assert(this.hasReference == true);
		assert(this.reference.getWidth() == submission.getWidth());
		assert(this.reference.getHeight() == submission.getHeight());
		
		int width = this.reference.getWidth();
		int height = this.reference.getHeight();
		int numPixels = width * height;
		int differenceTolerance = 10;
		
		float totalGrade = 0;
		
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				Color refPixelColor = this.reference.getCell(row, col);
				Color subPixelColor = submission.getCell(row, col);
				
				float redDifference = Math.abs(refPixelColor.getRed() - subPixelColor.getRed());
				float blueDifference = Math.abs(refPixelColor.getBlue() - subPixelColor.getBlue());
				float greenDifference = Math.abs(refPixelColor.getGreen() - subPixelColor.getGreen());

				float pixelScoring = 1 - (redDifference/differenceTolerance) - (blueDifference/differenceTolerance) - (greenDifference/differenceTolerance);
				totalGrade += Math.max(0, pixelScoring);
				
			}
		}
		
		System.out.println(totalGrade / numPixels);
		return totalGrade / numPixels;
	}
	
	private Canvas copyCanvas(Canvas reference) {
		int width = reference.getWidth();
		int height = reference.getHeight();
		Canvas copy = new Canvas(width, height);
		
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				Color referenceColor = reference.getCell(row, col);
				copy.setCell(row, col, referenceColor);
			}
		}
		
		return copy;
	}

}
