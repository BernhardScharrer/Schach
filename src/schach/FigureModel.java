package schach;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public abstract class FigureModel {
	
	private BufferedImage image1,image2,image3,image4;
	
	public FigureModel(String texture) {
		try {
			this.image1 = ImageIO.read(new File("res/"+texture+"1.png"));
			this.image2 = ImageIO.read(new File("res/"+texture+"2.png"));
			this.image3 = ImageIO.read(new File("res/"+texture+"3.png"));
			this.image4 = ImageIO.read(new File("res/"+texture+"4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public abstract boolean isPerformingSchach();
	public abstract List<Position> getAvailablePositionsToMove(List<Figure<? extends FigureModel>> grid, Figure<? extends FigureModel> own);

	public BufferedImage getImage(boolean dark, Team team) {
		return team==Team.WHITE?(dark ? image2 : image1):(dark ? image4 : image3);
	}
	
	protected boolean isFree(Position pos, List<Figure<? extends FigureModel>> grid) {
		
		for (Figure<? extends FigureModel> figure : grid) {
			if (figure.getPos().equals(pos)) {
				return true;
			}
		}
		return false;
		
	}
	
}
