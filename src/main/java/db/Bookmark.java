package db;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bookmark {
	private int bookmarkId;
	private int groupId;
	private String mainNumber;
	private String registerDate;
}
