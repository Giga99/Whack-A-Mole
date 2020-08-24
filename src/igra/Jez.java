package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Jez extends Zivotinja {
	
	public Jez(Rupa rupa) {
		super(rupa);
	}
	
	@Override
	public synchronized void crtaj(Graphics g) {
		super.crtaj(g);
		g.setColor(Color.RED);
		g.fillOval(rupa.getWidth() / 2 - width / 2, rupa.getHeight() / 2 - height / 2, width, height);
	}

	@Override
	public void efekatUdareneZiv() {
		rupa.getBasta().smanjiPovrce();
		rupa.getBasta().smanjiPovrce();
		rupa.getBasta().smanjiPovrce();
		rupa.zaustavi();
	}

	@Override
	public void efekatPobegleZiv() {
		// TODO Auto-generated method stub

	}

}
