package geometricobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Class to represent a polyline
 * 
 * @author Davide Tonin
 * @version 1.0 2017-09-21
 */
public class Polyline extends GeometricObject implements Drawable {

	private ArrayList<Point> points;
	private Color color;
	private boolean complete;

	/**
	 * Basic constructor
	 */
	public Polyline() {
		super("Polyline");
		this.points = new ArrayList<Point>();
		this.complete = false;
	}

	/**
	 * Complete constructor
	 * 
	 * @param line
	 *            the first point
	 */
	public Polyline(Point point) {
		this();
		this.points.add(point);
	}

	/**
	 * Complete constructor
	 * 
	 * @param line
	 *            the first point
	 * @param complete
	 *            true if the polyline is complete
	 */
	public Polyline(Point point, boolean complete) {
		this();
		this.points.add(point);
	}

	/**
	 * Return points
	 * 
	 * @return points
	 */
	public ArrayList<Point> getPoints() {
		return points;
	}

	/**
	 * Set points
	 */
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	/**
	 * Set the color
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Return the current color
	 * @return color
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Add a point
	 * 
	 * @param p
	 */
	public void addPoint(Point p) {
		this.points.add(p);
	}

	/**
	 * Remove the last element
	 */
	public void removeLast() {
		if (!this.points.isEmpty()) {
			this.points.remove(this.points.size() - 1);
		}
	}

	/**
	 * Empty the points
	 */
	public void clear() {
		this.points.clear();
	}

	/**
	 * @return the complete
	 */
	public boolean isComplete() {
		return complete;
	}

	/**
	 * @param complete
	 *            the complete to set
	 */
	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		for (int i = 0; i < points.size() - 1; i++) {
			Line line = new Line(points.get(i), points.get(i + 1));
			line.setColor(color);
			line.draw(g);
		}

	}

}
