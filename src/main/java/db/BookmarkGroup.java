package db;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkGroup {
	private int groupId;
	private String groupName;
	private int order;
	private String registerDate;
	private String lastEdit;
}
