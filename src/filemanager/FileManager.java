package filemanager;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

import geometricobjects.Circle;
import geometricobjects.GeometricObject;
import geometricobjects.Point;
import geometricobjects.Polyline;
import geometricobjects.Line;

/**
 * Class to represent a file manager to handle writing and reading requests
 * 
 * @author Davide Tonin
 * @version 1.0 2017-10-05
 */
public class FileManager {
    
    /**
     * Read the points from a CSV file
     * @throws Exception an exception during the read
     */
    public ArrayList<GeometricObject> readCSV(String filename) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        String row, geometricForm;
        StringTokenizer st;
        Color color;
        ArrayList<GeometricObject> geometricObjects = new ArrayList<>();
        
        try {
            fileReader = new FileReader(filename);
            bufferedReader = new BufferedReader(fileReader);            
        } catch (Exception e) {
            System.err.println("Errore nell'apertura del file");
            System.exit(1);
        }
        
        try {
            // Reads and save a row
            while ((row = bufferedReader.readLine()) != null) {
                st = new StringTokenizer(row, ",");
                geometricForm = st.nextToken();
                color = Color.decode(st.nextToken());
                
                // Reads points from file
                if (geometricForm.equals("point")) {
                    int x = Integer.parseInt(st.nextToken());
                    int y = Integer.parseInt(st.nextToken());	
                    Point point = new Point(x, y);                    ;
                    point.setColor(color);
                    geometricObjects.add(point);
                } else if (geometricForm.equals("circle")) {
                	// Reads Circles from file
                    int x = Integer.parseInt(st.nextToken());
                    int y = Integer.parseInt(st.nextToken());	
                    Point point = new Point(x, y);                    
                	int radius = Integer.parseInt(st.nextToken());
                	Circle c = new Circle(point, radius, color);
                	c.setComplete(true);
                	geometricObjects.add(c);
                } else if (geometricForm.equals("line")) {                	
                	// Reads lines from file
                    int x = Integer.parseInt(st.nextToken());
                    int y = Integer.parseInt(st.nextToken());	
                    Point point = new Point(x, y);                   
                    int x2 = Integer.parseInt(st.nextToken());
                    int y2 = Integer.parseInt(st.nextToken());  
                    Point point2 = new Point(x2, y2);
                    Line line = new Line(point, point2, color, true);
                    line.setColor(color);
                    geometricObjects.add(line);
                } else if (geometricForm.equals("polyline")) {
                	// Reads polylines from file
            		Polyline polyline = new Polyline();
                	while (st.hasMoreElements()) {                	
                		try {
                			int x = Integer.parseInt(st.nextToken());
                    		int y = Integer.parseInt(st.nextToken());
                    		Point point = new Point(x, y, color);
                    		polyline.addPoint(point);
                		} catch (Exception e) {                			
                		}
                	}
                	polyline.setColor(color);
                	polyline.setComplete(true);
                	geometricObjects.add(polyline);
                }            
            }
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file.");
            System.exit(1);
        }

        try {
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Errore nella chiusura del file.");
            System.exit(1);
        }    
        
        return geometricObjects;
    }
    
    /**
     * Write the geometric objects on a CSV file
     * @param filename
    */
	public void writeCSV(String filename, ArrayList<GeometricObject> geometricObjects) {
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        
        try {
            fileWriter = new FileWriter(filename);
            printWriter = new PrintWriter(fileWriter);
        } catch(Exception e) {        	
            System.err.println("Errore nell'apertura del file");
            System.exit(1);
        }
        
        for(int i=0; i<geometricObjects.size(); i++){
            GeometricObject go = geometricObjects.get(i);
            // Writes on file
            if (go instanceof Point) {
            	Point p = (Point)go;
            	// example: point,color,x,y
                printWriter.println("point,#"+Integer.toHexString(p.getColor().getRGB()).substring(2)+","+ p.getX() + "," + p.getY());            	
            } else if (go instanceof Line ) {
            	Line l = (Line)go;
            	// example: line,color,x1,y1,x2,y2
            	printWriter.println("line,#"+Integer.toHexString(l.getColor().getRGB()).substring(2) +","+ l.getP1().getX() + "," + l.getP1().getY()+","+l.getP2().getX() + "," + l.getP2().getY());
            } else if (go instanceof Circle) {
            	Circle c = (Circle)go;
            	// example: circle,color,xCenter,yCenter,radius
            	printWriter.println("circle,#"+Integer.toHexString(c.getColor().getRGB()).substring(2)+","+ c.getCenter().getX()+ "," + c.getCenter().getY()+"," + c.getRadius());
            } else if (go instanceof Polyline) {
            	Polyline pol = (Polyline)go;
            	ArrayList<Point> points = pol.getPoints();
            	
            	if (!points.isEmpty()) {
                	String str = "polyline,";
                	str += "#"+Integer.toHexString(points.get(0).getColor().getRGB()).substring(2)+",";
            		for (int j=0; j<points.size();j++) {
            			Point p = points.get(j);
            			str += p.getX() + "," + p.getY() + ",";
            		}
            		// example: polyline,color,x1,y1,x2,y2,x3,y3,...
            		str = str.substring(0, str.length()-1);
            		printWriter.println(str);
            	}
            }
            printWriter.flush();
        }
        
        try {
            fileWriter.close();
        } catch(Exception e) {
            System.err.println("Errore nella chiusura del file");
            System.exit(1);
        }
    }
	
	/**
	 * Read an xml file
	 * @param filename
	 * @return objects to draw
	 */
	public ArrayList<GeometricObject> readXML(String filename) {
        ArrayList<GeometricObject> geometricObjects = new ArrayList<>();
        
        File fXmlFile = new File(filename);
        // Defines a factory API that enables applications to obtain a parser that produces DOM object trees from XML documents.
    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	try {
    		// Defines the API to obtain DOM Document instances from an XML document.
    		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    		// Parse the content of the given file as an XML document and return a new DOM Document object.
	    	Document doc = dBuilder.parse(fXmlFile);
	
	    	doc.getDocumentElement().normalize();
	
	    	NodeList points = doc.getElementsByTagName("point");
	
			for (int i = 0; i < points.getLength(); i++) {
	    		Node pointNode = points.item(i);
	    		if (pointNode.getNodeType() == Node.ELEMENT_NODE) {
	
	    			Element pointElement = (Element) pointNode;
	    			int x = Integer.parseInt(pointElement.getElementsByTagName("x").item(0).getTextContent());
	    			int y = Integer.parseInt(pointElement.getElementsByTagName("y").item(0).getTextContent());
	    			Color color = Color.decode(pointElement.getElementsByTagName("color").item(0).getTextContent());
	    			Point p = new Point(x, y, color);
	    			
	    			geometricObjects.add(p);
	    		}
	    	}
			
			NodeList lines = doc.getElementsByTagName("line");
			
			for (int i = 0; i < lines.getLength(); i++) {
	    		Node lineNode = lines.item(i);
	    		if (lineNode.getNodeType() == Node.ELEMENT_NODE) {
	
	    			Element lineElement = (Element) lineNode;
	    			int x1 = Integer.parseInt(lineElement.getElementsByTagName("x1").item(0).getTextContent());
	    			int y1 = Integer.parseInt(lineElement.getElementsByTagName("y1").item(0).getTextContent());
	    			int x2 = Integer.parseInt(lineElement.getElementsByTagName("x2").item(0).getTextContent());
	    			int y2 = Integer.parseInt(lineElement.getElementsByTagName("y2").item(0).getTextContent());

	    			Color color = Color.decode(lineElement.getElementsByTagName("color").item(0).getTextContent());
	    			Line l = new Line(new Point(x1, y1, color), new Point(x2, y2, color), color, true);
	    				    			
	    			geometricObjects.add(l);
	    		}
	    	}
			
			NodeList circles = doc.getElementsByTagName("circle");
			
			for (int i = 0; i < circles.getLength(); i++) {
	    		Node circleNode = circles.item(i);
	    		if (circleNode.getNodeType() == Node.ELEMENT_NODE) {
	
	    			Element circleElement = (Element) circleNode;
	    			int xCenter = Integer.parseInt(circleElement.getElementsByTagName("x").item(0).getTextContent());
	    			int yCenter = Integer.parseInt(circleElement.getElementsByTagName("y").item(0).getTextContent());
	    			int radius = Integer.parseInt(circleElement.getElementsByTagName("radius").item(0).getTextContent());
	    			Color color = Color.decode(circleElement.getElementsByTagName("color").item(0).getTextContent());
	    			Circle c = new Circle(new Point(xCenter, yCenter), radius, color, true);
	    			
	    			geometricObjects.add(c);
	    		}
	    	}
			
			NodeList polylines = doc.getElementsByTagName("polyline");
			
			for (int i = 0; i < polylines.getLength(); i++) {
	    		Node polylineNode = polylines.item(i);
	    		if (polylineNode.getNodeType() == Node.ELEMENT_NODE) {
	
	    			Element polylineElement = (Element) polylineNode;
	    			
	    			int nPoints = polylineElement.getElementsByTagName("x").getLength(); 
	    			Polyline pol = new Polyline();
	    			Color color = Color.decode(polylineElement.getElementsByTagName("color").item(0).getTextContent());
	    			
	    			for (int j=0; j<nPoints; j++) {
		    			int x = Integer.parseInt(polylineElement.getElementsByTagName("x").item(j).getTextContent());
		    			int y = Integer.parseInt(polylineElement.getElementsByTagName("y").item(j).getTextContent());	    				
		    			pol.addPoint(new Point(x, y, color));
	    			}
	    			pol.setColor(color);
	    			pol.setComplete(true);
	    			
	    			geometricObjects.add(pol);
	    		}
	    	}
		} catch(Exception e) {
			System.err.println("Errore di apertura del file");
		}
        return geometricObjects;
	}
	
	public void writeXML(String filename, ArrayList<GeometricObject> geometricObjects) {
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("geometricobjects");
			doc.appendChild(rootElement);

			// point elements
			for (GeometricObject go: geometricObjects) {
				if (go instanceof Point) {
					Point point = (Point)go;
					
	 				Element pointElement = doc.createElement("point");
					rootElement.appendChild(pointElement);
					
					Element xCoordinate = doc.createElement("x");
					Element yCoordinate = doc.createElement("y");
					Element color = doc.createElement("color");
					xCoordinate.appendChild(doc.createTextNode(String.valueOf(point.getX())));
					yCoordinate.appendChild(doc.createTextNode(String.valueOf(point.getY())));
					color.appendChild(doc.createTextNode("#"+Integer.toHexString(point.getColor().getRGB()).substring(2)));

					pointElement.appendChild(xCoordinate);
					pointElement.appendChild(yCoordinate);
					pointElement.appendChild(color);
					
				} else if (go instanceof Line) {
					Line line = (Line)go;
					
	 				Element lineElement = doc.createElement("line");
					rootElement.appendChild(lineElement);
					
					Element x1 = doc.createElement("x1");
					Element y1 = doc.createElement("y1");
					Element x2 = doc.createElement("x2");
					Element y2 = doc.createElement("y2");
					Element color = doc.createElement("color");

					x1.appendChild(doc.createTextNode(String.valueOf(line.getP1().getX())));
					y1.appendChild(doc.createTextNode(String.valueOf(line.getP1().getY())));
					x2.appendChild(doc.createTextNode(String.valueOf(line.getP2().getX())));
					y2.appendChild(doc.createTextNode(String.valueOf(line.getP2().getY())));
					color.appendChild(doc.createTextNode("#"+Integer.toHexString(line.getColor().getRGB()).substring(2)));

					
					lineElement.appendChild(x1);
					lineElement.appendChild(y1);
					lineElement.appendChild(x2);
					lineElement.appendChild(y2);	
					lineElement.appendChild(color);	

				}  else if (go instanceof Circle) {
					Circle circle = (Circle)go;
					
	 				Element circleElement = doc.createElement("circle");
					rootElement.appendChild(circleElement);
					
					Element xCenter = doc.createElement("x");
					Element yCenter = doc.createElement("y");
					Element radius = doc.createElement("radius");
					Element color = doc.createElement("color");

					xCenter.appendChild(doc.createTextNode(String.valueOf(circle.getCenter().getX())));
					yCenter.appendChild(doc.createTextNode(String.valueOf(circle.getCenter().getY())));
					radius.appendChild(doc.createTextNode(String.valueOf(circle.getRadius())));
					color.appendChild(doc.createTextNode("#"+Integer.toHexString(circle.getColor().getRGB()).substring(2)));
					
					circleElement.appendChild(xCenter);
					circleElement.appendChild(yCenter);
					circleElement.appendChild(radius);
					circleElement.appendChild(color);	

				} else if (go instanceof Polyline) {
					Polyline polyline = (Polyline)go;
					
	 				Element polylineElement = doc.createElement("polyline");
					rootElement.appendChild(polylineElement);
					
					Element x;
					Element y;
					Element color = doc.createElement("color");
					color.appendChild(doc.createTextNode("#"+Integer.toHexString(polyline.getColor().getRGB()).substring(2)));					
					polylineElement.appendChild(color);
					
					for (Point p: polyline.getPoints()) {
						x = doc.createElement("x");
						y = doc.createElement("y");
						
						x.appendChild(doc.createTextNode(String.valueOf(p.getX())));
						y.appendChild(doc.createTextNode(String.valueOf(p.getY())));
						
						polylineElement.appendChild(x);
						polylineElement.appendChild(y);
					}										
				}
			}		

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filename));

			transformer.transform(source, result);			

		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
	}
}
