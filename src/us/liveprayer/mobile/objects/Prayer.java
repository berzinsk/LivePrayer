package us.liveprayer.mobile.objects;

public class Prayer {
	
	long id;
	String text;
	
	public Prayer() {
		
	}
	
	public Prayer(long id) {
		this.id = id;
	}
	
	public Prayer(long id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public synchronized Prayer setId(long id) {
		this.id = id;
		return this;
	}
	
	public synchronized Prayer setText(String text) {
		this.text = text;
		return this;
	}
	
	public synchronized Long getId() {
		return this.id;
	}
	
	public synchronized String getText() {
		return this.text;
	}
}