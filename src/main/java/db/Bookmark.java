package db;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bookmark {
	private int bookmarkId;
	private int groupName;
	private String mainNumber;
	private String registerDate;
}
