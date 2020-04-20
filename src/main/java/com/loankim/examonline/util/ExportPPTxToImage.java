package com.loankim.examonline.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

/**
 * @author LamHM
 *
 */
public class ExportPPTxToImage {
	private static final Dimension pgsize = new Dimension(850, 540);


	public static void convertPPTxToImage(String fileName) throws IOException {
		// Tao thu muc theo ngay
		File directory = new File("export/123");
		if (!directory.exists())
			directory.mkdir();

		FileInputStream file = new FileInputStream("resources/" + fileName);
		try (XMLSlideShow ppt = new XMLSlideShow(file)) {
			// getting the dimensions and size of the slide
			System.out.println(pgsize.getWidth() + "/" + pgsize.getHeight());
			List<XSLFSlide> slideList = ppt.getSlides();

			for (int i = 0; i < slideList.size(); i++) {
				BufferedImage img = new BufferedImage(pgsize.width, pgsize.height,BufferedImage.TYPE_INT_RGB);
		          Graphics2D graphics = img.createGraphics();

		          
		          graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		          graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		          graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		          graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		          //clear the drawing area
		          graphics.setColor(Color.white);
		          graphics.clearRect(0, 0, pgsize.width, pgsize.height);
		          graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

				// render
				XSLFSlide xslfSlide = slideList.get(i);
				xslfSlide.draw(graphics);
				String title = xslfSlide.getTitle();
				System.out.println(title);

				// creating an image file as output
				FileOutputStream out = new FileOutputStream("images/ppt_image_" + i + ".png");
				javax.imageio.ImageIO.write(img, "png", out);
				System.out.println("Image successfully created");
				out.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	public static void convertPPTxToImage1(String fileName) throws IOException {
		// Tao thu muc theo ngay
		File directory = new File("export/123");
		if (!directory.exists())
			directory.mkdir();

		FileInputStream file = new FileInputStream("resources/" + fileName);
		try (XMLSlideShow ppt = new XMLSlideShow(file)) {
			// getting the dimensions and size of the slide
			System.out.println(pgsize.getWidth() + "/" + pgsize.getHeight());
			List<XSLFSlide> slideList = ppt.getSlides();

			for (int i = 0; i < slideList.size(); i++) {
				BufferedImage img = new BufferedImage(pgsize.width, pgsize.height,BufferedImage.TYPE_INT_RGB);
		          Graphics2D graphics = img.createGraphics();

		          
		          graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
					graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
					graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
							RenderingHints.VALUE_FRACTIONALMETRICS_ON);

					graphics.setColor(Color.white);
					graphics.clearRect(0, 0, pgsize.width, pgsize.height);
					graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

				// render
				XSLFSlide xslfSlide = slideList.get(i);
				xslfSlide.draw(graphics);
				String title = xslfSlide.getTitle();
				System.out.println(title);

				// creating an image file as output
				FileOutputStream out = new FileOutputStream("images/ppt_image_" + i + ".png");
				javax.imageio.ImageIO.write(img, "png", out);
				System.out.println("Image successfully created");
				out.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}


	public static void convertPPTxToImage(InputStream file) throws IOException {
		// Tao thu muc theo ngay
		try (XMLSlideShow ppt = new XMLSlideShow(file)) {
			// getting the dimensions and size of the slide
			Dimension pgsize = ppt.getPageSize();
			List<XSLFSlide> slideList = ppt.getSlides();

			for (int i = 0; i < slideList.size(); i++) {
				BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, 1);

				Graphics2D graphics = img.createGraphics();
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
						RenderingHints.VALUE_FRACTIONALMETRICS_ON);

				graphics.setColor(Color.white);
				graphics.clearRect(0, 0, pgsize.width, pgsize.height);
				graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
				// render
				slideList.get(i).draw(graphics);
				String title = slideList.get(i).getTitle();
				System.out.println(title);

				// creating an image file as output
				FileOutputStream out = new FileOutputStream("export/123/ppt_image_" + i + ".png");
				javax.imageio.ImageIO.write(img, "png", out);
				System.out.println("Image successfully created");
				out.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}


	public static String convertSlideToImage(XSLFSlide slide, String folderName, String fileName) {
		BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, 1);
		Graphics2D graphics = img.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

		graphics.setColor(Color.white);
		graphics.clearRect(0, 0, pgsize.width, pgsize.height);
		graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
		// render
		slide.draw(graphics);

		// creating an image file as output
		String url = folderName + "/" + fileName + ".png";
		try (FileOutputStream out = new FileOutputStream(url)) {
			ImageIO.write(img, "png", out);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return url;
	}


	public static void main(String[] args) throws IOException {
		convertPPTxToImage1("Suggest.pptx");
		
//		Presentation presentation = new Presentation("resources/" + "Olbaby.pptx");
//		// access the first slide from the collection
//		ISlide slide = presentation.getSlides().get_Item(0);
//		// create a full scale image of the slide
//		BufferedImage image = slide.getThumbnail(1f, 1f);
//		// save the image in JPEG format
//		ImageIO.write(image, "jpeg", new File("resources/" + "output.jpg"));
	}
}
