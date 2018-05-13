package schach;

public class Figure<MODEL extends FigureModel> {

	private MODEL model;
	private Position pos;
	private Team team;
	
	public Figure(MODEL model, Position pos, Team team) {
		this.model = model;
		this.pos = pos;
		this.team = team;
	}

	public MODEL getModel() {
		return model;
	}

	public Position getPos() {
		return pos;
	}
	
	public Team getTeam() {
		return team;
	}
	
}
