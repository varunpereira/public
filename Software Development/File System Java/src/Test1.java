import static org.junit.Assert.*;

import org.junit.*;

//test method naming convention: method_condition_outcome
// return success is 0, return failure is 1

public class Test1 {

	FSLogic fs;

	@Before
	public void setup() {
		fs = new FSLogic();
	}

	@Test
	public void listIsValid_validNormalFile_returnSuccess() {
		String testName = "listIsValid_validNormalFile_returnSuccess()";
		String internalRecordPath = "FSTest.notes";
		try {
			assertEquals(0,fs.listIsValid(internalRecordPath));
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Successful test for : " + testName);
	}

}
