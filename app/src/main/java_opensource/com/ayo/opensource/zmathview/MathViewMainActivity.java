package com.ayo.opensource.zmathview;

import android.os.Bundle;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

public class MathViewMainActivity extends BaseActivity {
    MathView formula_two;
    MathView formula_three;
    String tex = "This come from string. You can insert inline formula:" +
            " \\(ax^2 + bx + c = 0\\) " +
            "or displayed formula: $$\\sum_{i=0}^n i^2 = \\frac{(n^2+n)(2n+1)}{6}$$";
    String mathml =
            "<math xmlns=\"http://www.w3.org/1998/Math/MathML\" display=\"block\" mathcolor=\"black\">\n" +
            "  <mrow>\n" +
            "    <mi>f</mi>\n" +
            "    <mrow>\n" +
            "      <mo>(</mo>\n" +
            "      <mi>a</mi>\n" +
            "      <mo>)</mo>\n" +
            "    </mrow>\n" +
            "  </mrow>\n" +
            "  <mo>=</mo>\n" +
            "  <mrow>\n" +
            "    <mfrac>\n" +
            "      <mn>1</mn>\n" +
            "      <mrow>\n" +
            "        <mn>2</mn>\n" +
            "        <mi>&#x3C0;</mi>\n" +
            "        <mi>i</mi>\n" +
            "      </mrow>\n" +
            "    </mfrac>\n" +
            "    <msub>\n" +
            "      <mo>&#x222E;</mo>\n" +
            "      <mrow>\n" +
            "        <mi>&#x3B3;</mi>\n" +
            "      </mrow>\n" +
            "    </msub>\n" +
            "    <mfrac>\n" +
            "      <mrow>\n" +
            "        <mi>f</mi>\n" +
            "        <mo>(</mo>\n" +
            "        <mi>z</mi>\n" +
            "        <mo>)</mo>\n" +
            "      </mrow>\n" +
            "      <mrow>\n" +
            "        <mi>z</mi>\n" +
            "        <mo>&#x2212;</mo>\n" +
            "        <mi>a</mi>\n" +
            "      </mrow>\n" +
            "    </mfrac>\n" +
            "    <mi>d</mi>\n" +
            "    <mi>z</mi>\n" +
            "  </mrow>\n" +
            "</math>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mathview_ac_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        formula_two = (MathView) findViewById(R.id.formula_two);
        formula_three = (MathView) findViewById(R.id.formula_three);

        formula_two.setText(tex);
        formula_three.setText(mathml);
    }

}
