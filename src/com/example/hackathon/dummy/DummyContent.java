//package com.example.hackathon.dummy;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.example.hackathon.models.ListUsers;

///**
// * Helper class for providing sample content for user interfaces created by
// * Android template wizards.
// * <p>
// * TODO: Replace all uses of this class before publishing your app.
// *///
//public class DummyContent {
//
//	/**
//	 * An array of sample (dummy) items.
//	 *///
//	public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();
//
//	/**
//	 * A map of sample (dummy) items, by ID.
//	 */
//	public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();
//
//	static {
//		// Add 3 sample items.
//		addItem(new DummyItem("1", "Item 1"));
//		addItem(new DummyItem("2", "Item 2"));
//		addItem(new DummyItem("3", "Item 3"));
//	}
//
//	private static void addItem(DummyItem item) {
//		ITEMS.add(item);
//		ITEM_MAP.put(item.id, item);
//	}
//
//	/**
//	 * A dummy item representing a piece of content.
//	 */
//	public static class DummyItem {
//		public String id;
//		public String content;
//
//		public DummyItem(String id, String content) {
//			this.id = id;
//			this.content = content;
//		}
//
//		@Override
//		public String toString() {
//			return content;
//		}
//	}
//	public static ArrayList<ListUsers> USERS = new ArrayList<ListUsers>();
//	public static ArrayList<Event> EVENTS = new ArrayList<Event>();
//	
//	static {
//		USERS.add(new ListUsers(12345, "Matthew Looi", "Glenvista JHB" ,new String[] {"Parkour", "Squash", "Ruby on Rails"},new String[] {"Android", "Mandarin", "Baking"},"http://profile.ak.fbcdn.net/hprofile-ak-ash4/c76.491.462.462/s160x160/1006183_10151439849650925_172684630_n.jpg", true, new String[] {"Passionate", "iNspiration", "Parkour", "Live Life", "Simon Sinek my hero", "Dreamer", "Like Always"}));
//		USERS.add(new ListUsers(23456, "James Allingham", "Linden, JHB", new String[] {"Electronics", "Guitar", "Java"},new String[] {"Android", "Engineering", "Guitar"},"http://aiti.mit.edu/media/cache/94/4b/944b049123db55195de6c5e3d87f2ae9.jpg", true, new String[] {"Passionate", "iNspiration", "Guitar Acoustic", "Live Life", "Simon Sinek my hero", "Dreamer", "Like Always"}));
//		USERS.add(new ListUsers(34567, "Merelda Wu", "Edenvale JHB", new String[] {"Fashion", "Android", "Baking"},new String[] {"Parkour", "Skydiving", "Squash"},"http://profile.ak.fbcdn.net/hprofile-ak-ash3/c50.194.608.608/s160x160/522558_10151112223026330_1472903822_n.jpg", false, new String[] {"Chocolate Cookies", "Happiness Amplifier", "Baking Cakes", "Cappuchino Grande", "Cheese Cake Factory", "Dream big + Far", "Like Always"}));
//		
//		USERS.add(new ListUsers(45678, "Jackline Atsango", "Northcliff, JHB", new String[] {"Yoga", "Running", "Meditation"},new String[] {"German", "Zulu", "French"},"http://sphotos-a.ak.fbcdn.net/hphotos-ak-ash4/2175_63289166659_1264_n.jpg", false, new String[] {"Chocolate Cookies", "Happiness Amplifier", "Yoga Living", "Cappuchino Grande", "Cheese Cake Factory", "Dream big + Far", "Like Always"}));
//		USERS.add(new ListUsers(56789, "Zaahirah Bhumajeeeeeee", "Kylami, JHB", new String[] {"Fashion", "Makeup", "Blogging"},new String[] {"CSS", "HTML5", "Web design"},"http://profile.ak.fbcdn.net/hprofile-ak-frc3/c44.44.552.552/s160x160/1011307_10152961840670082_1660923073_n.jpg", false, new String[] {"Chocolate Cookies", "Happiness Amplifier", "Painting Portraits", "Cappuchino Grande", "Cheese Cake Factory", "Dream big + Far", "Like Always"}));
//		USERS.add(new ListUsers(13579, "Robert Louw", "Linden, JHB", new String[] {"Parkour", "Guitar", "Swimming"},new String[] {"Parkour", "Drums", "Baking"},"http://profile.ak.fbcdn.net/hprofile-ak-ash4/c36.36.447.447/s160x160/1004596_599462753418308_108864779_n.jpg", true, new String[] {"Passionate", "iNspiration", "Baking Cookies", "Live Life", "Simon Sinek my hero", "Dreamer", "Like Always"}));
//		USERS.add(new ListUsers(24680, "Ronald Clark", "Sandton, JHB", new String[] {"Tennis", "Photography", "Engineering"},new String[] {"German", "Animation", "Filming"},"http://profile.ak.fbcdn.net/hprofile-ak-ash4/s160x160/1004448_10201423936938317_650525523_a.jpg", true, new String[] {"Passionate", "iNspiration", "Tennis is Fun", "Live Life", "Simon Sinek my hero", "Dreamer", "Like Always"}));
//		USERS.add(new ListUsers(12589, "Matt McAfee", "---", new String[] {"Bandpipes", "Banjo", "Gymnastics"},new String[] {"Tricking", "Dancing", "Balancing"},"http://profile.ak.fbcdn.net/hprofile-ak-prn2/c83.30.371.371/s160x160/168257_10151485256129818_1098145458_n.jpg", true, new String[] {"Passionate", "iNspiration", "Banjo Player", "Live Life", "Simon Sinek my hero", "Dreamer", "Like Always"}));
//	
//		EVENTS.add(new Event("Android Dev Hackathon","06/07/13","12h00","WITS","This is going to be a fun weekend","hack"));
//		EVENTS.add(new Event("Eating Cake","07/07/13","12h00","WITS","Mereleda Loves Cheesecake!","cake"));
//		EVENTS.add(new Event("Resting","08/07/13","06h00","Home","Finally Some Rest","rest"));
//	}
//
////	public static class User{
////		public String name;
////		public String[] skillsLearn;
////		public String[] skillsOffer;
////		public String picture;
////
////		public User(String name, String[] skillsOffer, String[] skillsLearn, String picture)
////		{
////			this.name = name;
////			this.skillsLearn = skillsLearn;
////			this.skillsOffer = skillsOffer;
////			this.picture = picture;
////		}
////		
////		public String skillsOfferToString()
////		{
////			String result = "";
////			
////			for (String s : skillsOffer)
////			{
////				result = result + '#' + s;
////			}
////			return result;
////		}
////		
////	}
//	
//	public static class Event{
//		public String name;
//		public String date;
//		public String time;
//		public String location;
//		public String description;
//		public String picture;
//		
//		public Event(String name, String date, String time, String location, String description,String picture)
//		{
//			this.name = name;
//			this.date = date;
//			this.time = time;
//			this.location = location;
//			this.description = description;
//			this.picture = picture;
//		}
//	}
//}
