package service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import dal.PropertyFacade;
import entities.User;

@Singleton
public class TimerSession {
	@Resource
	TimerService timerService;

	private Date lastProgrammaticTimeout;
	private Date lastAutomaticTimeout;
	
	@EJB
	EmailBean eb;
	
	@EJB
	OperatorBean ob;
	
	@EJB
	private PropertyFacade propertyFacade;
	
	private String body;

	public void setTimer(long intervalDuration) {
		Timer timer = timerService.createTimer(intervalDuration, "Created new programmatic timer");
	}

	@Timeout
	public void programmaticTimeout(Timer timer) {
		this.setLastProgrammaticTimeout(new Date());
	}

	@Schedule(minute = "*", hour = "*/23")
	public void automaticTimeout() {
		this.setLastAutomaticTimeout(new Date());
		
		List<User> listUsers = ob.listUsers();
		
		listUsers.stream()
		.filter(u -> u.getProperties() != null && u.getProperties().size() > 0)
		.forEach(u -> {
			body = "A mai napon ennyien tekintették meg a hirdetéseid: \n";
			u.getProperties().forEach(p -> {
				body += p.getAddress() + ": " + p.getNumberOfViews() + " megtekintés \n";
				p.setNumberOfViews(0);
				propertyFacade.edit(p);
			});
			body += "/n Üdvözlettel, /n Ingatlan Keresõ";
			eb.sendEmail(u.getEmail(), "Ingatlan Keresõ", body);
			}
		);
	}

	public String getLastProgrammaticTimeout() {
		if (lastProgrammaticTimeout != null) {
			return lastProgrammaticTimeout.toString();
		} else {
			return "never";
		}
	}

	public void setLastProgrammaticTimeout(Date lastTimeout) {
		this.lastProgrammaticTimeout = lastTimeout;
	}

	public String getLastAutomaticTimeout() {
		if (lastAutomaticTimeout != null) {
			return lastAutomaticTimeout.toString();
		} else {
			return "never";
		}
	}

	public void setLastAutomaticTimeout(Date lastAutomaticTimeout) {
		this.lastAutomaticTimeout = lastAutomaticTimeout;
	}
}
