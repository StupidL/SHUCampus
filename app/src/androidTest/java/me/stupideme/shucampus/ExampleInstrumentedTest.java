package me.stupideme.shucampus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.test.ActivityUnitTestCase;
import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;

import me.stupideme.shucampus.remind.RemindersActivity;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest extends ActivityUnitTestCase<RemindersActivity>{

    private Context mContext;
    private Activity mActivity;
    private Toolbar toolbar;
    private Snackbar snackbar;

    public ExampleInstrumentedTest() {
        super(RemindersActivity.class);
        mContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void useAppContext() throws Exception {
        assertEquals("me.stupideme.shucampus", mContext.getPackageName());
    }

    @Override
    public void setUp() throws Exception{
        super.setUp();
        Intent intent = new Intent(mContext,RemindersActivity.class);
        startActivity(intent,null,null);

        mActivity = getActivity();
        toolbar = (Toolbar) mActivity.findViewById(R.id.toolbar);
        assertNotNull(toolbar);
        assertEquals("",toolbar.getTitle());

        FloatingActionButton floatingActionButton = (FloatingActionButton) mActivity.findViewById(R.id.fab);
        assertNotNull(floatingActionButton);
        assertEquals("",floatingActionButton.getTransitionName());

        snackbar = Snackbar.make(toolbar,"Hello",Snackbar.LENGTH_SHORT);


    }

    @Test
    public void testToolbar(){

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.show();
            }
        });
         toolbar.performClick();
        assertEquals(false,snackbar.isShown());
    }

}
