package db;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class History {
	private int id;
	private double latitude;
	private double longitude;
	private String viewDate;
}
