package us.liveprayer.mobile.objects;

public class Prayer {
	
	private long id;
	private boolean isMine;
	private String imageId;
	private String title;
	private String text;
	private String startTime;
	private boolean isFinalCountdown;
	private boolean isStarted;
	private int participantCount;
	private int progress;
	private boolean isEnded;
	private boolean isAccepted;
	
	public Prayer() {
		
	}
	
	public Prayer(long id) {
		this.id = id;
	}
	
	public Prayer(long id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public Prayer(long id, boolean isMine, String imageId, String title, String text, String startTime,
			boolean isFinalCountdown, boolean isStarted, int participantCount, int progress, boolean isEnded, boolean isAccepted) {
		
		this.id = id;
		this.isMine = isMine;
		this.imageId = imageId;
		this.title = title;
		this.text = text;
		this.startTime = startTime;
		this.isFinalCountdown = isFinalCountdown;
		this.isStarted = isStarted;
		this.participantCount = participantCount;
		this.progress = progress;
		this.isEnded = isEnded;
		this.isAccepted = isAccepted;
		
	}
	
	public synchronized Prayer setId(long id) {
		this.id = id;
		return this;
	}
	
	public synchronized Prayer setIsMine(boolean isMine) {
		this.isMine = isMine;
		return this;
	}
	
	public synchronized Prayer setImageId(String imageId) {
		this.imageId = imageId;
		return this;
	}
	
	public synchronized Prayer setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public synchronized Prayer setText(String text) {
		this.text = text;
		return this;
	}
	
	public synchronized Prayer setStartTime(String startTime) {
		this.startTime = startTime;
		return this;
	}
	
	public synchronized Prayer setIsFinalCountdown(boolean isFinalCountdown) {
		this.isFinalCountdown = isFinalCountdown;
		return this;
	}
	
	public synchronized Prayer setIsStarted(boolean isStarted) {
		this.isStarted = isStarted;
		return this;
	}
	
	public synchronized Prayer setParticipantCount(int participantCount) {
		this.participantCount = participantCount;
		return this;
	}
	
	public synchronized Prayer setProgress(int progress) {
		this.progress = progress;
		return this;
	}
	
	public synchronized Prayer setIsEnded(boolean isEnded) {
		this.isEnded = isEnded;
		return this;
	}
	
	public synchronized Prayer setIsAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
		return this;
	}
	
	public synchronized Long getId() {
		return this.id;
	}
	
	public synchronized boolean getIsMine() {
		return this.isMine;
	}
	
	public synchronized String getImageId() {
		return this.imageId;
	}
	
	public synchronized String getTitle() {
		return this.title;
	}
	
	public synchronized String getText() {
		return this.text;
	}
	
	public synchronized String getStartTime() {
		return this.startTime;
	}
	
	public synchronized boolean getIsFinalCountdown() {
		return this.isFinalCountdown;
	}
	
	public synchronized boolean getIsStarted() {
		return this.isStarted;
	}
	
	public synchronized int getParticipantCount() {
		return this.participantCount;
	}
	
	public synchronized int getProgress() {
		return this.progress;
	}
	
	public synchronized boolean getIsEnded() {
		return this.isEnded;
	}
	
	public synchronized boolean getIsAccepted() {
		return this.isAccepted;
	}
}