import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.SwingWorker;

public class paddle implements KeyListener 
{
	public int score = 0;
	public int x = 200;
	public int y = 200;
	public int width = 20;
	public int height = 80;
	public boolean up = false;
	public boolean down = false;
	public int spd = 5;
	public int limit_h = 0;
	public int limit_v = 0;
	int up_key = 0;
	int down_key = 0;
	int type = 0;
	public paddle(int x, int y, int width, int height, int type)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		if (type == 0)
		{
			up_key = KeyEvent.VK_W;
			down_key = KeyEvent.VK_S;
		}
		else if (type == 1)
		{
			up_key = KeyEvent.VK_UP;
			down_key = KeyEvent.VK_DOWN;
		}
		this.type = type;
	}
	public void moveUp()
	{
		if (y - spd >= 0)
		{
			this.y -= spd;
		}
	}
	public void moveDown()
	{
		if (y + height + spd <= limit_v)
		this.y += spd;
	}
	public void prepareThread()
	{
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void> ()
		{
			@Override
			protected Void doInBackground ()
			{
				while (true)
				{
					if (up)
					{
						moveUp();
					}
					if (down)
					{
						moveDown();
					}

					try
					{
						Thread.sleep(10);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		};
		worker.execute();
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}
	@Override
	public void keyPressed(KeyEvent e)
	{
		int keycode = e.getKeyCode();
		if (keycode == up_key)
		{
			up = true;
		}
		if (keycode == down_key)
		{
			down = true;
		}
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
		int keycode = e.getKeyCode();
		if (keycode == up_key)
		{
			up = false;
		}
		if (keycode == down_key)
		{
			down = false;
		}
	}
}
