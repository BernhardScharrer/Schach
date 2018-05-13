package schach;

import java.util.ArrayList;
import java.util.List;

public class BauerModel extends FigureModel {

	public BauerModel() {
		super("bauer");
	}

	@Override
	public boolean isPerformingSchach() {
		return false;
	}

	@Override
	public List<Position> getAvailablePositionsToMove(List<Figure<? extends FigureModel>> grid, Figure<? extends FigureModel> own) {
		
		List<Position> positions = new ArrayList<>();
		
		if (own.getTeam() == Team.WHITE) {
			for (int n = 1;n<=(own.getPos().y==1?2:1);n++) {
				positions.add(new Position(own.getPos().x, own.getPos().y+n));
			}
		} else {
			for (int n = 1;n<=(own.getPos().y==6?2:1);n++) {
				positions.add(new Position(own.getPos().x, own.getPos().y-n));
			}
		}
		
		return positions;
	}

}
