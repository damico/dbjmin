package org.jdamico.dbjmin.test;

import db2jmin.pojo.util.OutputDataValidation;
import junit.framework.TestCase;

public class TestOutputDataValidation extends TestCase {

	public void testWrapResult() {
		String output = "ASASAKKLÇasÇLÇLÇÇ,Lkdsçldkspokdçsldsdfjoiej023u0291kolvmn30ur430yhouiwqpeldo3049ur43p9rjo4weoçfjiewnopfnc4r90tvpc4,ri4=-0mpwakemw0s9vcu34w0uvbçm,pkvdfé-0w9ivrmi40 t9ik5i09tv0it0 9i5-0jk-0u302cun 034j-5jtv- utg-59j prj-9gj -34uir=4vbt 54-gj0-jf pobtjgŕ[fe-g 0t i53-j0pj -0t-jtk gpokfe-0 kwe-9ui894ut580oj 60ikgimtofj098rut85jhti5ompdsm f05j tw4oijnkl";
		System.out.println(OutputDataValidation.getInstance()
				.wrapResult(output));
	}

}
