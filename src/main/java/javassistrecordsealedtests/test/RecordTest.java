package javassistrecordsealedtests.test;

public record RecordTest(String name, int inte, Object obj) {

	public static RecordTest rec_0 = new RecordTest("test0",0,new String("Test0"));
	public static RecordTest rec_1 = new RecordTest("test1",1,new String("Test1"));

	
}
