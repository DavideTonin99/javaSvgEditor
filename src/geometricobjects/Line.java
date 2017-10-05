package geometricobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Class to represent a line
 * 
 * @author Davide Tonin
 * @version 1.0 2017-09-20
 */
public class Line extends GeometricObject implements Drawable {
	static final Color DEFAULT_COLOR = Color.RED;
	
	private Point p1;
	private Point p2;
	private Color color;
	private boolean complete;
	
	/**
	 * Basic constructor
	 */
	public Line() {
		super("Line");
		this.color = DEFAULT_COLOR;
		this.complete = false;
	}

	/**
	 * Basic constructor
	 * @param p1 the first point
	 */
	public Line(Point p1) {
		this();
		this.p1 = p1;
	}
	
	/**
	 * Complete constructor
	 * @param p1 first point
	 * @param p2 second point
	 */
	public Line(Point p1, Point p2) {
		this();
		this.p1 = p1;
		this.p2 = p2;
	}
	
	/**
	 * Complete constructor
	 * @param p1 first point
	 * @param p2 second point
	 * @param color the color of the line
	 */
	public Line(Point p1, Point p2, Color color) {
		this();
		this.p1 = p1;
		this.p2 = p2;
		this.color = color;
	}

	/**
	 * Complete constructor
	 * @param p1 first point
	 * @param p2 second point
	 * @param color the color of the line
	 * @param complete true if the line is complete
	 */
	public Line(Point p1, Point p2, Color color, boolean complete) {
		this();
		this.p1 = p1;
		this.p2 = p2;
		this.color = color;
		this.complete = complete;
	}
	
	/**
	 * @return the p1
	 */
	public Point getP1() {
		return p1;
	}

	/**
	 * @param p1 the p1 to set
	 */
	public void setP1(Point p1) {
		this.p1 = p1;
	}

	/**
	 * @return the p2
	 */
	public Point getP2() {
		return p2;
	}

	/**
	 * @param p2 the p2 to set
	 */
	public void setP2(Point p2) {
		this.p2 = p2;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
		if (p1 != null) p1.setColor(color);
		if (p2 != null) p2.setColor(color);
	}
	
	/**
	 * @return the complete
	 */
	public boolean isComplete() {
		return complete;
	}

	/**
	 * @param complete the complete to set
	 */
	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	@Override
	public boolean equals(Object object) {
		Line line = (Line)object;
		return this.p1.equals(line.getP1()) && this.p2.equals(line.getP2());
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(color);
		if (p1 != null && p2 != null) {
			g2d.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		} else if (p1 != null) {
			p1.draw(g2d);
		}
	}

	@Override
	public String toString() {
		return "Line {p1=" + p1 + ", p2=" + p2 + "}, color=" + color;
	}	
}
