package com.maligai.legoframe;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
 
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.maligai.legoframe.filter.ImageFilter;

@SpringBootApplication
public class LegoframeApplication implements CommandLineRunner{

	public static void main(String[] args) {
		new SpringApplicationBuilder(LegoframeApplication.class)
                .web(WebApplicationType.NONE)
                .headless(false)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(args.length>0){
			System.out.println("opening file : " + args[0]);
			File file = new File(args[0]);
			BufferedImage img = null;
			try{ img = ImageIO.read(file); } 
			catch (IOException e) { e.printStackTrace(System.out); }
			if (img != null) {
				System.out.println("height: " + img.getHeight() + " width: " + img.getWidth());
				img = ImageFilter.resize(img, 50);
				display(img);
				img = ImageFilter.legozised(img);
				display(img);
			}
		}
	}


	public static void display (BufferedImage img) {
		System.out.println("  Displaying image.");
		JFrame frame = new JFrame();
	    JLabel label = new JLabel();
		frame.setSize(img.getWidth(), img.getHeight());
		label.setIcon(new ImageIcon(img));
		frame.getContentPane().add(label, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}
