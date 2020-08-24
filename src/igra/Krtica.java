package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Krtica extends Zivotinja {
	
	public Krtica(Rupa rupa) {
		super(rupa);
	}
	
	@Override
	public synchronized void crtaj(Graphics g) {
		super.crtaj(g);
		g.setColor(Color.DARK_GRAY);
		g.fillOval(rupa.getWidth() / 2 - width / 2, rupa.getHeight() / 2 - height / 2, width, height);
	}

	@Override
	public synchronized void efekatUdareneZiv() {
		rupa.setUdarena(true);
		rupa.zaustavi();
	}

	@Override
	public synchronized void efekatPobegleZiv() {
		rupa.getBasta().smanjiPovrce();
	}

}
