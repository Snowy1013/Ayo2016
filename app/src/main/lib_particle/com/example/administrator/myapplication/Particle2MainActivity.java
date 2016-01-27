package com.example.administrator.myapplication;

import android.os.Bundle;

import com.cowthan.sample.R;
import com.example.administrator.myapplication.factory.ExplodeParticleFactory;
import com.example.administrator.myapplication.factory.FallingParticleFactory;
import com.example.administrator.myapplication.factory.FlyawayFactory;
import com.example.administrator.myapplication.factory.VerticalAscentFactory;

import org.ayo.app.base.ActivityAttacher;

public class Particle2MainActivity extends ActivityAttacher {//ActionBarActivity {

	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.paticle2_ac_main);
	       
	        ExplosionField explosionField = new ExplosionField(getActivity(),new FallingParticleFactory());
	        explosionField.addListener(findViewById(R.id.text));
	        explosionField.addListener(findViewById(R.id.layout1));

	        ExplosionField explosionField2 = new ExplosionField(getActivity(),new FlyawayFactory());
	        explosionField2.addListener(findViewById(R.id.text2));
	        explosionField2.addListener(findViewById(R.id.layout2));
	        
	        ExplosionField explosionField4 = new ExplosionField(getActivity(),new ExplodeParticleFactory());
	        explosionField4.addListener(findViewById(R.id.text3));
	        explosionField4.addListener(findViewById(R.id.layout3));
	        
	        ExplosionField explosionField5 = new ExplosionField(getActivity(),new VerticalAscentFactory());
	        explosionField5.addListener(findViewById(R.id.text4));
	        explosionField5.addListener(findViewById(R.id.layout4));
	    }


}
