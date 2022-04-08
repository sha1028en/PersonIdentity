package com.mrhi.class_six.person_identity;


import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class PersonIdentity implements Comparable<Object>{
	
	////	OUR MEMBERS ARE @NotNull	/////
	
	private String personId; 		// length = 10. PRIMARY KEY
	private String personName;		// length = 11
	private int personAge;			// int
	
	private int pointLangC;			// int
	private int pointLangJava;		// int
	private int pointLangSql;		// int
	private int pointSum;			// int
	private double pointAvg;		// float(8)
	private String pointGrade;		// length = 1
	
	
	private final int LIMIT_POINT_MAX = 100;
	private final int LIMIT_POINT_MIN = 0;
	
	
	private static Scanner scanner;
	static {
		PersonIdentity.scanner = new Scanner( System.in);
	}
	
	
	public PersonIdentity() {	this(null, null, 0, 0, 0, 0, 0, 0.0, null);	}
	public PersonIdentity(	String personId, String personName, int personAge, 
							int pointLangC, int pointLangJava, int pointLangSql, 
							int pointSum, double pointAvg, String pointGrade) {
		this.personId = personId;
		this.personName = personName;
		this.personAge = personAge;
		this.pointLangC = pointLangC;
		this.pointLangJava = pointLangJava;
		this.pointLangSql = pointLangSql;
		this.pointSum = pointSum;
		this.pointAvg = pointAvg;
		this.pointGrade = pointGrade;
	}
	
	// Input Members Value to Set 
	public PersonIdentity setPersonIdentityInput() {
		
		this.personId 		= getPersonIdInput();
		this.personName 	= getPersonNameInput();
		this.personAge 		= getPersonAgeInput();
		
		this.pointLangC		= getPointInput("C ¾ð¾î Á¡¼ö¸¦ ÀÔ·ÂÇØ ÁÖ¼¼¿ä(" 		+ LIMIT_POINT_MIN + " ~ " + LIMIT_POINT_MAX + ")");
		this.pointLangJava	= getPointInput("Java ¾ð¾î Á¡¼ö¸¦ ÀÔ·ÂÇØ ÁÖ¼¼¿ä(" 	+ LIMIT_POINT_MIN + " ~ " + LIMIT_POINT_MAX + ")");
		this.pointLangSql	= getPointInput("SQL ¾ð¾î Á¡¼ö¸¦ ÀÔ·ÂÇØ ÁÖ¼¼¿ä(" 	+ LIMIT_POINT_MIN + " ~ " + LIMIT_POINT_MAX + ")");
		this.initPersonIdentity();
		return this;
	}
	
	

	// init after set Members Value.
	// to update pointSum / Avg / Grade.
	public PersonIdentity initPersonIdentity() {
		
		this.pointSum		= getPointSummry(this.pointLangC, this.pointLangJava, this.pointLangSql);
		this.pointAvg		= getPointAverage(this.pointSum);
		this.pointGrade		= getPointGrade(this.pointAvg);
	
		return this;
	}
	
	
	////	for SET this PersonIdentity func()	////
	public int getPointInput(String infor) {
		int pointLang = -1;
			
		while(true ) {
			System.out.println( infor);
			try {	
				pointLang = scanner.nextInt();
			
		} catch (InputMismatchException e) {
			System.err.println("Àß¸ø ÀÔ·ÂÇÏ¼Ì¾î¿ä!!");
			PersonIdentity.scanner.close();
			PersonIdentity.scanner = new Scanner( System.in);
		}
			
			if(		pointLang <=	LIMIT_POINT_MAX &&
					pointLang >=	LIMIT_POINT_MIN) 
					break;
					
			System.out.println("´Ù½Ã ÀÔ·ÂÇØ ÁÖ½Ã°Ú¾î¿ä??");
		}
		return pointLang;
	}
	
	

	public int getPersonAgeInput() {
		int personAge = -1;
			
		while(true ) {
			System.out.println("³ªÀÌ¸¦ ÀÔ·ÂÇÏ¼¼¿ä (20 ~ 40)");
			try {	
				personAge = scanner.nextInt();
				
			} catch (InputMismatchException e) {
			System.err.println("Àß¸ø ÀÔ·ÂÇÏ¼Ì¾î¿ä!!");
			PersonIdentity.scanner.close();
			PersonIdentity.scanner = new Scanner( System.in);
		}
			
				
			if(		personAge < 41 &&
					personAge > 19 )
					break;	
			System.out.println("´Ù½Ã ÀÔ·ÂÇØ ÁÖ½Ã°Ú¾î¿ä??");
		}
		return personAge;
	}
	
	
	
	
	
	public String getPersonIdInput() {
		String personId = null;
		
		while(true ) {
			personId = (String ) getValueInput( "ID¸¦ ÀÔ·ÂÇÏ¼¼¿ä!");
			scanner.nextLine();
			
			if(		isCorrectLength(personId, 11)) {
					this.personId = personId;
					break;
			}	
			System.out.println("´Ù½Ã ÀÔ·ÂÇØ ÁÖ½Ã°Ú¾î¿ä??");
		}
		
		
		return this.personId;
	}
	

	public String getPersonNameInput() {
		final String PATTERN_NAME 		= "^[°¡-ÆR]{2,4}$";
		String personName = null;
		
		while(true ) {
			personName = (String ) getValueInput( "ÀÌ¸§À» ÀÔŽ¶ÇÏ¼¼¿ä!");
			scanner.nextLine();
			
			if( 	isVaildValue( personName, PATTERN_NAME) &&
					isCorrectLength( personName, 11)) 
					break;	
			System.out.println("´Ù½Ã ÀÔ·ÂÇØ ÁÖ½Ã°Ú¾î¿ä??");
		}
		
		
		return personName;
	}
	
	
	// for initPersonIdentity() 
	////	 __INTERNAL FUNC__		////
	private int getPointSummry(int...subject) {
		int pointSummry = 0;
		
		for( int i : subject) {
			
			pointSummry += i;	
		}
		
		return pointSummry;
	}
	
	
	private double getPointAverage(int summry) {
		return (double ) summry / 3.0;
	}
	
	
	private String getPointGrade(double avgerage) {
		String grade = null;
		final int GRADE_A = 9;
		final int GRADE_B = 8;
		final int GRADE_C = 7;
		final int GRADE_D = 6;
		
		
		switch ((int )avgerage / 10) {
			case 10:
			case GRADE_A:	grade = "A"; break;
			case GRADE_B: 	grade = "B"; break;
			case GRADE_C:	grade = "C"; break;
			case GRADE_D:	grade = "D"; break;
			default:		grade = "F";
		}
		
		return grade;
	}
	
	
	////	__Internal Func()__		////
	
	private boolean isCorrectLength( String value, int length) {	
		
		return ( value.length() <= length) ?( true) :( false);
	}
	
	
	// AFTER USE, NEED TO CASTING VAILD OBJECT TYPE
	// CAUTION TO USE FLUSH BUFFER
	private Object getValueInput( String infor) {
		Object returnValue = null;
		
		System.out.println( infor);
		try {
			returnValue = scanner.next();
		
		} catch ( Exception e) {
			PersonIdentity.scanner.close();
			PersonIdentity.scanner = new Scanner( System.in);
		} 
		return returnValue;
	}
	
		
	// TO USE personIdentityInsert()... isVaild Ivalue???
	private boolean isVaildValue( String value, String filter) {
			
		Pattern pattern = Pattern.compile( filter);
	    Matcher matcher = pattern.matcher( value);
	    if ( matcher.matches()) return true;
		    
	    return false;
	}
	
	
	//// 	__Override Func()__		////
	@Override
	public int compareTo( Object obj) {
		int isSame = 0;
		
		if( !( obj instanceof PersonIdentity))
			throw new IllegalArgumentException();
		
		PersonIdentity buf = (PersonIdentity ) obj;
		
		if(buf.pointSum > this.pointSum)
			isSame = -1;
		
		else if( buf.pointSum < this.pointSum )
			isSame = 1;
		
		
		return isSame;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		
		if( !( obj instanceof PersonIdentity))
			throw new IllegalArgumentException();
		
		PersonIdentity buf = (PersonIdentity ) obj;
		
		return ( this.personId.equals(buf.personId)) ?( true) :( false);
	}
	
	
	@Override
	public int  hashCode() {	return Objects.hash( this.personId);	}
	
	
	@Override
	public String toString() {
		return 	personId + "\t" + personName + "\t" + personAge +"\t" + 
				pointLangC + "\t" + pointLangJava + "\t" + pointLangSql + "\t" + 
				pointSum + "\t" + String.format("%3.2f", pointAvg) + "\t" + pointGrade;
	}
	
	
	////	GETTER / SETTER		////
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public int getPersonAge() {
		return personAge;
	}

	public void setPersonAge(int personAge) {
		this.personAge = personAge;
	}

	public int getPointLangC() {
		return pointLangC;
	}

	public void setPointLangC(int pointLangC) {
		this.pointLangC = pointLangC;
	}

	public int getPointLangJava() {
		return pointLangJava;
	}

	public void setPointLangJava(int pointLangJava) {
		this.pointLangJava = pointLangJava;
	}

	public int getPointLangSql() {
		return pointLangSql;
	}

	public void setPointLangSql(int pointLangSql) {
		this.pointLangSql = pointLangSql;
	}

	public int getPointSum() {
		return pointSum;
	}

	public void setPointSum(int pointSum) {
		this.pointSum = pointSum;
	}

	public double getPointAvg() {
		return pointAvg;
	}

	public void setPointAvg(double pointAvg) {
		this.pointAvg = pointAvg;
	}

	public String getPointGrade() {
		return pointGrade;
	}

	public void setPointGrade(String pointGrade) {
		this.pointGrade = pointGrade;
	}
}
