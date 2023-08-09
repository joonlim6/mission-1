package db;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkGroup {
	private int groupId;
	private String groupName;
	private int groupOrder;
	private String registerDate;
	private String lastEdit;
}
