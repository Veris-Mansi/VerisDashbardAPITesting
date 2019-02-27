package Files;

public class PayLoadData {

		public static String AddWorkflow(String form_type,String title)
		{
			String s="{\r\n" + 
					"	\"form_type\":"+"\""+form_type+"\",\r\n" + 
					"\"meta\":	{},\r\n" + 
					"\"screens\":	[],\r\n" + 
					"\"status\":	1,\r\n" + 
					"\"title\"	:"+"\""+title+"\"\r\n" + 
					"}";
			//System.out.println(s);
			return s;
	}
		public static String addCustomScreens()
		{
			String s="{\r\n" + 
					"              \"title\": \"My screen\",\r\n" + 
					"			   \"form_type\":\"My screen\",\r\n" + 
					"              \"form_fields\": [\r\n" + 
					"                \r\n" + 
					"              ]\r\n" + 
					"            }";
			return s;
		}
		public static String addScreens()
		{
			String s="{\r\n" + 
					"   \r\n" + 
					"    \"screens\": [\r\n" + 
					"        {\r\n" + 
					"        \"screen\": 32,\r\n" + 
					"        \"position\": 1\r\n" + 
					"        }\r\n" + 
					"    ]\r\n" + 
					"}";
			//System.out.println(s);
			return s;
		}
		
		public static String mappingWorkflow(int fid,int venue_id)
		{
			String form_id=String.valueOf(fid);
			String s="{\r\n" + 
					"    \"object_id\":"+venue_id+",\r\n" + 
					"    \"object_type\":\"venue\",\r\n" + 
					"    \"form_id\": "+form_id+"\r\n" + 
					"}";
			//System.out.println(s);
		return s;
		}
		public  static String editNewScreen()
		{
			String s="{\r\n" + 
					"	\"deleted_form_fields\":	[],\r\n" + 
					"\"form_fields\":	[\r\n" + 
					"{\r\n" + 
					"\"choices\"	:[],\r\n" + 
					"\"field_code\":\"first_name\",\r\n" + 
					"\"field_type\":	1,\r\n" + 
					"\"is_autofill\":	false,\r\n" + 
					"\"is_printable\":	false,\r\n" + 
					"\"label\":\"name\",\r\n" + 
					"\"meta\":	{},\r\n" + 
					"\"order\":1,\r\n" + 
					"\"placeholder_text\":\"\",	\r\n" + 
					"\"required\"	:false\r\n" + 
					"}\r\n" + 
					"],\r\n" + 
					"\"form_type\":\"My screen\",\r\n" + 
					"\"title\":\"My screen\"\r\n" + 
					"}";
			System.out.println(s);
			return s;
		}

		public static String MapCustomScreen(int screenid)
		{
			String id=String.valueOf(screenid);
			String s="{\r\n" + 
					"  \"screens\": [\r\n" + 
					"    {\r\n" + 
					"      \"screen\":"+id+",\r\n" + 
					"      \"position\": 2\r\n" + 
					"    }\r\n" + 
					"  ]\r\n" + 
					"}";
			return s;
		}public static String removeFormId(String id)
		{
			String s="{\r\n" + 
					"\"deleted_form_fields\":["+id+"],\r\n" + 
					"\"form_fields\":	[],\r\n" + 
					"\"form_type\":\"My screen\",\r\n" + 
					"\"title\":	\"My screen\"\r\n" + 
					"}";
			return s;
		}
		public static String removeWorkflow(String form_id)
		{
			String s="{\r\n" + 
					"    \"object_id\": 12,\r\n" + 
					"    \"object_type\":\"venue\",\r\n" + 
					"    \"form_id\": "+form_id+"\r\n" + 
					"}";
			return s;
		}

		
		public static String removeexixtingscreen(String id)
		{
			String s="{\r\n" + 
					"	\"remove_screens_mappings\":["+id+"]\r\n" + 
					"}";
			return s;
		}

		
		
}
