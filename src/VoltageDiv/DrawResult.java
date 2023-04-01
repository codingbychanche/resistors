package VoltageDiv;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class DrawResult {

	
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 800;
	
	private static final int BORDER_WIDTH=10;
	
	
	public static void draw(DividerResult result) {

		// Create an in memory Image
		// The whole canvas in which the rendered image will be placed...
		BufferedImage canvas = new BufferedImage(WIDTH+2*BORDER_WIDTH, HEIGHT+2*BORDER_WIDTH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D c=canvas.createGraphics();
		
		// Contains the rendered image of the soution....
		BufferedImage gfxArea=new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		c=assignRendieringHints(c);
		Graphics2D graphics = gfxArea.createGraphics();
		graphics = assignRendieringHints(graphics);
		
		
		
		
		//
		// Render the solution
		//
		graphics.setBackground(Color.WHITE);

		Stroke stroke = new BasicStroke(1f);
		graphics.setStroke(stroke);
		graphics.setColor(Color.BLACK);

		graphics.drawString("Anticipated Vout="+result.getVOutAnticipated(), 0, 10);
		graphics.drawString("Nominal Vout="+result.getVoutNominal(), 0, 20);
		
		graphics.drawRect(10,10,200,200);
		

		
		//
		// Place picture of solution on canvas
		//
		c.drawImage(gfxArea, new AffineTransform(1f,0f,0f,1f,BORDER_WIDTH,BORDER_WIDTH),null);
		
		//
		// Save to file.
		//
		try {
			File outputfile = new File("result.png");
			ImageIO.write(canvas, "png", outputfile);
		} catch (Exception e) {
		}
	}

	/**
	 * Transforms a y- coordinate.
	 * 
	 * @param y To be transformed.
	 * @return Transformed y- coordinate.
	 */
	private static double getYT(double y) {
		return y;
	}

	/**
	 * Transforms a x- coordinate.
	 * 
	 * @param x To be transformed
	 * @return Transformed x- coordinate.
	 */
	private static double getXT(double x,double vMax,double vMin) {
		return (WIDTH/(vMax-vMin))*x-(WIDTH/(vMax-vMin)*vMin);
	}

	/**
	 * Display width of a string in pixels.
	 * 
	 * @param string
	 * @param g      Associated {@link Graphics} object.
	 * @return Width of string in pixels.
	 */
	private int getWidth(String string, Graphics g) {
		FontMetrics f = g.getFontMetrics();
		return f.stringWidth(string);
	}

	/**
	 * Calculates the x position of the upper left corner for a string which centers
	 * it relative to a given coordinate.
	 * 
	 * @param string The string.
	 * @param x      Coordinate relative to which the given string should be
	 *               centered
	 * @param g      Associated {@ling Graphics} object.
	 * @return The upper left coordinate of the centered string.
	 */
	private int getHorizCenterCoordinate(String string, int x, Graphics g) {
		int widthOfString = getWidth(string, g);
		return x - widthOfString / 2;
	}

	private int getVertCenterCoordinate(int heightOfString, int y) {
		return y + heightOfString / 2;
	}
	
	/**
	 * Adds rendering hints for the graphics display.
	 * 
	 * @param graphics The {@Graphics2D}- object to which the rendering settings are
	 *                 to be added.
	 * @return The {@Graphics2D}- object containing the new rendering settings.
	 */
	private static Graphics2D assignRendieringHints(Graphics2D graphics) {
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		graphics.setRenderingHints(rh);

		return graphics;
	}
}
