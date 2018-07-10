package cardiact.bpl.pkg.com.bplcardiactconnect;

import android.support.test.rule.*;
import android.view.*;

import org.junit.*;

import static org.junit.Assert.*;

public class SplashScreenActivityTest {

    @Rule
    public ActivityTestRule<SplashScreenActivity> splashScreenActivityActivityTestRule=new
            ActivityTestRule<>(SplashScreenActivity.class);


    private SplashScreenActivity splashScreenActivity=null;



    @Before
    public void setUp() throws Exception {
        splashScreenActivity=splashScreenActivityActivityTestRule.getActivity();
    }


    @Test
    public void testHandler(){

        View view=splashScreenActivity.findViewById(R.id.text);
        assertNotNull(view);

    }

    @After
    public void tearDown() throws Exception {
        splashScreenActivity=null;
    }
}