package schach;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Schach extends JFrame {

	private static final long serialVersionUID = 640712912075073256L;
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	private static final int HEADER = 100;
	
	public static final int FIELD_SIZE = 8;
	
	private static Team current = Team.WHITE;
	private static Position selected = new Position(-1,-1);
	
	private static List<Figure<? extends FigureModel>> grid = new Vector<Figure<? extends FigureModel>>(); 
	
	public static void main(String[] args) {
		new Schach();
	}
	
	public Schach() {
		super("Schach");
		
		initFrame();
		initGame();
		
		super.setVisible(true);
	}
	
	private void initGame() {
		
		Figures.initFigures();
		
		super.add(new Drawer());
		
		// place figures on board
		for (int n=0;n<FIELD_SIZE;n++) {
			grid.add(new Figure<FigureModel>(Figures.BAUER, new Position(n, 1), Team.WHITE));
			grid.add(new Figure<FigureModel>(Figures.BAUER, new Position(n, 6), Team.BLACK));
		}
	}

	private void initFrame() {
		super.setBounds(0, 0, WIDTH, HEADER+HEIGHT);
		super.setUndecorated(true);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setLocationRelativeTo(null);
	}
	
	private class Drawer extends JPanel {
		
		private static final long serialVersionUID = -6172872886208674146L;
		private final Font DEFAULT_FONT = new Font("Times New Roman", 25, 25);
		private final Color DARK_GRAY = new Color(80,80,80);
		
		private final BasicStroke DEFAULT_STROKE = new BasicStroke(1);
		private final BasicStroke SELECTION_STROKE = new BasicStroke(5);
		
		public Drawer() {
			super.addMouseListener(new Listener());
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			super.repaint();
			
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(DEFAULT_STROKE);
			
			// draw title bar
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, Schach.WIDTH, HEADER);
			g.setColor(Color.BLACK);
			g.setFont(DEFAULT_FONT);
			g.drawString("Schach | Gerade am Zug: "+current.toString(), 50, Schach.HEADER/2+12);
			
			// draw background
			g.setColor(Color.WHITE);
			g.fillRect(0, HEADER, Schach.WIDTH, Schach.HEIGHT);
			
			// draw fields
			g.setColor(DARK_GRAY);
			for (int y = 0;y<FIELD_SIZE;y++) {
				for (int x = 0;x<FIELD_SIZE;x++) {
					if (isADarkField(x,y)) {
						g.fillRect(1+x*(Schach.WIDTH/FIELD_SIZE), HEADER+1+y*(Schach.WIDTH/FIELD_SIZE), Schach.WIDTH/FIELD_SIZE, Schach.WIDTH/FIELD_SIZE);
					}
				}
			}
			
			// draw borders
			g.drawRect(1, HEADER+1, Schach.WIDTH-4, Schach.HEIGHT-4);
			
			// draw figures
			for (Figure<? extends FigureModel> model : grid) {
				
				g.drawImage(model.getModel().getImage(isADarkField(model.getPos().x, model.getPos().y),model.getTeam()), 1+model.getPos().x*(Schach.WIDTH/FIELD_SIZE), HEADER+1+model.getPos().y*(Schach.WIDTH/FIELD_SIZE), (Schach.WIDTH/FIELD_SIZE), (Schach.WIDTH/FIELD_SIZE), null);
				
			}
			
			// check if something is selected
			if (selected.x>=0&&selected.y>=0) {
				
				g2.setStroke(SELECTION_STROKE);
				
				// draw possibilities
				g.setColor(Color.BLUE);
				Figure<? extends FigureModel> target = getSelected();
				for (Position possibility : target.getModel().getAvailablePositionsToMove(grid, target)) {
					g.drawRect(possibility.x*(Schach.WIDTH/FIELD_SIZE), HEADER+possibility.y*(Schach.WIDTH/FIELD_SIZE), (Schach.WIDTH/FIELD_SIZE), (Schach.WIDTH/FIELD_SIZE));
				}
				
				// draw selection
				g.setColor(Color.GREEN);
				g.drawRect(selected.x*(Schach.WIDTH/FIELD_SIZE), HEADER+selected.y*(Schach.WIDTH/FIELD_SIZE), (Schach.WIDTH/FIELD_SIZE), (Schach.WIDTH/FIELD_SIZE));
			}
			
		}

		private boolean isADarkField(int x, int y) {
			return (y*FIELD_SIZE+x+((y%2==0)?1:0))%2==0;
		}
		
	}
	
	private Figure<? extends FigureModel> getSelected() {
		for (Figure<? extends FigureModel> figure : grid) {
			if (figure.getPos().equals(selected)) return figure;
		}
		return null;
	}
	
	private void switchTeam() {
		selected.x = -1;
		selected.y = -1;
		
		if (current==Team.WHITE) current = Team.BLACK;
		else current = Team.WHITE;
	}
	
	private class Listener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent event) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent event) {
			Position pos = new Position(event.getX()/(WIDTH/FIELD_SIZE), (event.getY()-HEADER)/(WIDTH/FIELD_SIZE));
			
			// check if position is a possible move
			Figure<? extends FigureModel> selection = Schach.this.getSelected();
			
			if (selection!=null) {
				List<Position> possibilities = selection.getModel().getAvailablePositionsToMove(grid, selection);
				for (Position possibility : possibilities) {
					if (possibility.equals(pos)) {
						
						for (Figure<? extends FigureModel> figure : grid) {
							selection.getPos().x = possibility.x;
							selection.getPos().y = possibility.y;
						}
						
						switchTeam();
						return;
					}
				}
			}
			
			
			// check if other figure gets selected
			for (Figure<? extends FigureModel> figure : grid) {
				if (pos.equals(figure.getPos())) {
					if (figure.getTeam() == current) {
						selected.x = pos.x;
						selected.y = pos.y;
					}
				}
			}
		}
		
	}
	
}
