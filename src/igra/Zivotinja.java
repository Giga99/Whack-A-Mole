package igra;

import java.awt.Graphics;

public abstract class Zivotinja {
	protected Rupa rupa;
	protected int height, width;

	public Zivotinja(Rupa rupa) {
		super();
		this.rupa = rupa;
	}
	
	public synchronized void crtaj(Graphics g) {
		double proc = (double)rupa.getTrenBrKoraka() / (double)rupa.getUkBrKoraka();
		height = (int)(rupa.getHeight() * proc);
		width = (int)(rupa.getWidth() * proc);
	}
	
	public abstract void efekatUdareneZiv();
	public abstract void efekatPobegleZiv();
}
