import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ball
{
	public double x = 50;
	public double y = 50;
	public int r = 20;
	public double spd_x = 2.5;
	public double spd_y = 1.25;
	public boolean colision = false;
	public Clip bounceSound[] = new Clip[3];
	Random rn = new Random();
	ball()
	{
		try
		{
			bounceSound[0] = AudioSystem.getClip();
			bounceSound[1] = AudioSystem.getClip();
			bounceSound[2] = AudioSystem.getClip();
			AudioInputStream sound = AudioSystem.getAudioInputStream(new File("src/bounce.wav"));
			bounceSound[0].open(sound);
			sound = AudioSystem.getAudioInputStream(new File("src/bounce2.wav"));
			bounceSound[1].open(sound);
			sound = AudioSystem.getAudioInputStream(new File("src/bounce3.wav"));
			bounceSound[2].open(sound);
		}
		catch(LineUnavailableException | IOException | UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		}
	}

	
	public void changeHorDir()
	{
		spd_x = -spd_x;
		int num = rn.nextInt(3);
		bounceSound[num].setFramePosition(0);
		bounceSound[num].start();
	}
	public void changeVertDir()
	{
		spd_y = -spd_y;
		int num = rn.nextInt(3);
		bounceSound[num].setFramePosition(0);
		bounceSound[num].start();
	}
}
